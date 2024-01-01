package com.ikpydev.ecommerceapp.presention.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ikpydev.ecommerceapp.data.api.product.dto.Product
import com.ikpydev.ecommerceapp.databinding.ItemProductBinding
import com.ikpydev.ecommerceapp.presention.product.ProductViewHolder


class VerticalAdapter(
    private val product: List<Product>,
    private val onClick: (product: Product) -> Unit,
    private val liked: (product: Product) -> Unit
) : RecyclerView.Adapter<ProductViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ProductViewHolder(
        ItemProductBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
    )

    override fun getItemCount() = product.size

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        holder.bind(product[position],onClick, liked)
    }


}