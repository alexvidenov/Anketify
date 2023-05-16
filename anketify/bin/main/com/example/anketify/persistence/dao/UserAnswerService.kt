package com.example.anketify.persistence.dao

import com.example.anketify.persistence.base.BaseService
import com.example.anketify.persistence.entities.UserAnswerEntity
import com.example.anketify.persistence.repositories.UserAnswerRepository
import org.springframework.stereotype.Service

@Service
class UserAnswerService(userAnswerRepository: UserAnswerRepository) :
    BaseService<UserAnswerEntity>(userAnswerRepository) {
}