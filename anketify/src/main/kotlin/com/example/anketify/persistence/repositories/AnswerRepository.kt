package com.example.anketify.persistence.repositories

import com.example.anketify.persistence.entities.AnswerEntity
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface AnswerRepository : CrudRepository<AnswerEntity, Long> {
}