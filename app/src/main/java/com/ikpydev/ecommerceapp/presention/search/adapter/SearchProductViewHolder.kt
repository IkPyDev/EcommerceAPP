package com.ikpydev.ecommerceapp.presention.search.adapter

import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.ikpydev.ecommerceapp.R
import com.ikpydev.ecommerceapp.data.api.product.dto.Product
import com.ikpydev.ecommerceapp.databinding.ItemProductBinding
import com.ikpydev.ecommerceapp.databinding.ItemProductSearchBinding

class SearchProductViewHolder(private val binding: ItemProductSearchBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(
        product: Product,
        onClick: (product: Product) -> Unit,
        liked: (product: Product) -> Unit
    ) = with(binding) {
        Glide.with(root).load(product.image).into(image)

        name.text = product.title

        val current = product.price - (product.discount ?: 0.0)

        price.text = root.context.getString(R.string.home_price, current)


        ratingCount.text =
            root.context.getString(R.string.item_product_reting_count, product.ratingCount)
        ratingStart.text = root.context.getString(R.string.home_rating_start, product.rating)

        oldPrice.text = root.context.getString(R.string.home_price, product.price)
        oldPrice.isVisible = product.discount != null

        root.setOnClickListener {
            onClick(product)
        }

    }

}
