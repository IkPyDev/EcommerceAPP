package com.ikpydev.ecommerceapp.presention.search.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ikpydev.ecommerceapp.databinding.ItemsRecentBinding

class RecentsAdapter(private val resents :List<String>, private val onClick:(search:String)->Unit):RecyclerView.Adapter<RecentsAdapter.ViewHolder>() {
    inner class ViewHolder(private val binding:ItemsRecentBinding):RecyclerView.ViewHolder(binding.root){
        fun bind(search: String){
            binding.resent.text = search
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(
        ItemsRecentBinding.inflate(LayoutInflater.from(parent.context),parent,false)
    )

    override fun getItemCount() = resents.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(resents[position])
    }
}