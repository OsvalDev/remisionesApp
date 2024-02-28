package com.example.remissionapp.framework.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.remissionapp.data.network.model.genericResponse
import com.example.remissionapp.data.network.model.noteBody
import com.example.remissionapp.domain.addNote
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ActivityNoteViewModel: ViewModel() {
    val resultLiveData = MutableLiveData<genericResponse?>()
    private val payment = addNote()

    fun noteVM(body: noteBody){
        viewModelScope.launch(Dispatchers.IO){
            val result : genericResponse? = payment(body)
            CoroutineScope(Dispatchers.Main).launch {
                resultLiveData.postValue(result)
            }
        }
    }
}