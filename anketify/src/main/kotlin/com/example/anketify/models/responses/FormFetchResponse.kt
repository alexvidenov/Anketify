package com.example.anketify.models.responses

import com.example.anketify.persistence.entities.FormEntity

sealed class FormFetchResponse {
    data class Fetched(val form: FormEntity) : FormFetchResponse()
    data class FormClosed(val message: String) : FormFetchResponse()
    data class FormDoesNotExist(val message: String) : FormFetchResponse()
}