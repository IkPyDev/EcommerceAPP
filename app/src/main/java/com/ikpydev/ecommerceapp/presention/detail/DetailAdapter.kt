package com.ikpydev.ecommerceapp.presention.detail

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.ikpydev.ecommerceapp.data.api.product.dto.Banner
import com.ikpydev.ecommerceapp.databinding.ItemBannerBinding
import com.ikpydev.ecommerceapp.databinding.ItemDetailImagesBinding

class DetailAdapter(
    private val images: List<String>,
) : RecyclerView.Adapter<DetailAdapter.ViewHolder>() {

    inner class ViewHolder(private val binding: ItemDetailImagesBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(images: String) = with(binding) {

            Glide.with(root).load(images).into(image)


        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(
        ItemDetailImagesBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )


    override fun getItemCount() = images.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(images[position])
    }
}