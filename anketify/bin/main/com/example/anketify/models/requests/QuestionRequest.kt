package com.example.anketify.models.requests

import kotlinx.serialization.Serializable

@Serializable
data class QuestionRequest(
    val description: String,
    val imageDescription: String?,
    val optional: Boolean,
    val canSelectMoreThanOne: Boolean,
    val answers: List<String>,
)