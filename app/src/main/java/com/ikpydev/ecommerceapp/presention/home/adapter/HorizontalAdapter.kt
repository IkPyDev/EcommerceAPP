package com.ikpydev.ecommerceapp.presention.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.ikpydev.ecommerceapp.R
import com.ikpydev.ecommerceapp.data.api.product.dto.Product
import com.ikpydev.ecommerceapp.databinding.ItemProductHorizontalSectionBinding


class HorizontalAdapter(
    private val product: List<Product>,
    private val onClick: (product: Product) -> Unit,
    private val liked: (product: Product) -> Unit
) : RecyclerView.Adapter<HorizontalAdapter.ViewHolder>() {

    inner class ViewHolder(private val binding: ItemProductHorizontalSectionBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(product: Product) = with(binding) {
            Glide.with(root).load(product.image).into(image)
            name.text = product.title

            val current = product.price - (product.discount ?: 0.0)
            price.text = root.context.getString(R.string.home_price, current)
            discount.isVisible = product.discount != null

            product.discount?.let {
                val pree = ((product.discount / product.price) * 100)
                binding.discount.text = root.context.getString(
                    R.string.fragment_product_discountd_off,
                    pree
                )
            }

            ratingCount.text = root.context.getString(R.string.item_product_reting_count,product.ratingCount)
            ratingStart.text = root.context.getString(R.string.home_rating_start,product.rating)

            oldPrice.text = root.context.getString(R.string.home_price, product.price)
            oldPrice.isVisible = product.discount != null

            root.setOnClickListener {
                onClick(product)
            }
            fun setLike() {
                val liked =
                    if (product.favorite) R.drawable.ic_heart_cheked else R.drawable.ic_heart_uncheked
                like.setImageResource(liked)
            }
            setLike()


            like.setOnClickListener {
                product.favorite = product.favorite.not()
                setLike()
                liked(product)
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(
        ItemProductHorizontalSectionBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
    )

    override fun getItemCount() = product.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(product[position])
    }


}