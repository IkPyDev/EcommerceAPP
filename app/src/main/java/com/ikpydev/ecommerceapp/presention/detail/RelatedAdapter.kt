package com.ikpydev.ecommerceapp.presention.detail

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.ikpydev.ecommerceapp.data.api.product.dto.Product
import com.ikpydev.ecommerceapp.databinding.ItemProductRelatedBinding
import com.ikpydev.ecommerceapp.utils.textStrike

class RelatedAdapter(
    private val product: List<Product>,
    private val onClick: (product: Product) -> Unit
) : RecyclerView.Adapter<RelatedAdapter.ViewHolder>() {


    inner class ViewHolder(private val binding: ItemProductRelatedBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(product: Product) = with(binding) {
            Glide.with(root).load(product.image).into(image)


            val current = product.price - (product.discount ?: 0.0)

            price.text = "$$current"


            title.text = product.title

            oldPrice.isVisible = product.discount != null
            oldPrice.text = textStrike(product.price.toString())

            root.setOnClickListener {
                onClick(product)
            }


        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(
        ItemProductRelatedBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )

    override fun getItemCount() = product.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(product[position])
    }
}

