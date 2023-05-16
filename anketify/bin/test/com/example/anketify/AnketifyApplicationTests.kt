package com.example.anketify

import com.example.anketify.controllers.FormController
import com.example.anketify.models.requests.UserAnswerRequest
import com.example.anketify.persistence.dao.FormService
import com.example.anketify.persistence.dao.UserAnswerService
import com.example.anketify.persistence.entities.FormEntity
import com.example.anketify.persistence.entities.UserAnswerEntity
import com.example.anketify.persistence.repositories.FormRepository
import com.example.anketify.persistence.repositories.UserAnswerRepository
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.Mock
import org.mockito.junit.jupiter.MockitoExtension
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.annotation.DirtiesContext
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.mockito.Mockito.`when`
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import java.util.*

@ExtendWith(MockitoExtension::class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK, classes = [AnketifyApplication::class])
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
internal class AnketifyApplicationTests {
    @Autowired
    private lateinit var mvc: MockMvc

    @Autowired
    private lateinit var formController: FormController

    @Mock
    private lateinit var formRepositoryMock: FormRepository

    private lateinit var formService: FormService

    @BeforeEach
    fun setup() {
        formService = FormService(formRepositoryMock)
        formController = FormController(formService = formService, null, null)
        mvc = MockMvcBuilders.standaloneSetup(formController).build()
    }

    @Test
    fun findForm() {
        val uuid = UUID.randomUUID()
        `when`(formRepositoryMock.findByFormUUID_Uuid(uuid)).thenReturn(FormEntity(name = "Entity"))
        val entity = formService.findByUUID(uuid)
        assert(entity != null)
    }

    @Test
    fun testFormClosed() {
        val uuid = UUID.randomUUID()
        val string = uuid.toString()
        `when`(formRepositoryMock.findByFormUUID_Uuid(uuid)).thenReturn(FormEntity(name = "Entity", isClosed = true))
        mvc.perform(MockMvcRequestBuilders.get("/forms/$string")).andExpect(MockMvcResultMatchers.status().`is`(204))
    }

    @Test
    fun testFormFoundAndNotClosed() {
        val uuid = UUID.randomUUID()
        val string = uuid.toString()
        `when`(formRepositoryMock.findByFormUUID_Uuid(uuid)).thenReturn(FormEntity(name = "Entity"))
        mvc.perform(MockMvcRequestBuilders.get("/forms/$string")).andExpect(MockMvcResultMatchers.status().`is`(200))
    }

    @Test
    fun submitAnswersFailing() {
        val uuid = UUID.randomUUID()
        val string = uuid.toString()
        val answers = listOf(UserAnswerRequest(index = 0, formId = 1, questionId = 1))
        `when`(formRepositoryMock.findById(answers[0].formId)).thenReturn(Optional.of(FormEntity(name = "Entity",
            isClosed = true)))
        mvc.perform(MockMvcRequestBuilders.post("/forms/$string")
            .content(Json.encodeToString(answers)).contentType(MediaType.APPLICATION_JSON))
            .andExpect(MockMvcResultMatchers.status().`is`(204))
    }


}
