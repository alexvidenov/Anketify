package com.example.anketify.models.users

import com.example.anketify.persistence.entities.UserEntity
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.userdetails.UserDetails

typealias PasswordEncoder = (String) -> String

// simply implements the UserDetails interface for the JwtUserDetailsService
class SecurityUser(
    val id: String,
    private val username: String,
    private val password: String,
    private val authorities: List<GrantedAuthority> = emptyList()
) : UserDetails {
    override fun getAuthorities(): Collection<GrantedAuthority> = authorities

    override fun isEnabled(): Boolean = true

    override fun getUsername(): String = username

    override fun isCredentialsNonExpired(): Boolean = true

    override fun getPassword(): String = password

    override fun isAccountNonExpired(): Boolean = true

    override fun isAccountNonLocked(): Boolean = true
}

fun UserEntity.toSecurityUser(): SecurityUser = SecurityUser(
    id.toString(), username, password
)