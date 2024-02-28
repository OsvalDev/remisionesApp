package com.example.remissionapp.domain

import com.example.remissionapp.data.network.model.loginBody
import com.example.remissionapp.data.network.model.loginResponse
import com.example.remissionapp.data.repository

class loginUS {
    private  val repository = repository()

    suspend operator fun invoke (
        loginBody: loginBody
    ): loginResponse? = repository.login(loginBody)
}