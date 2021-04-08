package com.example.anketify.persistence.entities

import com.example.anketify.persistence.base.BaseEntity
import kotlinx.serialization.Serializable
import javax.persistence.*

@Entity
@Table(name = "questions")
@Serializable
data class QuestionEntity(
    @Lob
    val imageDescription: ByteArray? = byteArrayOf(),
    val description: String,
    val optional: Boolean? = false,
    val canSelectMoreThanOne: Boolean? = false,

    @OneToMany(fetch = FetchType.LAZY, cascade = [CascadeType.ALL])
    @JoinColumn(name = "question_id")
    val answers: List<AnswerEntity> = listOf(),
) : BaseEntity() {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as QuestionEntity

        if (imageDescription != null) {
            if (other.imageDescription == null) return false
            if (!imageDescription.contentEquals(other.imageDescription)) return false
        } else if (other.imageDescription != null) return false
        if (description != other.description) return false
        if (optional != other.optional) return false
        if (canSelectMoreThanOne != other.canSelectMoreThanOne) return false
        if (answers != other.answers) return false

        return true
    }

    override fun hashCode(): Int {
        var result = imageDescription?.contentHashCode() ?: 0
        result = 31 * result + description.hashCode()
        result = 31 * result + (optional?.hashCode() ?: 0)
        result = 31 * result + (canSelectMoreThanOne?.hashCode() ?: 0)
        result = 31 * result + answers.hashCode()
        return result
    }
}

