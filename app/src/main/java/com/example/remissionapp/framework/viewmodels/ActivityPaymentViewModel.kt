package com.example.remissionapp.framework.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.remissionapp.data.network.model.genericResponse
import com.example.remissionapp.data.network.model.paymentBody
import com.example.remissionapp.domain.addPayment
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ActivityPaymentViewModel:ViewModel() {
    val resultLiveData = MutableLiveData<genericResponse?>()
    private val payment = addPayment()

    fun paymentVM(body: paymentBody){
        viewModelScope.launch(Dispatchers.IO){
            val result : genericResponse? = payment(body)
            CoroutineScope(Dispatchers.Main).launch {
                resultLiveData.postValue(result)
            }
        }
    }
}