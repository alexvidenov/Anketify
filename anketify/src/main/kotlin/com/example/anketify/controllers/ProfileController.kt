package com.example.anketify.controllers

import com.example.anketify.models.requests.FormRequest
import com.example.anketify.models.requests.FormUpdateRequest
import com.example.anketify.models.responses.FormURLResponse
import com.example.anketify.persistence.dao.AnswerService
import com.example.anketify.persistence.dao.FormService
import com.example.anketify.persistence.dao.UserService
import com.example.anketify.persistence.entities.AnswerEntity
import com.example.anketify.persistence.entities.FormEntity
import com.example.anketify.persistence.entities.FormUUIDEntity
import com.example.anketify.persistence.entities.QuestionEntity
import com.example.anketify.persistence.transient.UserAnswerAggregation
import com.example.anketify.utils.FetchUtils
import com.example.anketify.utils.orThrow
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.*

@CrossOrigin(origins = ["*"], maxAge = 3600)
@RestController
@RequestMapping("/profile")
class ProfileController(
    private val userService: UserService,
    private val formService: FormService,
    private val answerService: AnswerService,
) : AuthAwareController() {

    companion object {
        private const val BASE_URL = "http://localhost:8081/"
    }

    @GetMapping(value = ["/forms"])
    fun getForms(): ResponseEntity<List<FormEntity>> {
        FetchUtils.getModelSafe(userService.getUserForms(authContext.currentUser()), {
            return ResponseEntity(it, HttpStatus.OK)
        }, {
            return ResponseEntity(listOf(), HttpStatus.OK)
        })
    }

    @PostMapping(value = ["/createForm"])
    fun createForm(@RequestBody formRequest: FormRequest): ResponseEntity<FormURLResponse> {
        val questionsEntity = formRequest.questions.map { q ->
            QuestionEntity(description = q.description,
                imageDescription = q.imageDescription,
                optional = q.optional,
                canSelectMoreThanOne = q.canSelectMoreThanOne)
        }

        val formEntity = FormEntity(name = formRequest.name,
            isClosed = formRequest.isClosed,
            isPublic = formRequest.isPublic,
            questions = questionsEntity)

        val uuid = UUID.randomUUID()

        val user = userService.getByUsername(authContext.currentUser())
        val form =
            formService.create(formEntity.copy(userId = user.id, formUUID = FormUUIDEntity(uuid = uuid))).orThrow()

        form.questions.forEach { q ->
            val question = formRequest.questions.first { it.description == q.description }
            question.answers.forEach {
                answerService.create(AnswerEntity(description = it, question_id = q.id))
            }
        }

        return ResponseEntity(FormURLResponse(formUrl = "${BASE_URL}forms/${uuid}"), HttpStatus.CREATED)
    }

    @PatchMapping(value = ["/form/{formId}"])
    fun updateForm(
        @PathVariable formId: Long,
        @RequestBody formUpdateRequest: FormUpdateRequest,
    ): ResponseEntity<Boolean> =
        FetchUtils.getModelSafe(formService.getById(formId), {
            return if (userService.getByUsername(authContext.currentUser()).id == it.userId) {
                it.isClosed = formUpdateRequest.isClosed
                it.isPublic = formUpdateRequest.isPublic
                formService.update(it)
                ResponseEntity(true, HttpStatus.OK)
            } else ResponseEntity(false, HttpStatus.FORBIDDEN)
        }, {
            return ResponseEntity(false, HttpStatus.NOT_FOUND)
        })

    @GetMapping(value = ["/form/{formId}/results"])
    fun getFormResults(@PathVariable formId: Long): ResponseEntity<UserAnswerAggregation> {
        val currentUser = userService.getByUsername(authContext.currentUser())
        FetchUtils.getModelSafe(formService.getById(formId), { form ->
            if (currentUser.id == form.userId!!) {
                val questionAnswers =
                    mutableMapOf<Long, MutableList<Int>>() // question_id -> list of indexes as answers
                val aggregatedAnswers = mutableMapOf<Long, Int>()
                val formQuestions = form.questions
                form.userAnswers.forEach { answer ->
                    val index = answer.index
                    val qAnswer = questionAnswers[answer.questionId]
                    if (qAnswer !== null) {
                        qAnswer.apply {
                            add(index)
                        }
                    } else {
                        questionAnswers[answer.questionId] = mutableListOf(index)
                    }
                }
                questionAnswers.forEach { qa ->
                    val question = formQuestions.first { it.id == qa.key }
                    val answers = question.answers
                    for (i in answers.indices) {
                        aggregatedAnswers[answers[i].id] = Collections.frequency(qa.value, i)
                    }
                }
                return ResponseEntity(UserAnswerAggregation(aggregatedUserAnswers = aggregatedAnswers),
                    HttpStatus.OK)
            } else {
                return ResponseEntity(UserAnswerAggregation(),
                    HttpStatus.FORBIDDEN)
            }
        }, {
            return ResponseEntity(UserAnswerAggregation(), HttpStatus.OK)
        })
    }
}