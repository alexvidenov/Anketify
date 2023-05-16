package com.example.anketify.persistence.repositories

import com.example.anketify.persistence.entities.UserAnswerEntity
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Component
import org.springframework.stereotype.Repository

@Repository
interface UserAnswerRepository : CrudRepository<UserAnswerEntity, Long> {
}