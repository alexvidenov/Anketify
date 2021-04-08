package com.example.anketify.persistence.repositories

import com.example.anketify.persistence.entities.FormEntity
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import java.util.*
import javax.transaction.Transactional

@Repository
interface FormRepository : CrudRepository<FormEntity, Long> {
    fun findByFormUUID_Uuid(uuid: UUID): FormEntity?

    fun findByIsPublicAndIsClosedIsFalse(isPublic: Boolean): List<FormEntity>?
}