package com.example.remissionapp.framework.views.activities

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.remissionapp.R
import com.example.remissionapp.databinding.ActivityDashboardBinding
import com.example.remissionapp.databinding.ActivityLoginBinding
import com.example.remissionapp.framework.viewmodels.ActivityDashboardViewModel
import com.example.remissionapp.framework.viewmodels.ActivityLoginViewModel
import com.example.remissionapp.framework.views.fragments.ActiveFragment
import com.example.remissionapp.framework.views.fragments.HistoryFragment

class DashboardActivity: AppCompatActivity() {
    private lateinit var binding: ActivityDashboardBinding
    private val viewModel: ActivityDashboardViewModel by viewModels()

    private lateinit var currentFragment: Fragment
    private var currentMenuOption:String?= null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initializeBinding()
        initializeListeners()

        selectMenuOption("active")
    }

    private fun initializeBinding() {
        binding = ActivityDashboardBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    private fun exchangeCurrentFragment(newFragment: Fragment, newMenuOption:String){
        currentFragment = newFragment

        supportFragmentManager.beginTransaction()
            .replace(R.id.nav_host_fragment_content_main,currentFragment)
            .commit()

        currentMenuOption = newMenuOption
    }

    private fun selectMenuOption(menuOption:String){
        if(menuOption == currentMenuOption){
            return
        }

        when(menuOption){
            "history" -> exchangeCurrentFragment(HistoryFragment(),"history")
            "active" -> exchangeCurrentFragment(ActiveFragment(),"active")
        }
    }

    private fun initializeListeners() {
        binding.appBarMain.active.setOnClickListener {
            selectMenuOption("active")
        }

        binding.appBarMain.history.setOnClickListener {
            selectMenuOption("history")
        }
    }
}