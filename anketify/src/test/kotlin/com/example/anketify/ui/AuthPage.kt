package com.example.anketify.ui

import org.openqa.selenium.By
import org.openqa.selenium.WebDriver
import org.openqa.selenium.support.ui.ExpectedConditions
import org.openqa.selenium.support.ui.WebDriverWait

class AuthPage {
    private val seleniumConfig = SeleniumConfig()
    private val driver: WebDriver = seleniumConfig.driver
    private val url = "http://localhost:3000"

    init {
        driver.get(url)
    }

    fun closeWindow() {
        driver.close()
    }

    private fun goToCreateNewForm() {
        val createFormElement = driver.findElement(By.id("Create a new form"))
        createFormElement.click()
    }

    fun goToYourForms() {
        val formsElement = driver.findElement(By.id("Your forms"))
        formsElement.click()
    }

    fun createAndSubmitForm(formDescription: String) {
        val wait = WebDriverWait(driver, 5)
        wait.until(ExpectedConditions.presenceOfElementLocated(By.id("Create a new form")))
        goToCreateNewForm()
        createAnswers("Question 1", listOf("Answer 3", "Answer 2", "Answer 1"))
        createAnswers("Question 2", listOf("Some other Answer 3", "Some other Answer 2", "Some other Answer 1"))
        submitForm(formDescription)
    }

    fun getFormAnswerButtons(): List<String> = driver.findElements(By.id("answersEntry")).map {
        it.text
    }

    fun updateFormClosed(formName: String) {
        val formElement = driver.findElement(By.id("formEntry_${formName}"))
        formElement.click()
        val formClosedElement = driver.findElement(By.id("formClosed"))
        formClosedElement.click()
        val updateFormElement = driver.findElement(By.id("updateForm"))
        updateFormElement.click()
    }

    fun goToPublicForms() {
        val publicFormsButton = driver.findElement(By.id("Public forms"))
        publicFormsButton.click()
    }

    fun register() {
        val moveToRegisterButton = driver.findElement(By.id("navigateToRegister"))
        moveToRegisterButton.click()
        authFlow("username", "password", "signup", "parakatowski", "parakatowski02")
    }

    fun logout() {
        val logoutButton = driver.findElement(By.id("logout"))
        logoutButton.click()
    }

    fun goToLogin() {
        val loginButton = driver.findElement(By.id("auth"))
        loginButton.click()
    }

    fun login() {
        authFlow("username", "password", "login", "parakatowski", "parakatowski02")
    }

    private fun createAnswers(description: String, answers: List<String>) {
        val createAnswerButton = driver.findElement(By.id("addQuestion"))
        createAnswerButton.click()
        val wait = WebDriverWait(driver, 5)
        wait.until(ExpectedConditions.presenceOfElementLocated(By.id("questionDescriptionField")))
        val descriptionElement = driver.findElement(By.id("questionDescriptionField"))
        descriptionElement.sendKeys(description)

        val addFirstButton = driver.findElement(By.id("addFirstAnswer"))

        for (a in answers.indices) {
            if (a == 0) {
                addFirstButton.click()
            } else {
                val addAnswerButton = driver.findElement(By.id("addAnswer"))
                addAnswerButton.click()
            }
            val answerInput = driver.findElement(By.id("answer_0"))
            answerInput.sendKeys(answers[a])
        }

        val submitQuestion = driver.findElement(By.id("submitQuestion"))
        submitQuestion.click()
    }

    private fun submitForm(formDescription: String) {
        val formDescriptionElement = driver.findElement(By.id("formDescription"))
        formDescriptionElement.sendKeys(formDescription)

        val isPublicElement = driver.findElement(By.id("isPublicChecked"))
        isPublicElement.click()

        val submitFormElement = driver.findElement(By.id("submitForm"))
        submitFormElement.click()
    }

    fun getPublicForms(): List<String> = driver.findElements(By.id("formEntry")).map {
        it.text
    }

    private fun authFlow(
        username: String,
        password: String,
        authButton: String,
        usernameToInput: String,
        passwordToInput: String,
    ) {
        val usernameField = driver.findElement(By.id(username))
        val passwordField = driver.findElement(By.id(password))
        usernameField.sendKeys(usernameToInput)
        passwordField.sendKeys(passwordToInput)
        val authButtonComponent = driver.findElement(By.id(authButton))
        authButtonComponent.click()
    }

}