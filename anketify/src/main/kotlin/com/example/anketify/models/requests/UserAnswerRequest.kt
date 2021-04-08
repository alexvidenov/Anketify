package com.example.anketify.models.requests

import kotlinx.serialization.Serializable

@Serializable
data class UserAnswerRequest(
    val index: Int,
    val questionId: Long,
    val formId: Long,
)