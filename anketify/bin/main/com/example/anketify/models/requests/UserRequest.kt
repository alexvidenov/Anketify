package com.example.anketify.models.requests

import com.example.anketify.models.users.PasswordEncoder
import com.example.anketify.persistence.entities.UserEntity
import kotlinx.serialization.Serializable

@Serializable
data class UserRequest(
    val username: String,
    val password: String
)

fun UserRequest.toEntity(encoder: PasswordEncoder): UserEntity =
    UserEntity(username = username, password = encoder(password))
