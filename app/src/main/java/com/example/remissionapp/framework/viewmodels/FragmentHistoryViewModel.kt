package com.example.remissionapp.framework.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.remissionapp.data.network.model.idBody
import com.example.remissionapp.data.network.model.remissionList
import com.example.remissionapp.domain.getRemissionListDone
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class FragmentHistoryViewModel : ViewModel() {
    val remissionListLiveData = MutableLiveData<remissionList?>()
    private val remissionListRequirement = getRemissionListDone()

    fun getRemissionList(id: Int) {
        val id = idBody(id = id)
        viewModelScope.launch(Dispatchers.IO) {
            val result: remissionList? = remissionListRequirement(id)
            CoroutineScope(Dispatchers.Main).launch {
                remissionListLiveData.postValue(result)
            }
        }
    }
}