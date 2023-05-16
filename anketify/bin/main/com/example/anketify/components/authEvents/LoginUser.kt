package com.example.anketify.components.authEvents

import arrow.core.Try
import com.example.anketify.components.JwtTokenUtil
import com.example.anketify.config.SetAuthentication
import com.example.anketify.utils.tryLogger
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.stereotype.Component

@Component
class LoginUser(
    private val authenticationManager: AuthenticationManager,
    private val jwtTokenUtil: JwtTokenUtil,
    private val setAuthentication: SetAuthentication
) {
    operator fun invoke(userPass: UsernamePasswordAuthenticationToken): Try<String> = tryLogger {
        val auth = authenticationManager.authenticate(userPass)
        setAuthentication(auth)
        val claims: Map<String, Any> = HashMap()
        jwtTokenUtil.generateToken(claims, userPass.principal.toString())
    }
}