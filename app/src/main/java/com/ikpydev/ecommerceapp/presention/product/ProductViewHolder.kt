package com.ikpydev.ecommerceapp.presention.product

import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.ikpydev.ecommerceapp.R
import com.ikpydev.ecommerceapp.data.api.product.dto.Product
import com.ikpydev.ecommerceapp.databinding.ItemProductBinding
import com.ikpydev.ecommerceapp.utils.textStrike

class ProductViewHolder(private val binding: ItemProductBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(
        product: Product,
        onClick: (product: Product) -> Unit,
        wishlist: (product: Product) -> Unit
    ) = with(binding) {
        Glide.with(root).load(product.image).into(image)

        name.text = product.title

        val current = product.price - (product.discount ?: 0.0)

        price.text = root.context.getString(R.string.home_price, current)
        discount.isVisible = product.discount != null

        product.discount?.let {
            val dis_count = ((product.discount / product.price) * 100)
            binding.discount.text = binding.root.context.getString(
                R.string.fragment_product_discountd_off,
                dis_count
            )
        }

        ratingCount.text =
            root.context.getString(R.string.item_product_reting_count, product.ratingCount)
        ratingStart.text = root.context.getString(R.string.home_rating_start, product.rating)

        oldPrice.text = textStrike(product.price.toString())
        oldPrice.isVisible = product.discount != null

        root.setOnClickListener {
            onClick(product)
        }
        fun setLike() {
            val liked =
                if (product.wishlist) R.drawable.ic_heart_cheked else R.drawable.ic_heart_uncheked
            binding.favorite.setImageResource(liked)
        }
        setLike()


        favorite.setOnClickListener {
            product.wishlist = product.wishlist.not()
            setLike()
            wishlist(product)
        }

    }
}