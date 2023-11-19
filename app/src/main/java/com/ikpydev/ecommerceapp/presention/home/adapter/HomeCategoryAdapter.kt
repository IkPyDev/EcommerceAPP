package com.ikpydev.ecommerceapp.presention.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.ikpydev.ecommerceapp.R
import com.ikpydev.ecommerceapp.data.api.product.dto.Category
import com.ikpydev.ecommerceapp.databinding.ItemCategoriesHomeBinding

class HomeCategoryAdapter(
    private val categories: List<Category>,
    private val onCategoryClick: (category: Category) -> Unit
):RecyclerView.Adapter<HomeCategoryAdapter.ViewHolder>() {

    inner class ViewHolder(private val binding: ItemCategoriesHomeBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(categories: Category) = with(binding){

            Glide.with(root).load(categories.image).into(image)
            name.text = categories.title
            itemCount.text= root.context.getString(R.string.Item_category_count,categories.count)

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(
        ItemCategoriesHomeBinding.inflate(LayoutInflater.from(parent.context),parent,false)
    )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(categories[position])
    }

    override fun getItemCount()= categories.size


}