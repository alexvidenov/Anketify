package com.example.anketify.components.authEvents

import arrow.core.Option
import arrow.core.Try
import arrow.core.failure
import com.example.anketify.models.errors.ErrorStates
import com.example.anketify.persistence.dao.UserService
import com.example.anketify.persistence.entities.UserEntity
import org.springframework.stereotype.Component

@Component
class RegisterUser(private val userDao: UserService) {
    operator fun invoke(userEntity: UserEntity): Try<UserEntity> =
        userDao.getByUsernameOptional(userEntity.username).flatMap(createIfNotRegistered(userEntity))

    private fun createIfNotRegistered(userEntity: UserEntity): (Option<UserEntity>) -> Try<UserEntity> = {
        it.fold({
            userDao.create(userEntity)
        }, {
            ErrorStates.AlreadyRegistered.failure()
        })
    }
}