package com.example.anketify.components

import com.example.anketify.config.SetAuthentication
import com.example.anketify.services.JwtUserDetailsService
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Component
class JwtRequestFilter(
    private val jwtTokenUtil: JwtTokenUtil,
    private val jwtUserDetailsService: JwtUserDetailsService,
    private val setAuthentication: SetAuthentication
) : OncePerRequestFilter() {

    companion object {
        private const val AUTHORIZATION_HEADER = "Authorization"
        private const val BEARER = "Bearer "
    }

    override fun doFilterInternal(request: HttpServletRequest, response: HttpServletResponse, chain: FilterChain) {
        val token = getJwtToken(request)

        if (token != null) {
            val username = jwtTokenUtil.getUsernameFromToken(token)
            val userDetails = jwtUserDetailsService.loadUserByUsername(username)
            if (jwtTokenUtil.isValidToken(token, userDetails.username)) {
                val authentication =
                    UsernamePasswordAuthenticationToken(userDetails, null, userDetails.authorities)
                setAuthentication(authentication)
            }
        }
        chain.doFilter(request, response)
    }

    private fun getJwtToken(request: HttpServletRequest): String? =
        request.getHeader(AUTHORIZATION_HEADER)?.let { token ->
            if (token.startsWith(BEARER)) {
                token.substring(7)
            } else {
                null
            }
        }
}
