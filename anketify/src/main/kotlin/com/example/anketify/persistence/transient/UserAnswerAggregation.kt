package com.example.anketify.persistence.transient

import kotlinx.serialization.Serializable

@Serializable
data class UserAnswerAggregation(
    val aggregatedUserAnswers: Map<Long, Int> = mapOf(), // Map of all answerId -> votes mappings
)