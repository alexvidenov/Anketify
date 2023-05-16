package com.example.anketify.models.requests

import kotlinx.serialization.Serializable

@Serializable
data class FormRequest(
    val name: String,
    val isPublic: Boolean,
    val isClosed: Boolean,
    var questions: List<QuestionRequest>
)