package com.example.remissionapp.framework.viewmodels

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.remissionapp.data.network.model.idBody
import com.example.remissionapp.data.network.model.remissionList
import com.example.remissionapp.domain.getRemissionList
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlin.math.log

class FragmentActiveViewModel:ViewModel() {
    val remissionListLiveData = MutableLiveData<remissionList?>()
    private val remissionListRequirement = getRemissionList()

    fun getRemissionList(id: Int) {
        val id = idBody(id = id)
        viewModelScope.launch(Dispatchers.IO) {
            val result: remissionList? = remissionListRequirement(id)
            CoroutineScope(Dispatchers.Main).launch {
                Log.d("prueba", "${result}")
                remissionListLiveData.postValue(result)
            }
        }
    }
}