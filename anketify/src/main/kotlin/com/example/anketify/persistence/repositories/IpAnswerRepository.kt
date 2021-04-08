package com.example.anketify.persistence.repositories

import com.example.anketify.persistence.entities.IpAnswerEntity
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface IpAnswerRepository : CrudRepository<IpAnswerEntity, Long> {
}