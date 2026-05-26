# my-msa-product

Troica Market MSA의 **상품(Product) 서비스**. 헥사고날 아키텍처로 구성되며, gRPC로 외부와 통신한다.

## 아키텍처

### 헥사고날(Ports & Adapters) 구조

```
product/                          ← 순수 도메인 + 유스케이스
  domain/entity/ProductDomainEntity
  domain/exception/ProductException
  application/port/inbound/       ← UseCase 인터페이스 (FetchProductQuery, CreateProductCommand 등)
  application/port/outbound/      ← Repository 인터페이스
  application/service/            ← 비즈니스 로직 구현
  adapter/infrastructure/jpa/     ← PostgreSQL 구현체

product-service/                  ← 실행 진입점 (Spring Boot + gRPC 서버)
  ProductGrpcController           ← gRPC 요청 수신 → UseCase 호출
  GrpcExceptionHandler            ← CustomException → gRPC Status 변환
```

### 왜 모듈을 나누나?

`product` 모듈은 Spring Boot에 의존하지 않는 순수 비즈니스 로직 레이어. `product-service`는 실행 모듈로 gRPC 서버와 DB 연결만 담당한다. 이렇게 나누면 도메인 로직을 외부 기술(gRPC, JPA)과 독립적으로 테스트할 수 있다.

## gRPC API

```protobuf
service ProductService {
  rpc CreateProduct (CreateProductRequest) returns (ProductResponseDto);
  rpc FetchProduct  (FetchProductRequest)  returns (ProductResponseDto);
  rpc FetchAll      (Empty)                returns (FetchAllProductsResponse);
}
```

| 메서드 | 설명 |
|--------|------|
| CreateProduct | 상품 생성 |
| FetchProduct | ID로 단건 조회 (UUID 형식 필요) |
| FetchAll | 전체 목록 조회 |

> gRPC 포트: **9090** (HTTP: 8080)

## 예외 처리 흐름

```
UUID.fromString(id) 실패 → IllegalArgumentException
    ↓ catch
NoSuchProductException (CustomException, status=404)
    ↓
GrpcExceptionHandler → Status.NOT_FOUND
    ↓
user-api-gateway GlobalExceptionHandler → HTTP 404
```

## 실행 포트

| 포트 | 용도 |
|------|------|
| 8080 | HTTP (actuator: /healthz, /prometheus) |
| 9090 | gRPC (내부 서비스 통신) |

## 관측성 (Observability)

### 메트릭 (Prometheus)
- `/prometheus` 엔드포인트로 메트릭 노출 (`management.endpoints.web.base-path: /`)
- HTTP 요청별 latency histogram bucket 활성화 (`percentiles-histogram: true`)
- ServiceMonitor로 Prometheus가 자동 스크레이프

### 분산 트레이싱 (Tempo)
- `micrometer-tracing-bridge-otel` + `opentelemetry-exporter-otlp` 사용
- OTLP HTTP로 Tempo(`tempo.monitoring.svc.cluster.local:4318`)로 전송
- sampling probability: 1.0 (전량 수집)
- user-api-gateway → product-service 간 trace context 전파 완료

### 로그-트레이스 연동
- 로그에 `[traceId-spanId]` 포함 → Grafana Loki에서 Tempo 링크로 바로 이동 가능

## CI/CD 흐름

```
GitHub push
  → JAR 빌드
  → Docker 이미지 빌드 + Docker Hub push (jyupk/my-msa-product-service)
  → my-msa-manifest-values/product-service/values-release.yaml 의 tag를 커밋 SHA로 업데이트
  → ArgoCD 감지 → 클러스터 롤링 업데이트
```

## 의존 라이브러리 (주요)

| 라이브러리 | 역할 |
|-----------|------|
| `my-msa-common` (GitHub Packages) | CustomException, BaseEntity, QueryDSL 설정 |
| `grpc-spring-boot-starter` | gRPC 서버 |
| `micrometer-registry-prometheus` | 메트릭 노출 |
| `micrometer-tracing-bridge-otel` | OTLP 트레이싱 |
| PostgreSQL | 상품 데이터 저장 |

## 로컬 Docker 빌드

```bash
docker build --no-cache -t ktcloud-msa-product-service:latest -f Containerfile .
```

## 관련 레포

| 레포 | 역할 |
|------|------|
| [my-msa-common](https://github.com/kjylab/my-msa-common) | 공통 라이브러리 |
| [my-msa-manifest-values](https://github.com/kjylab/my-msa-manifest-values) | Helm values (이미지 태그 관리) |
| [my-market-msa-manifest](https://github.com/kjylab/my-market-msa-manifest) | 공통 Helm 차트 |
