package com.example.anketify.persistence.repositories

import com.example.anketify.persistence.entities.UserEntity
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Component
import org.springframework.stereotype.Repository
import javax.transaction.Transactional

@Repository
interface UserRepository : CrudRepository<UserEntity, Long> {
    fun findByUsername(username: String): UserEntity?
}