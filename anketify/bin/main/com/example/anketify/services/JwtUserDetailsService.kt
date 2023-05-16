package com.example.anketify.services

import com.example.anketify.models.users.toSecurityUser
import com.example.anketify.persistence.dao.UserService
import com.example.anketify.utils.orThrow
import com.example.anketify.utils.toTry
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service

@Service
class JwtUserDetailsService(private val userDao: UserService) : UserDetailsService {

    @Throws(UsernameNotFoundException::class)
    override fun loadUserByUsername(username: String): UserDetails =
        userDao.getByUsernameOptional(username).flatMap {
            // throws if that option value is empty
            it.toTry { UsernameNotFoundException("User $username not found") }
        }.orThrow().toSecurityUser()

}