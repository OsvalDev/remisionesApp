package com.example.remissionapp.framework.views.activities

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import android.provider.MediaStore
import android.util.Base64
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.result.contract.ActivityResultContracts.*
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.remissionapp.R
import com.example.remissionapp.data.network.model.genericResponse
import com.example.remissionapp.data.network.model.paymentBody
import com.example.remissionapp.databinding.ActivityPaymentBinding
import com.example.remissionapp.framework.viewmodels.ActivityPaymentViewModel
import com.github.gcacace.signaturepad.views.SignaturePad
import java.io.ByteArrayOutputStream

class PaymentActivity : AppCompatActivity() {
    private lateinit var binding: ActivityPaymentBinding
    private val viewModel: ActivityPaymentViewModel by viewModels()
    private var numRemission : String? = ""
    private var numCompra : String? = ""
    private var id: Int = 0
    private var signeded = false
    private var entregaUploaded = false
    private var ineUploaded = false

    val pickMedia = registerForActivityResult(PickVisualMedia()) {uri ->
        if ( uri != null ) {
            binding.imgEntrega.setImageURI(uri)
            entregaUploaded = true
        } else {
            entregaUploaded = false
        }
    }
    val pickMediaIne = registerForActivityResult(PickVisualMedia()) {uri ->
        if ( uri != null ) {
            binding.imgIne.setImageURI(uri)
            ineUploaded = true
        } else {
            ineUploaded = false
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initializeBinding()
        manageIntent()
        getPreferences()
        initializeObservers()
        initializeListeners()

    }
    private fun initializeBinding() {
        binding = ActivityPaymentBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    private fun initializeObservers(){
        viewModel.resultLiveData.observe(this) {paymentResponse ->
            if (paymentResponse != null)
                manageResponse(paymentResponse)
        }
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

    private fun initializeListeners(){
        binding.btnReset.setOnClickListener{
            binding.signaturePad.clear()
        }

        binding.btnAcept.setOnClickListener{
            binding.btnAcept.isEnabled = false
            preparePayment()
        }

        binding.btnEntregaImg.setOnClickListener{
            pickMedia.launch(PickVisualMediaRequest(PickVisualMedia.ImageOnly))
        }

        binding.btnIneImg.setOnClickListener{
            pickMediaIne.launch(PickVisualMediaRequest(PickVisualMedia.ImageOnly))
        }

        binding.signaturePad.setOnSignedListener(object : SignaturePad.OnSignedListener {
            override fun onStartSigning() {
                signeded = true
            }

            override fun onSigned() {

            }

            override fun onClear() {
                signeded = false
            }
        })



    }

    private fun preparePayment() {

        if (!signeded || binding.etxtMount.text.toString() == "" || binding.etxtName.text.toString() == "" || !ineUploaded || !entregaUploaded) {
            Toast.makeText(this, "Llene todos los campos", Toast.LENGTH_SHORT).show()
            return
        }

        val signatureBitmap = binding.signaturePad.getTransparentSignatureBitmap()
        val byteArrayOutputStream = ByteArrayOutputStream()
        signatureBitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream)
        val byteArray = byteArrayOutputStream.toByteArray()
        val encodedImage = Base64.encodeToString(byteArray, Base64.DEFAULT)

        val bitmapEntrega = (binding.imgEntrega.drawable as BitmapDrawable).bitmap
        val byteArrayOutputStreamEntrega = ByteArrayOutputStream()
        bitmapEntrega.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStreamEntrega)
        val byteArrayEntrega = byteArrayOutputStreamEntrega.toByteArray()
        val encodedImageEntrega = Base64.encodeToString(byteArrayEntrega, Base64.DEFAULT)

        val bitmapIne = (binding.imgIne.drawable as BitmapDrawable).bitmap
        val byteArrayOutputStreamIne = ByteArrayOutputStream()
        bitmapIne.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStreamIne)
        val byteArrayIne = byteArrayOutputStreamIne.toByteArray()
        val encodedImageIne = Base64.encodeToString(byteArrayIne, Base64.DEFAULT)

        var chbxCalidadVal: Boolean = false
        var chbxPiezasVal: Boolean = false

        if (binding.chbxCalidad.isChecked) chbxCalidadVal = true
        if (binding.chbxPiezas.isChecked) chbxPiezasVal = true

        val body = paymentBody(
            cantidad = binding.etxtMount.text.toString().toInt(),
            img = encodedImage,
            numCompra = numCompra as String,
            numRemission = numRemission as String,
            pagoPersona = binding.etxtName.text.toString(),
            responsable = id,
            imgEntrega = encodedImageEntrega,
            imgIne = encodedImageIne,
            calidadVal = chbxCalidadVal,
            cantidadVal = chbxPiezasVal
        )

        viewModel.paymentVM(body)
    }

    private fun manageResponse(result : genericResponse){
        val builder = AlertDialog.Builder(this)

        if (result.result == "success") {
            builder.setTitle("Pago registrado correctamente")
                .setMessage("El pago se ha registrado con éxito.")
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
            binding.btnAcept.isEnabled = true
            builder.setTitle("Error")
                .setMessage(result.msg)
                .setPositiveButton("Aceptar", null)
        }

        val alertDialog: AlertDialog = builder.create()
        alertDialog.show()
    }

}