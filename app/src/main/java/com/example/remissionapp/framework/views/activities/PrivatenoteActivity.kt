package com.example.remissionapp.framework.views.activities

import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.remissionapp.data.network.model.privatenotes
import com.example.remissionapp.data.network.model.remissionBody
import com.example.remissionapp.data.network.model.remissionDetail
import com.example.remissionapp.data.network.model.remissionItem
import com.example.remissionapp.data.network.model.remissionNote
import com.example.remissionapp.databinding.ActivityPrivatenoteBinding
import com.example.remissionapp.framework.viewmodels.ActivityPrivatenoteViewModel
import com.example.remissionapp.framework.views.adapters.privatenoteAdapter
import com.example.remissionapp.framework.views.adapters.remissionAdapter

class PrivatenoteActivity: AppCompatActivity() {
    private lateinit var binding: ActivityPrivatenoteBinding
    private  val viewModel: ActivityPrivatenoteViewModel by viewModels()

    private var numRemission : String? = ""
    private var numCompra : String? = ""

    private val adapter : privatenoteAdapter = privatenoteAdapter()
    private lateinit var data:ArrayList<remissionNote>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initializeBinding()
        initializeObservers()
        manageIntent()
    }
    private fun initializeBinding() {
        binding = ActivityPrivatenoteBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    private fun initializeObservers(){
        viewModel.privateNoteLiveData.observe(this) { privateNotes ->
            if (privateNotes != null) {
                setUpView(privateNotes)
            }else{
                Log.d("prueba", "private notes es null")
            }
        }
    }

    private fun setUpView(noteList : privatenotes?){
        Log.d("prueba", "Este es la data ${noteList}")
        val itemList: ArrayList<remissionNote> = ArrayList(noteList!!.data.map { internList ->
            remissionNote(
                nombre = internList[0],
                contenido = internList[1]
            )
        })
        setUpRecyclerView(itemList)
    }

    private fun manageIntent(){
        if(intent != null) {
            numRemission = intent.getStringExtra("numRemission")
            numCompra = intent.getStringExtra("numCompra")

            val body = remissionBody(
                numCompra = numCompra as String,
                numRemission = numRemission as String
            )
            viewModel.getPrivatenotes(body)
        }
    }

    private fun setUpRecyclerView(dataForList:ArrayList<remissionNote>){
        binding?.notesRV?.setHasFixedSize(true)

        val linearLayoutManager = LinearLayoutManager(
            this,
            LinearLayoutManager.VERTICAL,
            false)
        binding.notesRV.layoutManager = linearLayoutManager
        adapter.privatenoteAdapter(dataForList, this)
        binding.notesRV.adapter = adapter
    }

}