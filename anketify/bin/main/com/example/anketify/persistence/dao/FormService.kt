package com.example.anketify.persistence.dao

import com.example.anketify.persistence.base.BaseService
import com.example.anketify.persistence.entities.FormEntity
import com.example.anketify.persistence.repositories.FormRepository
import org.springframework.stereotype.Service
import java.util.*

@Service
class FormService(private val formRepository: FormRepository) : BaseService<FormEntity>(formRepository) {
    fun findByUUID(uuid: UUID): FormEntity? = formRepository.findByFormUUID_Uuid(uuid)

    fun findPublic(): List<FormEntity>? = formRepository.findByIsPublicAndIsClosedIsFalse(isPublic = true)
}