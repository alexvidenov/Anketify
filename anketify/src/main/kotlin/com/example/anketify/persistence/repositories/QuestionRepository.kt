package com.example.anketify.persistence.repositories

import com.example.anketify.persistence.entities.QuestionEntity
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Component
import org.springframework.stereotype.Repository

@Repository
interface QuestionRepository : CrudRepository<QuestionEntity, Long> {
}