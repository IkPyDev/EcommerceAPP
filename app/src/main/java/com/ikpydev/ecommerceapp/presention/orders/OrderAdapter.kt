package com.ikpydev.ecommerceapp.presention.orders

import android.content.res.ColorStateList
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.ikpydev.ecommerceapp.R
import com.ikpydev.ecommerceapp.databinding.ItemOrderBinding
import com.ikpydev.ecommerceapp.domain.module.Order

class OrderAdapter(private val track: (order: Order) -> Unit) :
    PagingDataAdapter<Order, OrderAdapter.ViewHolder>(DIFF_UTIL) {

    inner class ViewHolder(
        private val binding: ItemOrderBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(order: Order) = with(binding) {
            title.text = root.context.getString(R.string.item_order_id, order.id)
            count.text = root.context.getString(R.string.order_count, order.items)
            status.text = root.context.getString(order.status)
            val background = ContextCompat.getColor(root.context, order.background)
            image.backgroundTintList = ColorStateList.valueOf(background)
            val foreground = ContextCompat.getColor(root.context, order.foreground)
            image.setColorFilter(foreground)
            steps.adapter = StepsAdapter(order.steps){
                track(order)
            }
            steps.isVisible = order.expanded
            arrow.scaleY = if (order.expanded) -1F else 1F
            root.setOnClickListener {
                order.expanded = order.expanded.not()
                steps.isVisible = order.expanded
                arrow.scaleY = if (order.expanded) -1F else 1F


            }
        }

    }

    companion object {
        val DIFF_UTIL = object : DiffUtil.ItemCallback<Order>() {
            override fun areItemsTheSame(oldItem: Order, newItem: Order) = oldItem == newItem

            override fun areContentsTheSame(oldItem: Order, newItem: Order) = oldItem == newItem
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position) ?: return)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(
        ItemOrderBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )

}