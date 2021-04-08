package com.example.anketify.persistence.transient

import kotlinx.serialization.Serializable

@Serializable
data class UserAnswerAggregation(
    val aggregatedUserAnswers: Map<Long, Int> = mapOf(), // List of all answerId -> votes mappings
)