package com.example.remissionapp.framework.views.fragments

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.remissionapp.data.network.model.remissionItem
import com.example.remissionapp.data.network.model.remissionList
import com.example.remissionapp.databinding.FragmentHistoryBinding
import com.example.remissionapp.framework.viewmodels.FragmentHistoryViewModel
import com.example.remissionapp.framework.views.activities.LoginActivity
import com.example.remissionapp.framework.views.adapters.remissionAdapter

class HistoryFragment : Fragment() {
    private var _binding: FragmentHistoryBinding? = null

    private val binding get() = _binding!!

    private lateinit var viewModel: FragmentHistoryViewModel
    private var id: Int = 0
    private val adapter : remissionAdapter = remissionAdapter()
    private lateinit var data:ArrayList<remissionItem>

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewModel = ViewModelProvider(this)[FragmentHistoryViewModel::class.java]

        _binding = FragmentHistoryBinding.inflate(inflater, container, false)
        val root: View = binding.root

        getPreferences()
        initializeObservers()
        initializelisteners()

        viewModel.getRemissionList(id)
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun getPreferences(){
        val sharedPreferences = requireActivity().getSharedPreferences("remissionPreferences", Context.MODE_PRIVATE)
        id = sharedPreferences.getInt("numWorker", 0)
    }

    private fun initializeObservers() {
        viewModel.remissionListLiveData.observe(viewLifecycleOwner) { remissionList ->
            if (remissionList != null) {
                setUpView(remissionList)
            }
        }
    }

    private fun  initializelisteners(){
        binding.btnLogout.setOnClickListener{
            val sharedPreferences = requireContext().getSharedPreferences("remissionPreferences", Context.MODE_PRIVATE)
            val editor = sharedPreferences.edit()
            editor.remove("numWorker")
            editor.apply()

            val context = requireContext()
            val intent = Intent(context, LoginActivity::class.java)
            startActivity(intent)
            requireActivity().finish()
        }
    }

    private fun setUpView(rList: remissionList?) {
        val itemList: ArrayList<remissionItem> = ArrayList(rList!!.data.map { internList ->
            remissionItem(
                numRemission = internList[0] as String,
                numCompra = internList[1] as String,
                costumer = internList[2] as String,
                remissionMount = internList[3] as Double,
                status = internList[4] as String,
                dateCostumer = internList[5] as String
            )
        })

        setUpRecyclerView(itemList)
    }

    private fun setUpRecyclerView(dataForList:ArrayList<remissionItem>){
        _binding?.remissionRV?.setHasFixedSize(true)

        val linearLayoutManager = LinearLayoutManager(
            requireContext(),
            LinearLayoutManager.VERTICAL,
            false)
        _binding?.remissionRV?.layoutManager = linearLayoutManager
        adapter.remissionAdapter(dataForList, requireContext(), "nofull")
        _binding?.remissionRV?.adapter = adapter
    }
}