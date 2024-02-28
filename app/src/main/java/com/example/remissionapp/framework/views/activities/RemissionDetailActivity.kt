package com.example.remissionapp.framework.views.activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.remissionapp.data.network.model.remissionBody
import com.example.remissionapp.data.network.model.remissionDetail
import com.example.remissionapp.databinding.ActivityRemissiondetailBinding
import com.example.remissionapp.framework.viewmodels.ActivityRemissionDetailViewModel
import java.text.SimpleDateFormat
import java.util.Locale
import java.util.TimeZone
import kotlin.math.log

class RemissionDetailActivity: AppCompatActivity() {
    private lateinit var binding: ActivityRemissiondetailBinding
    private val viewModel: ActivityRemissionDetailViewModel by viewModels()

    private var numRemission : String? = ""
    private var numCompra : String? = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initializeBinding()
        initializeObservers()
        initializeListeners()
        manageIntent()
    }

    private fun initializeBinding() {
        binding = ActivityRemissiondetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    private fun initializeObservers(){
        viewModel.remissionDetailLiveData.observe(this) { remissionDetail ->
            if (remissionDetail != null) {
                setUpView(remissionDetail)
            }
        }
    }

    private fun initializeListeners(){
        binding.btnSetpayment.setOnClickListener{ toSetPayment() }
        binding.btnAddNote.setOnClickListener{toAddNote()}
        binding.btnViewNotes.setOnClickListener{toViewNote()}
    }

    private fun toSetPayment(){
        val intent = Intent(this, PaymentActivity::class.java)
        intent.putExtra("numRemission", numRemission)
        intent.putExtra("numCompra", numCompra)
        startActivity(intent)
    }

    private fun toAddNote(){
        val intent = Intent(this, NoteActivity::class.java)
        intent.putExtra("numRemission", numRemission)
        intent.putExtra("numCompra", numCompra)
        startActivity(intent)
    }

    private fun toViewNote(){
        val intent = Intent(this, PrivatenoteActivity::class.java)
        intent.putExtra("numRemission", numRemission)
        intent.putExtra("numCompra", numCompra)
        startActivity(intent)
    }

    private fun setUpView(data : remissionDetail){
        var dateMsg: String = if ( data.data[5] != null ) {
            Log.d("prueba", "paso null")
            formatearFecha(data.data[5].toString())
        } else{
            Log.d("prueba", "else null")
            "Sin fecha asignada"
        }

        binding.nuRemission.text = "#Remision: " + data.data[0].toString()
        binding.numCompra.text = data.data[1].toString()
        binding.nameClient.text = data.data[2].toString()
        binding.mountRemission.text = "$ " + data.data[3].toString()
        binding.status.text = data.data[4].toString()
        binding.date.text = dateMsg
    }

    private fun manageIntent(){
        if(intent != null){
            numRemission = intent.getStringExtra("numRemission")
            numCompra = intent.getStringExtra("numCompra")
            val target =  intent.getStringExtra("target")

            val body = remissionBody(
                numCompra = numCompra as String,
                numRemission = numRemission as String
            )

            viewModel.getRemissionDetail(body)

            if (target != "full"){
                binding.btnSetpayment.visibility = View.GONE
                binding.btnAddNote.visibility = View.GONE
            }
        }
    }

    fun formatearFecha(fechaString: String): String {
        val formatoOriginal = SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss z", Locale.ENGLISH)
        val fecha = formatoOriginal.parse(fechaString)

        val formatoDeseado = SimpleDateFormat("dd / MMM / yyyy", Locale.getDefault())
        formatoDeseado.timeZone = TimeZone.getTimeZone("GMT")
        return formatoDeseado.format(fecha)
    }
}