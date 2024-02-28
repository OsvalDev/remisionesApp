package com.example.remissionapp.framework.views.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.remissionapp.data.network.model.remissionNote
import com.example.remissionapp.databinding.ItemNoteBinding
import com.example.remissionapp.framework.views.adapters.viewHolder.privatenoteViewHolder

class privatenoteAdapter: RecyclerView.Adapter<privatenoteViewHolder>() {
    var data:ArrayList<remissionNote> = ArrayList()
    lateinit var context: Context

    fun privatenoteAdapter(basicData : ArrayList<remissionNote>, context: Context){
        this.data = basicData
        this.context = context
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): privatenoteViewHolder {
        val binding = ItemNoteBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return privatenoteViewHolder(binding)
    }

    override fun onBindViewHolder(holder: privatenoteViewHolder, position: Int) {
        val item = data[position]
        holder.bind(item)
    }

    override fun getItemCount(): Int {
        return data.size
    }
}