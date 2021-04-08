package com.example.anketify.persistence.entities

import com.example.anketify.persistence.base.BaseEntity
import kotlinx.serialization.Serializable
import javax.persistence.*

@Entity
@Table(name = "answers")
@Serializable
data class AnswerEntity(
    val description: String,
    @Column(name = "question_id")
    val question_id: Long? = -1,
) : BaseEntity()