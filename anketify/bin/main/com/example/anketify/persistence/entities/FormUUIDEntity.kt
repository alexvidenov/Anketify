package com.example.anketify.persistence.entities

import com.example.anketify.persistence.base.BaseEntity
import com.example.anketify.utils.UUIDSerializer
import kotlinx.serialization.Serializable
import java.util.*
import javax.persistence.*

@Entity
@Table(name = "form_uuids")
@Serializable
data class FormUUIDEntity(
    @Serializable(with = UUIDSerializer::class) val uuid: UUID,
) : BaseEntity()