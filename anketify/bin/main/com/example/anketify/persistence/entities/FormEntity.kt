package com.example.anketify.persistence.entities

import com.example.anketify.persistence.base.BaseEntity
import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient
import javax.persistence.*

@Entity
@Table(name = "forms")
@Serializable
data class FormEntity(
    val name: String,
    var isPublic: Boolean? = false,
    var isClosed: Boolean? = false,

    @Transient
    @Column(name = "user_id")
    val userId: Long? = -1,

    @ManyToMany(fetch = FetchType.EAGER, cascade = [CascadeType.ALL])
    @JoinTable(name = "question_form", joinColumns = [JoinColumn(name = "form_id")],
        inverseJoinColumns = [JoinColumn(name = "question_id")])
    var questions: List<QuestionEntity> = listOf(),

    @Transient
    @OneToMany(fetch = FetchType.LAZY, cascade = [CascadeType.ALL])
    @JoinColumn(name = "form_id", referencedColumnName = "id")
    val userAnswers: List<UserAnswerEntity> = listOf(),

    @Transient
    @OneToMany(fetch = FetchType.LAZY, cascade = [CascadeType.ALL])
    @JoinColumn(name = "form_id", referencedColumnName = "id")
    val ipAnswers: List<IpAnswerEntity> = listOf(),

    @OneToOne(fetch = FetchType.LAZY, cascade = [CascadeType.ALL])
    val formUUID: FormUUIDEntity? = null,
) : BaseEntity() {

    companion object {
        fun empty() = FormEntity(name = "nonexistent", userId = -1)
    }
}
