package com.example.anketify.ui

import org.junit.jupiter.api.AfterAll

import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test

class SeleniumWithJunitLiveTest {

    companion object {
        @JvmStatic
        private lateinit var authPage: AuthPage

        @JvmStatic
        @BeforeAll
        fun setUp() {
            authPage = AuthPage()
        }

        @JvmStatic
        @AfterAll
        fun tearDown() {
            authPage.closeWindow()
        }
    }

    @Test
    fun registerCreateFormLogoutLoginAndSeeForms() {
        authPage.register()
        authPage.createAndSubmitForm("Form 1")
        authPage.logout()
        authPage.goToPublicForms()
        val forms = authPage.getPublicForms()
        assert(forms.size == 1)
        assert(forms[0] == "Form 1")
        authPage.goToLogin()
        authPage.login()
        val answerButtons = authPage.getFormAnswerButtons()
        assert(answerButtons.size == 1)
        assert(answerButtons[0] == "See answers for Form 1")
        authPage.createAndSubmitForm("Form 2")
        authPage.goToPublicForms()
        val newForms = authPage.getPublicForms()
        assert(newForms.size == 2)
        assert(newForms[0] == "Form 1")
        assert(newForms[1] == "Form 2")
        authPage.goToYourForms()
        authPage.updateFormClosed("Form 1")
        authPage.logout()
        authPage.goToPublicForms()
        val updatedForms = authPage.getPublicForms()
        assert(updatedForms.size == 1)
        assert(updatedForms[0] == "Form 2")
    }

}