package com.example.remissionapp.domain

import com.example.remissionapp.data.network.model.remissionBody
import com.example.remissionapp.data.network.model.remissionDetail
import com.example.remissionapp.data.repository

class getRemissiondetail {
    private val repository = repository()

    suspend operator fun  invoke(
        body: remissionBody
    ): remissionDetail? = repository.remissionDetail(body)
}