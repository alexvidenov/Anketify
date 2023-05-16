package com.example.anketify.config

import com.example.anketify.components.JwtAuthenticationEntryPoint
import com.example.anketify.components.JwtRequestFilter
import com.example.anketify.components.JwtTokenUtil
import com.example.anketify.models.users.PasswordEncoder
import com.example.anketify.services.JwtUserDetailsService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.config.BeanIds
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.core.Authentication
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter

typealias SetAuthentication = (Authentication) -> Unit

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
class WebSecurityConfig(
    private val jwtUserDetailsService: JwtUserDetailsService,
    private val jwtTokenUtil: JwtTokenUtil,
) : WebSecurityConfigurerAdapter() {

    @Autowired
    private val jwtAuthenticationEntryPoint: JwtAuthenticationEntryPoint? = null

    companion object {
        private val BCRYPT_ENCODER = BCryptPasswordEncoder()
    }

    @Autowired
    fun configureGlobal(auth: AuthenticationManagerBuilder) {
        auth.userDetailsService(jwtUserDetailsService).passwordEncoder(BCRYPT_ENCODER)
    }

    @Bean
    fun passwordEncoder(): PasswordEncoder = { password -> BCRYPT_ENCODER.encode(password) }

    @Bean
    fun authenticationContext(): SetAuthentication = {
        SecurityContextHolder.getContext().authentication = it
    }

    @Bean(name = [(BeanIds.AUTHENTICATION_MANAGER)])
    override fun authenticationManagerBean(): AuthenticationManager = super.authenticationManagerBean()

    override fun configure(httpSecurity: HttpSecurity) {
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
            .authorizeRequests().antMatchers("/auth/**", "/forms/**").permitAll()
            .anyRequest().authenticated()
            .and()
            .addFilterBefore(
                JwtRequestFilter(jwtTokenUtil, jwtUserDetailsService, authenticationContext()),
                UsernamePasswordAuthenticationFilter::class.java
            )
            .cors().and()
            .csrf()
            .disable().exceptionHandling()
            .authenticationEntryPoint(jwtAuthenticationEntryPoint)
    }
}
