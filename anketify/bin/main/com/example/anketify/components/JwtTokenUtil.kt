package com.example.anketify.components

import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import java.io.Serializable
import java.util.*
import java.util.function.Function

@Component
class JwtTokenUtil : Serializable {
    @Value("\${jwt.secret}")
    private val secret: String? = null

    companion object {
        private val SIGNATURE_ALGORITHM = SignatureAlgorithm.HS512
        const val JWT_TOKEN_VALIDITY = 5 * 1000 * 1000.toLong()
    }

    private fun isTokenExpired(token: String?): Boolean {
        val expiration = getExpirationDateFromToken(token)
        return expiration.before(Date())
    }

    fun <T> getClaimFromToken(token: String?, claimsResolver: Function<Claims, T>): T {
        val claims = getAllClaimsFromToken(token)
        return claimsResolver.apply(claims)
    }

    fun getUsernameFromToken(token: String?): String = getClaimFromToken(token) { obj: Claims -> obj.subject }

    private fun getAllClaimsFromToken(token: String?): Claims =
        Jwts.parser().setSigningKey(secret).parseClaimsJws(token).body

    fun getExpirationDateFromToken(token: String?): Date = getClaimFromToken(token) { obj: Claims -> obj.expiration }

    fun generateToken(claims: Map<String, Any>, username: String): String =
        Jwts.builder()
            .setClaims(claims)
            .setSubject(username)
            .setIssuedAt(now())
            .setExpiration(getExpirationDate())
            .signWith(SIGNATURE_ALGORITHM, this.secret)
            .compact()

    fun isValidToken(token: String, username: String): Boolean {
        val user = getUsernameFromToken(token)
        return user == username && !isTokenExpired(token)
    }

    private fun now() = Date(System.currentTimeMillis())

    private fun getExpirationDate() = Date(now().time + JWT_TOKEN_VALIDITY * 1000)

}
