package com.example.remissionapp.domain

import com.example.remissionapp.data.network.model.genericResponse
import com.example.remissionapp.data.network.model.noteBody
import com.example.remissionapp.data.network.model.paymentBody
import com.example.remissionapp.data.repository

class addNote {
    private val repository = repository()

    suspend operator fun  invoke(
        body: noteBody
    ): genericResponse? = repository.addNote(body)
}