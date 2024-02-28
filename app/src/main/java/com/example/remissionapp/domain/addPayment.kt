package com.example.remissionapp.domain

import com.example.remissionapp.data.network.model.genericResponse
import com.example.remissionapp.data.network.model.paymentBody
import com.example.remissionapp.data.repository

class addPayment {
    private val repository = repository()

    suspend operator fun  invoke(
        body: paymentBody
    ): genericResponse? = repository.addPayment(body)
}