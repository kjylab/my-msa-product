package dev.ktcloud.black.product.adapter.infrastructure.jpa.entity

import dev.ktcloud.black.common.domain.entity.BaseOrmEntity
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table
import org.hibernate.annotations.JdbcTypeCode
import org.hibernate.annotations.SQLDelete
import org.hibernate.type.SqlTypes
import java.sql.Types

@SQLDelete(sql = "UPDATE products SET deleted_at = NOW() WHERE id = ?")
@Entity
@Table(name = "products")
data class Product(
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @JdbcTypeCode(Types.VARCHAR)
    val id: String,

    @Column
    val name: String,

    @JdbcTypeCode(SqlTypes.LONGVARCHAR)
    @Column(columnDefinition = "TEXT")
    val description: String,

    @JdbcTypeCode(Types.NUMERIC)
    val price: Int,
): BaseOrmEntity()