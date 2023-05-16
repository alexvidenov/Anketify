package com.example.anketify.persistence.dao

import arrow.core.Option
import arrow.core.Try
import arrow.core.toOption
import com.example.anketify.persistence.base.BaseService
import com.example.anketify.persistence.entities.FormEntity
import com.example.anketify.persistence.entities.UserEntity
import com.example.anketify.persistence.repositories.UserRepository
import com.example.anketify.utils.orThrow
import com.example.anketify.utils.toTry
import com.example.anketify.utils.tryLogger
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service

@Service
class UserService(private val userRepository: UserRepository) : BaseService<UserEntity>(userRepository) {

    fun getByUsernameOptional(username: String): Try<Option<UserEntity>> = tryLogger {
        userRepository.findByUsername(username).toOption()
    }

    fun getByUsername(username: String): UserEntity = userRepository.findByUsername(username)!!

    fun getUserForms(username: String): Try<Option<List<FormEntity>>> {
        return tryLogger {
            val user = getByUsernameOptional(username).flatMap {
                it.toTry { UsernameNotFoundException("User not found: $username") }
            }.orThrow()
            user.forms.toOption()
        }
    }

}