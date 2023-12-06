package com.ikpydev.ecommerceapp.presention.orders

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.ikpydev.ecommerceapp.databinding.ItemStepBinding
import com.ikpydev.ecommerceapp.domain.module.Steps

class StepsAdapter(private val steps: List<Steps>) :
    RecyclerView.Adapter<StepsAdapter.ViewHolder>() {

    inner class ViewHolder(private val binding: ItemStepBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(steps: Steps, dividerVisible: Boolean) = with(binding) {
            icon.setImageResource(steps.icon)
            title.text = root.context.getString(steps.title)
            data.text = steps.data
            divider.isVisible = dividerVisible
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(
        ItemStepBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )

    override fun getItemCount() = steps.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(steps[position], dividerVisible = position < steps.size - 1)
    }
}