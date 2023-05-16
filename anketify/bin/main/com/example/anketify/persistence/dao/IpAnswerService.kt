package com.example.anketify.persistence.dao

import com.example.anketify.persistence.base.BaseService
import com.example.anketify.persistence.entities.IpAnswerEntity
import com.example.anketify.persistence.repositories.IpAnswerRepository
import org.springframework.stereotype.Service

@Service
class IpAnswerService(ipAnswerRepository: IpAnswerRepository) : BaseService<IpAnswerEntity>(ipAnswerRepository) {
}