package com.example.anketify.persistence.entities

import com.example.anketify.persistence.base.BaseEntity
import kotlinx.serialization.Serializable
import javax.persistence.*

/*
    User logs in
    goes to /profile
    can see all forms he has created on /forms/all. Needs just the list of forms.
    can start a new form on /forms/create. Inserts form with userid, then all questions with formid, and then answers with questionid
    can update (close or make it public) on /forms/{id}/update. Will just fetch single form

    Client:
    in the unique link, he receives the whole form again

    upon completion, the React sends list of userAnswer object for the form id

 */

@Entity
@Table(name = "users")
@Serializable
data class UserEntity(
    val username: String,
    val password: String,
    @OneToMany(fetch = FetchType.LAZY, cascade = [CascadeType.ALL])
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    val forms: List<FormEntity> = listOf(),
) : BaseEntity()