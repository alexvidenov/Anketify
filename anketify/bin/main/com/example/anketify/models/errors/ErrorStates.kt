package com.example.anketify.models.errors

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

sealed class ErrorStates {
    @ResponseStatus(HttpStatus.CONFLICT)
    object AlreadyRegistered : RuntimeException("User already registered")
}
