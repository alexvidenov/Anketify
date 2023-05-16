package com.example.anketify.persistence.dao

import com.example.anketify.persistence.base.BaseService
import com.example.anketify.persistence.entities.QuestionEntity
import com.example.anketify.persistence.repositories.QuestionRepository
import org.springframework.stereotype.Service

@Service
class QuestionService(questionRepository: QuestionRepository) : BaseService<QuestionEntity>(questionRepository) {
}