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
import retrofit2.http.Body
import retrofit2.http.POST

interface apiService {
    //https://cardscarnival.com/registro/loginApp
    @POST("loginApp")
    suspend fun login(
        @Body requestBody: loginBody
    ) : loginResponse

    //https://cardscarnival.com/registro/remissionList
    @POST("remissionList")
    suspend fun remissionList(
        @Body requestBody: idBody
    ) : remissionList

    //https://cardscarnival.com/registro/remissionListDone
    @POST("remissionListDone")
    suspend fun remissionListDone(
        @Body requestBody: idBody
    ) : remissionList

    //https://cardscarnival.com/registro/remissionDetail
    @POST("remissionDetail")
    suspend fun remissionDetail(
        @Body requestBody: remissionBody
    ) : remissionDetail

    //https://cardscarnival.com/registro/payment
    @POST("payment")
    suspend fun addPayment(
        @Body requestBody: paymentBody
    ) : genericResponse

    //https://cardscarnival.com/registro/note
    @POST("note")
    suspend fun addNote(
        @Body requestBody: noteBody
    ) : genericResponse

    //https://cardscarnival.com/registro/notePrivate
    @POST("notePrivate")
    suspend fun getNote(
        @Body requestBody: remissionBody
    ) : privatenotes

}