package com.example.remissionapp.framework.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.remissionapp.data.network.model.loginBody
import com.example.remissionapp.data.network.model.loginResponse
import com.example.remissionapp.domain.loginUS
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.Dispatcher

class ActivityLoginViewModel : ViewModel() {
    val resultLiveData = MutableLiveData<loginResponse?>()
    private val login = loginUS()

    fun loginVM(loginBody: loginBody){
        viewModelScope.launch(Dispatchers.IO){
            val result : loginResponse? = login(loginBody)
            CoroutineScope(Dispatchers.Main).launch {
                resultLiveData.postValue(result)
            }
        }
    }
}