package com.example.anketify.components

import org.springframework.security.core.Authentication

interface AuthFacade {
    fun getAuth(): Authentication

    fun currentUser() : String
}