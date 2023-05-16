package com.example.anketify.components

import com.example.anketify.models.users.SecurityUser
import org.springframework.security.core.Authentication
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Component

@Component
class AuthContext : AuthFacade {
    override fun getAuth(): Authentication = SecurityContextHolder.getContext().authentication

    override fun currentUser(): String =
        (SecurityContextHolder.getContext().authentication.principal as SecurityUser).username
}