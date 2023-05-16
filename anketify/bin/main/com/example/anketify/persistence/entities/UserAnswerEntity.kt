package com.example.anketify.persistence.entities

import com.example.anketify.persistence.base.BaseEntity
import kotlinx.serialization.Serializable
import javax.persistence.*

@Entity
@Table(name = "user_answers")
@Serializable
data class UserAnswerEntity(
    val index: Int,
    val ip: String,
    val questionId: Long,
    @Column(name = "form_id")
    val formId: Long,
) : BaseEntity()
