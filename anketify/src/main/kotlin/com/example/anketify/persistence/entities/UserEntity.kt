package com.example.anketify.persistence.entities

import com.example.anketify.persistence.base.BaseEntity
import kotlinx.serialization.Serializable
import javax.persistence.*

@Entity
@Table(name = "users")
@Serializable
data class UserEntity(
    val username: String,
    val password: String,
    @OneToMany(fetch = FetchType.LAZY, cascade = [CascadeType.ALL])
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    val forms: List<FormEntity> = listOf(),
) : BaseEntity()