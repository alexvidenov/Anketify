package com.example.anketify.models.requests

import kotlinx.serialization.Serializable

@Serializable
data class FormUpdateRequest(
    val isClosed: Boolean,
    val isPublic: Boolean,
)