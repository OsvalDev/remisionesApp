package com.example.remissionapp.framework.views.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.remissionapp.data.network.model.genericResponse
import com.example.remissionapp.data.network.model.noteBody
import com.example.remissionapp.databinding.ActivityNoteBinding
import com.example.remissionapp.framework.viewmodels.ActivityNoteViewModel

class NoteActivity : AppCompatActivity() {
    private lateinit var binding: ActivityNoteBinding
    private val viewModel: ActivityNoteViewModel by viewModels()

    private var numRemission : String? = ""
    private var numCompra : String? = ""
    private var id: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initializeBinding()
        manageIntent()
        getPreferences()
        initializeObservers()
        initializeListeners()

    }

    private fun initializeBinding() {
        binding = ActivityNoteBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
    private fun manageIntent(){
        if(intent != null){
            numRemission = intent.getStringExtra("numRemission")
            numCompra = intent.getStringExtra("numCompra")
        }
    }
    private fun getPreferences(){
        val sharedPreferences = getSharedPreferences("remissionPreferences", Context.MODE_PRIVATE)
        id = sharedPreferences.getInt("numWorker", 0)
    }

    private fun initializeObservers(){
        viewModel.resultLiveData.observe(this) {noteResponse ->
            if (noteResponse != null){
                manageResponse(noteResponse)
            }
        }
    }

    private fun initializeListeners(){
        binding.btnAcept.setOnClickListener { sendNote() }
    }

    private fun sendNote(){
        val content = binding.content.text.toString()
        val body = noteBody(
            id = id,
            numCompra = numCompra as String,
            numRemission = numRemission as String,
            content = content
        )

        viewModel.noteVM(body)
    }

    private fun manageResponse(result : genericResponse){
        val builder = AlertDialog.Builder(this)

        if (result.result == "success") {
            builder.setTitle("Nota registrado correctamente")
                .setMessage("La nota se ha registrado con éxito.")
                .setPositiveButton("Aceptar") { _, _ ->

                    val intent = Intent(this, RemissionDetailActivity::class.java)
                    intent.putExtra("numRemission", numRemission)
                    intent.putExtra("numCompra", numCompra)
                    intent.putExtra("target", "full")
                    startActivity(intent)
                    finish()
                }
                .setCancelable(false)
        } else {

            builder.setTitle("Error")
                .setMessage(result.msg)
                .setPositiveButton("Aceptar", null)
        }

        val alertDialog: AlertDialog = builder.create()
        alertDialog.show()
    }
}