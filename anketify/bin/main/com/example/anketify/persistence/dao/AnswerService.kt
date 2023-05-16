package com.example.anketify.persistence.dao

import com.example.anketify.persistence.base.BaseService
import com.example.anketify.persistence.entities.AnswerEntity
import com.example.anketify.persistence.repositories.AnswerRepository
import org.springframework.stereotype.Service

@Service
class AnswerService(answerRepository: AnswerRepository) : BaseService<AnswerEntity>(answerRepository) {
}