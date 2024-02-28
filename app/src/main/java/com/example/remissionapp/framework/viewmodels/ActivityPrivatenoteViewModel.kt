package com.example.remissionapp.framework.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.remissionapp.data.network.model.privatenotes
import com.example.remissionapp.data.network.model.remissionBody
import com.example.remissionapp.data.network.model.remissionDetail
import com.example.remissionapp.domain.getNoteUS
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ActivityPrivatenoteViewModel:ViewModel() {
    val privateNoteLiveData = MutableLiveData<privatenotes?>()
    private val privateNoteRequirement = getNoteUS()

    fun getPrivatenotes(body : remissionBody) {
        viewModelScope.launch(Dispatchers.IO) {
            val result: privatenotes? = privateNoteRequirement(body)

            CoroutineScope(Dispatchers.Main).launch {
                privateNoteLiveData.postValue(result)
            }
        }
    }
}