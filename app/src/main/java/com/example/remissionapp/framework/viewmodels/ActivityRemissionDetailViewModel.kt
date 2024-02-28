package com.example.remissionapp.framework.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.remissionapp.data.network.model.remissionBody
import com.example.remissionapp.data.network.model.remissionDetail
import com.example.remissionapp.domain.getRemissiondetail
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ActivityRemissionDetailViewModel:ViewModel() {
    val remissionDetailLiveData = MutableLiveData<remissionDetail?>()
    private val remissionDetailRequirement = getRemissiondetail()

    fun getRemissionDetail(body : remissionBody) {
        viewModelScope.launch(Dispatchers.IO) {
            val result: remissionDetail? = remissionDetailRequirement(body)

            CoroutineScope(Dispatchers.Main).launch {
                remissionDetailLiveData.postValue(result)
            }
        }
    }
}