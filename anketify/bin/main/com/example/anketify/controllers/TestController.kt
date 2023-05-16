package com.example.anketify.controllers

import com.example.anketify.persistence.dao.UserService
import com.example.anketify.persistence.entities.*
import com.example.anketify.persistence.repositories.AnswerRepository
import com.example.anketify.persistence.repositories.FormRepository
import com.example.anketify.persistence.repositories.QuestionRepository
import com.example.anketify.persistence.repositories.UserAnswerRepository
import com.example.anketify.utils.orThrow
import com.example.anketify.utils.toTry
import org.springframework.http.MediaType
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.web.bind.annotation.*

@CrossOrigin(origins = ["*"], maxAge = 3600)
@RestController
@RequestMapping("/bruh", produces = [MediaType.APPLICATION_JSON_VALUE])
class TestController(
    private val formRepository: FormRepository,
    private val userDao: UserService,
    private val questionRepository: QuestionRepository,
    private val answerRepository: AnswerRepository,
    private val userAnswerRepository: UserAnswerRepository,
) {

    @GetMapping("/bruh")
    fun bruh(): String {
        val user = userDao.getByUsernameOptional("ebasi").flatMap {
            it.toTry { UsernameNotFoundException("User not found") }
        }.orThrow()
        formRepository.save(FormEntity(name = "Form1", userId = user.id!!))
        formRepository.save(FormEntity(name = "Form2", userId = user.id))
        return "BRUH"
    }
}