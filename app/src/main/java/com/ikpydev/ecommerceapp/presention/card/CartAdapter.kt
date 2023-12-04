package com.ikpydev.ecommerceapp.presention.card

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.ikpydev.ecommerceapp.R
import com.ikpydev.ecommerceapp.databinding.ItemsCardViewBinding
import com.ikpydev.ecommerceapp.domain.module.Cart
import com.ikpydev.ecommerceapp.utils.dp

class CartAdapter(
    private val carts: List<Cart>,
    private val onClick: (cart: Cart) -> Unit,
    private val set: (cart: Cart) -> Unit
) : RecyclerView.Adapter<CartAdapter.ViewHolder>() {

    inner class ViewHolder(private val binding: ItemsCardViewBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(cart: Cart) = with(binding) {
            Glide.with(root).load(cart.image).transform(CenterCrop(), RoundedCorners(14.dp))
                .into(image)
            title.text = cart.title
            category.text = cart.category
            price.text = root.context.getString(R.string.price, cart.price - (cart.discount ?: 0.0))
            oldPrice.text = root.context.getString(R.string.home_price, cart.price)
            oldPrice.isVisible = cart.discount != null
            item.text = cart.count.toString()

            root.setOnClickListener {
                onClick(cart)
            }
            plus.setOnClickListener {
                if (cart.count == cart.stock) return@setOnClickListener
                cart.count++
                item.text = cart.count.toString()
                set(cart)
            }
            minus.setOnClickListener {
                cart.count--
                item.text = cart.count.toString()
                set(cart)
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(
        ItemsCardViewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )

    override fun getItemCount() = carts.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(carts[position])
    }
}