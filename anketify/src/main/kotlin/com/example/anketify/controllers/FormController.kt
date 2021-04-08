package com.example.anketify.controllers

import com.example.anketify.models.requests.UserAnswerRequest
import com.example.anketify.models.responses.FormFetchResponse
import com.example.anketify.persistence.dao.FormService
import com.example.anketify.persistence.dao.IpAnswerService
import com.example.anketify.persistence.dao.UserAnswerService
import com.example.anketify.persistence.entities.FormEntity
import com.example.anketify.persistence.entities.IpAnswerEntity
import com.example.anketify.persistence.entities.UserAnswerEntity
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.*
import javax.servlet.http.HttpServletRequest

@CrossOrigin(origins = ["*"], maxAge = 3600)
@RestController
@RequestMapping("/forms")
class FormController(
    private val formService: FormService,
    private val userAnswerService: UserAnswerService,
    private val ipAnswerService: IpAnswerService,
) : AuthAwareController() {

    @GetMapping(value = ["/{formUUID}"])
    fun getForm(@PathVariable formUUID: String, request: HttpServletRequest): ResponseEntity<FormFetchResponse> {
        print("UUID: $formUUID")
        val form = formService.findByUUID(UUID.fromString(formUUID))
        return if (form != null) {
            if (form.isClosed == true) {
                ResponseEntity(FormFetchResponse.FormClosed("Whoops, seems like the form you're looking for is closed"),
                    HttpStatus.NO_CONTENT)
            } else {
                ResponseEntity(FormFetchResponse.Fetched(form = form),
                    HttpStatus.OK)
            }
        } else {
            ResponseEntity(FormFetchResponse.FormDoesNotExist(message = "Form does not exist!"),
                HttpStatus.NOT_FOUND)
        }
    }

    @PostMapping(value = ["/{formUUID}"])
    fun submitForm(
        @PathVariable formUUID: String,
        @RequestBody userAnswers: List<UserAnswerRequest>,
        request: HttpServletRequest,
    ): ResponseEntity<Boolean> {
        val ip =
            request.remoteAddr + request.remoteHost + request.remotePort
        val form = formService.getByIdUnsafe(userAnswers[0].formId)
        return if (form != null) {
            if (!form.ipAnswers.any { it.ip == ip }) {
                userAnswers.forEach {
                    userAnswerService.create(UserAnswerEntity(index = it.index,
                        questionId = it.questionId,
                        formId = it.formId, ip = ip))
                }
                ipAnswerService.create(IpAnswerEntity(ip = ip, formId = form.id))
                ResponseEntity(true, HttpStatus.OK)
            } else ResponseEntity(false, HttpStatus.FORBIDDEN)
        } else ResponseEntity(false, HttpStatus.NOT_FOUND)
    }

    @GetMapping(value = ["/public"])
    fun getPublicForms(): ResponseEntity<List<FormEntity>> =
        ResponseEntity(formService.findPublic() ?: listOf(), HttpStatus.OK)

}