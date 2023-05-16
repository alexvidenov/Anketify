package com.example.anketify.persistence.base

import kotlinx.serialization.Serializable
import javax.persistence.*

@MappedSuperclass
@Serializable
open class BaseEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO) open val id: Long = -1,
)