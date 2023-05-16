package com.example.anketify.persistence.base

import arrow.core.Option
import arrow.core.Try
import arrow.core.toOption
import com.example.anketify.utils.tryLogger
import org.springframework.data.repository.CrudRepository

abstract class BaseService<T : BaseEntity>(private val repository: CrudRepository<T, Long>) {
    fun create(entity: T): Try<T> = tryLogger {
        repository.save(entity)
    }

    fun update(entity: T): Try<T> = tryLogger {
        repository.save(entity)
    }

    fun getById(id: Long): Try<Option<T>> = tryLogger {
        repository.findById(id).orElse(null).toOption()
    }

    fun getByIdUnsafe(id: Long): T? = repository.findById(id).orElse(null)
}