package com.example.remissionapp.framework.views.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.remissionapp.data.network.model.loginBody
import com.example.remissionapp.data.network.model.loginResponse
import com.example.remissionapp.databinding.ActivityLoginBinding
import com.example.remissionapp.framework.viewmodels.ActivityLoginViewModel

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private val viewModel: ActivityLoginViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initializeBinding()
        initializeObservers()
        initializeListeners()
        manageAutoLogin()
    }

    private fun initializeBinding() {
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    private fun initializeObservers(){
        viewModel.resultLiveData.observe(this) {loginResponse ->
            if (loginResponse != null)
                manageLogin(loginResponse)
        }
    }

    private fun initializeListeners(){
        binding.btnLogin.setOnClickListener{ sendLogin() }
    }

    private fun sendLogin() {
        val numWorker = binding.etxtNumWorker.text.toString()
        val psw = binding.etxtPsw.text.toString()

        val data = loginBody(id = numWorker, psw = psw)
        viewModel.loginVM(data)
    }

    private fun manageLogin(result: loginResponse){
        if ( result.result == "success" ){
            //save session
            val numWorker = binding.etxtNumWorker.text.toString().toInt()
            val sharedPreferences = getSharedPreferences("remissionPreferences", Context.MODE_PRIVATE)
            val editor = sharedPreferences.edit()
            editor.putInt("numWorker", numWorker)
            editor.apply()

            val intent = Intent(this, DashboardActivity::class.java)
            startActivity(intent)
            finish()
        } else{
            Toast.makeText(this, result.msg, Toast.LENGTH_SHORT).show()
        }
    }

    private fun manageAutoLogin(){
        val sharedPreferences = getSharedPreferences("remissionPreferences", Context.MODE_PRIVATE)
        val id = sharedPreferences.getInt("numWorker", -1)
        if (id != -1){
            val intent = Intent(this, DashboardActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}