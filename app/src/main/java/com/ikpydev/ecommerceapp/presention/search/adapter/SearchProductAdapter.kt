package com.ikpydev.ecommerceapp.presention.search.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import com.ikpydev.ecommerceapp.data.api.product.dto.Product
import com.ikpydev.ecommerceapp.databinding.ItemProductSearchBinding

class SearchProductAdapter(
    private val onClick: (product: Product) -> Unit,
    private val liked: (product: Product) -> Unit,

    ) : PagingDataAdapter<Product, SearchProductViewHolder>(DIF_UTIL) {

    companion object {
        private val DIF_UTIL = object : DiffUtil.ItemCallback<Product>() {
            override fun areItemsTheSame(oldItem: Product, newItem: Product) =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: Product, newItem: Product) = oldItem == newItem
        }
    }

    override fun onBindViewHolder(holder: SearchProductViewHolder, position: Int) {
        holder.bind(getItem(position) ?: return ,onClick, liked)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = SearchProductViewHolder(
        ItemProductSearchBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )


}


