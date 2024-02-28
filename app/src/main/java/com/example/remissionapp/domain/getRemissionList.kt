package com.example.remissionapp.domain

import com.example.remissionapp.data.network.model.idBody
import com.example.remissionapp.data.network.model.remissionList
import com.example.remissionapp.data.repository

class getRemissionList {
    private val repository = repository()

    suspend operator fun  invoke(
        id: idBody
    ): remissionList? = repository.remissionList(id)
}