package com.example.remissionapp.data

import com.example.remissionapp.data.network.apiClient
import com.example.remissionapp.data.network.model.genericResponse
import com.example.remissionapp.data.network.model.idBody
import com.example.remissionapp.data.network.model.loginBody
import com.example.remissionapp.data.network.model.loginResponse
import com.example.remissionapp.data.network.model.noteBody
import com.example.remissionapp.data.network.model.paymentBody
import com.example.remissionapp.data.network.model.privatenotes
import com.example.remissionapp.data.network.model.remissionBody
import com.example.remissionapp.data.network.model.remissionDetail
import com.example.remissionapp.data.network.model.remissionList

class repository {
    private val api = apiClient()

    suspend fun login(loginBody: loginBody):loginResponse? = api.login(loginBody)
    suspend fun remissionList(id: idBody) : remissionList? = api.remissionList(id)
    suspend fun remissionListDone(id: idBody) : remissionList? = api.remissionListDone(id)
    suspend fun remissionDetail(body: remissionBody): remissionDetail? = api.remissionDetail(body)
    suspend fun addPayment(body: paymentBody) : genericResponse? = api.addPayment(body)
    suspend fun addNote(body: noteBody) : genericResponse? = api.addNote(body)
    suspend fun getNote(body: remissionBody): privatenotes? = api.getNote(body)
}