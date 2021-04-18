package com.example.anketify.controllers

import com.example.anketify.components.authEvents.LoginUser
import com.example.anketify.components.authEvents.RegisterUser
import com.example.anketify.models.requests.UserRequest
import com.example.anketify.models.requests.toEntity
import com.example.anketify.models.responses.UserResponse
import com.example.anketify.models.users.PasswordEncoder
import com.example.anketify.utils.orThrow
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.web.bind.annotation.*

@CrossOrigin(origins = ["*"], maxAge = 3600)
@RestController
@RequestMapping("/auth")
class JwtAuthenticationController(
    private val loginUser: LoginUser,
    private val registerUser: RegisterUser,
    private val passwordEncoder: PasswordEncoder,
) {

    @PostMapping("/register")
    fun register(@RequestBody request: UserRequest): ResponseEntity<UserResponse> {
        val user = request.toEntity(passwordEncoder)
        return registerUser(user).map {
            val userPass = UsernamePasswordAuthenticationToken(request.username, request.password)
            val token = loginUser(userPass).orThrow()
            ResponseEntity(UserResponse(username = it.username, token = token), HttpStatus.CREATED)
        }.orThrow()
    }

    @PostMapping("/login")
    private fun authenticate(@RequestBody request: UserRequest): UserResponse {
        val userPass = UsernamePasswordAuthenticationToken(request.username, request.password)
        return UserResponse(username = request.username, token = loginUser(userPass).orThrow())
    }
}
