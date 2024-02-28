package com.example.remissionapp.framework.views.adapters.viewHolder

import android.content.Context
import android.content.Intent
import androidx.recyclerview.widget.RecyclerView
import com.example.remissionapp.data.network.model.remissionItem
import com.example.remissionapp.databinding.ItemRemissionBinding
import com.example.remissionapp.framework.views.activities.RemissionDetailActivity

class remissionViewHolder(private val binding: ItemRemissionBinding) : RecyclerView.ViewHolder(binding.root) {
    fun bind(item: remissionItem, context: Context, target: String){
        var remisionText = "Remision: #" + item.numRemission
        var compraText = "Compra: #" + item.numCompra
        binding.remission.text = remisionText
        binding.compra.text = compraText

        binding.itemContainer.setOnClickListener {
            val intent = Intent(context, RemissionDetailActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP)
            intent.putExtra("numRemission", item.numRemission)
            intent.putExtra("numCompra", item.numCompra)
            intent.putExtra("target", target)
            context.startActivity(intent)
        }
    }
}