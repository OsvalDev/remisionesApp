package com.example.remissionapp.data.network

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

class apiClient {
    private lateinit var api: apiService

    suspend fun login(loginBody: loginBody): loginResponse?{
        api = NetworkModuleDi()
        return try{
            api.login(loginBody)
        }catch (e:java.lang.Exception){
            e.printStackTrace()
            null
        }
    }

    suspend fun remissionList(id: idBody): remissionList?{
        api = NetworkModuleDi()
        return try {
            api.remissionList(id)
        }catch (e:java.lang.Exception){
            e.printStackTrace()
            null
        }
    }

    suspend fun remissionListDone(id: idBody): remissionList?{
        api = NetworkModuleDi()
        return try {
            api.remissionListDone(id)
        }catch (e:java.lang.Exception){
            e.printStackTrace()
            null
        }
    }

    suspend fun remissionDetail(body: remissionBody): remissionDetail?{
        api = NetworkModuleDi()
        return try {
            api.remissionDetail(body)
        }catch (e:java.lang.Exception){
            e.printStackTrace()
            null
        }
    }

    suspend fun addPayment(body: paymentBody): genericResponse?{
        api = NetworkModuleDi()
        return try {
            api.addPayment(body)
        }catch (e:java.lang.Exception){
            e.printStackTrace()
            null
        }
    }

    suspend fun addNote(body: noteBody): genericResponse?{
        api = NetworkModuleDi()
        return try {
            api.addNote(body)
        }catch (e:java.lang.Exception){
            e.printStackTrace()
            null
        }
    }

    suspend fun getNote(body: remissionBody): privatenotes?{
        api = NetworkModuleDi()
        return try {
            api.getNote(body)
        }catch (e:java.lang.Exception){
            e.printStackTrace()
            null
        }
    }
}