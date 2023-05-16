package com.example.anketify.persistence.entities

import com.example.anketify.persistence.base.BaseEntity
import kotlinx.serialization.Serializable
import javax.persistence.Column
import javax.persistence.Entity

@Entity(name = "ip_answers")
@Serializable
data class IpAnswerEntity(
    val ip: String,
    @Column(name = "form_id")
    val formId: Long
) : BaseEntity()