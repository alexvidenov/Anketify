package com.example.anketify.persistence.entities

import com.example.anketify.persistence.base.BaseEntity
import kotlinx.serialization.Serializable
import javax.persistence.*

@Entity
@Table(name = "questions")
@Serializable
data class QuestionEntity(
    @Column(length = 1000000)
    val imageDescription: String? = null, // base64 String lmao..lazy for more decent solution
    val description: String,
    val optional: Boolean? = false,
    val canSelectMoreThanOne: Boolean? = false,

    @OneToMany(fetch = FetchType.LAZY, cascade = [CascadeType.ALL])
    @JoinColumn(name = "question_id")
    val answers: List<AnswerEntity> = listOf(),
) : BaseEntity()
