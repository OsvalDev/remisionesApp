package com.example.remissionapp.framework.views.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.remissionapp.data.network.model.remissionItem
import com.example.remissionapp.databinding.ItemRemissionBinding
import com.example.remissionapp.framework.views.adapters.viewHolder.remissionViewHolder

class remissionAdapter: RecyclerView.Adapter<remissionViewHolder>() {
    var data:ArrayList<remissionItem> = ArrayList()
    lateinit var context: Context
    var target = ""

    fun remissionAdapter(basicData : ArrayList<remissionItem>, context: Context, target: String){
        this.data = basicData
        this.context = context
        this.target = target
    }

    override fun onBindViewHolder(holder: remissionViewHolder, position: Int) {
        val item = data[position]
        holder.bind(item, context, target)
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): remissionViewHolder {
        val binding = ItemRemissionBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return remissionViewHolder(binding)
    }
    override fun getItemCount(): Int {
        return data.size
    }
}