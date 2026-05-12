package dev.ktcloud.black.product.service.adapter.presentation.web.inbound.grpc

import dev.ktcloud.black.product.service.adapter.presentation.web.inbound.grpc.ProductServiceGrpc.getServiceDescriptor
import io.grpc.CallOptions
import io.grpc.CallOptions.DEFAULT
import io.grpc.Channel
import io.grpc.Metadata
import io.grpc.MethodDescriptor
import io.grpc.ServerServiceDefinition
import io.grpc.ServerServiceDefinition.builder
import io.grpc.ServiceDescriptor
import io.grpc.Status.UNIMPLEMENTED
import io.grpc.StatusException
import io.grpc.kotlin.AbstractCoroutineServerImpl
import io.grpc.kotlin.AbstractCoroutineStub
import io.grpc.kotlin.ClientCalls.unaryRpc
import io.grpc.kotlin.ServerCalls.unaryServerMethodDefinition
import io.grpc.kotlin.StubFor
import kotlin.String
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext
import kotlin.jvm.JvmOverloads
import kotlin.jvm.JvmStatic

/**
 * Holder for Kotlin coroutine-based client and server APIs for product.service.ProductService.
 */
public object ProductServiceGrpcKt {
  public const val SERVICE_NAME: String = ProductServiceGrpc.SERVICE_NAME

  @JvmStatic
  public val serviceDescriptor: ServiceDescriptor
    get() = getServiceDescriptor()

  public val createProductMethod: MethodDescriptor<CreateProductRequest, ProductResponseDto>
    @JvmStatic
    get() = ProductServiceGrpc.getCreateProductMethod()

  public val fetchProductMethod: MethodDescriptor<FetchProductRequest, ProductResponseDto>
    @JvmStatic
    get() = ProductServiceGrpc.getFetchProductMethod()

  public val fetchAllMethod: MethodDescriptor<Empty, FetchAllProductsResponse>
    @JvmStatic
    get() = ProductServiceGrpc.getFetchAllMethod()

  /**
   * A stub for issuing RPCs to a(n) product.service.ProductService service as suspending
   * coroutines.
   */
  @StubFor(ProductServiceGrpc::class)
  public class ProductServiceCoroutineStub @JvmOverloads constructor(
    channel: Channel,
    callOptions: CallOptions = DEFAULT,
  ) : AbstractCoroutineStub<ProductServiceCoroutineStub>(channel, callOptions) {
    override fun build(channel: Channel, callOptions: CallOptions): ProductServiceCoroutineStub =
        ProductServiceCoroutineStub(channel, callOptions)

    /**
     * Executes this RPC and returns the response message, suspending until the RPC completes
     * with [`Status.OK`][io.grpc.Status].  If the RPC completes with another status, a
     * corresponding
     * [StatusException] is thrown.  If this coroutine is cancelled, the RPC is also cancelled
     * with the corresponding exception as a cause.
     *
     * @param request The request message to send to the server.
     *
     * @param headers Metadata to attach to the request.  Most users will not need this.
     *
     * @return The single response from the server.
     */
    public suspend fun createProduct(request: CreateProductRequest, headers: Metadata = Metadata()):
        ProductResponseDto = unaryRpc(
      channel,
      ProductServiceGrpc.getCreateProductMethod(),
      request,
      callOptions,
      headers
    )

    /**
     * Executes this RPC and returns the response message, suspending until the RPC completes
     * with [`Status.OK`][io.grpc.Status].  If the RPC completes with another status, a
     * corresponding
     * [StatusException] is thrown.  If this coroutine is cancelled, the RPC is also cancelled
     * with the corresponding exception as a cause.
     *
     * @param request The request message to send to the server.
     *
     * @param headers Metadata to attach to the request.  Most users will not need this.
     *
     * @return The single response from the server.
     */
    public suspend fun fetchProduct(request: FetchProductRequest, headers: Metadata = Metadata()):
        ProductResponseDto = unaryRpc(
      channel,
      ProductServiceGrpc.getFetchProductMethod(),
      request,
      callOptions,
      headers
    )

    /**
     * Executes this RPC and returns the response message, suspending until the RPC completes
     * with [`Status.OK`][io.grpc.Status].  If the RPC completes with another status, a
     * corresponding
     * [StatusException] is thrown.  If this coroutine is cancelled, the RPC is also cancelled
     * with the corresponding exception as a cause.
     *
     * @param request The request message to send to the server.
     *
     * @param headers Metadata to attach to the request.  Most users will not need this.
     *
     * @return The single response from the server.
     */
    public suspend fun fetchAll(request: Empty, headers: Metadata = Metadata()):
        FetchAllProductsResponse = unaryRpc(
      channel,
      ProductServiceGrpc.getFetchAllMethod(),
      request,
      callOptions,
      headers
    )
  }

  /**
   * Skeletal implementation of the product.service.ProductService service based on Kotlin
   * coroutines.
   */
  public abstract class ProductServiceCoroutineImplBase(
    coroutineContext: CoroutineContext = EmptyCoroutineContext,
  ) : AbstractCoroutineServerImpl(coroutineContext) {
    /**
     * Returns the response to an RPC for product.service.ProductService.CreateProduct.
     *
     * If this method fails with a [StatusException], the RPC will fail with the corresponding
     * [io.grpc.Status].  If this method fails with a [java.util.concurrent.CancellationException],
     * the RPC will fail
     * with status `Status.CANCELLED`.  If this method fails for any other reason, the RPC will
     * fail with `Status.UNKNOWN` with the exception as a cause.
     *
     * @param request The request from the client.
     */
    public open suspend fun createProduct(request: CreateProductRequest): ProductResponseDto = throw
        StatusException(UNIMPLEMENTED.withDescription("Method product.service.ProductService.CreateProduct is unimplemented"))

    /**
     * Returns the response to an RPC for product.service.ProductService.FetchProduct.
     *
     * If this method fails with a [StatusException], the RPC will fail with the corresponding
     * [io.grpc.Status].  If this method fails with a [java.util.concurrent.CancellationException],
     * the RPC will fail
     * with status `Status.CANCELLED`.  If this method fails for any other reason, the RPC will
     * fail with `Status.UNKNOWN` with the exception as a cause.
     *
     * @param request The request from the client.
     */
    public open suspend fun fetchProduct(request: FetchProductRequest): ProductResponseDto = throw
        StatusException(UNIMPLEMENTED.withDescription("Method product.service.ProductService.FetchProduct is unimplemented"))

    /**
     * Returns the response to an RPC for product.service.ProductService.FetchAll.
     *
     * If this method fails with a [StatusException], the RPC will fail with the corresponding
     * [io.grpc.Status].  If this method fails with a [java.util.concurrent.CancellationException],
     * the RPC will fail
     * with status `Status.CANCELLED`.  If this method fails for any other reason, the RPC will
     * fail with `Status.UNKNOWN` with the exception as a cause.
     *
     * @param request The request from the client.
     */
    public open suspend fun fetchAll(request: Empty): FetchAllProductsResponse = throw
        StatusException(UNIMPLEMENTED.withDescription("Method product.service.ProductService.FetchAll is unimplemented"))

    final override fun bindService(): ServerServiceDefinition = builder(getServiceDescriptor())
      .addMethod(unaryServerMethodDefinition(
      context = this.context,
      descriptor = ProductServiceGrpc.getCreateProductMethod(),
      implementation = ::createProduct
    ))
      .addMethod(unaryServerMethodDefinition(
      context = this.context,
      descriptor = ProductServiceGrpc.getFetchProductMethod(),
      implementation = ::fetchProduct
    ))
      .addMethod(unaryServerMethodDefinition(
      context = this.context,
      descriptor = ProductServiceGrpc.getFetchAllMethod(),
      implementation = ::fetchAll
    )).build()
  }
}
