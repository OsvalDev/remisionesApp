package com.example.remissionapp.framework.views.adapters.viewHolder

import androidx.recyclerview.widget.RecyclerView
import com.example.remissionapp.data.network.model.remissionNote
import com.example.remissionapp.databinding.ItemNoteBinding

class privatenoteViewHolder (private val binding: ItemNoteBinding) : RecyclerView.ViewHolder(binding.root){
    fun bind(item: remissionNote){
        binding.contenido.text = item.contenido
        binding.usuario.text = item.nombre
    }
}