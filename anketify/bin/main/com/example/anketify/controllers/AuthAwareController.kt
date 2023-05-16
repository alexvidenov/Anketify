package com.example.anketify.controllers

import com.example.anketify.components.AuthContext
import org.springframework.beans.factory.annotation.Autowired

abstract class AuthAwareController {
    @Autowired
    lateinit var authContext: AuthContext
}