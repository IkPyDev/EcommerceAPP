package com.ikpydev.ecommerceapp.presention.categories

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.ikpydev.ecommerceapp.R
import com.ikpydev.ecommerceapp.data.api.product.dto.Category
import com.ikpydev.ecommerceapp.databinding.CategoriesFragmentBinding
import com.ikpydev.ecommerceapp.databinding.ItemCategoriesBinding
import kotlin.reflect.KFunction0

class CategoriesAdapter(
    private val category: List<Category>,
    private val onClick:(category:Category)->Unit
) : RecyclerView.Adapter<CategoriesAdapter.ViewHolder>() {

    inner class ViewHolder(private val binding: ItemCategoriesBinding) :
        RecyclerView.ViewHolder(binding.root){
            fun bind(category: Category)= with(binding){
                Glide.with(root).load(category.image).into(image)
                name.text = category.title
                itemCount.text= root.context.getString(R.string.Item_category_count,category.count)
                root.setOnClickListener {
                    onClick(category)
                }
            }
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =  ViewHolder (
        ItemCategoriesBinding.inflate(LayoutInflater.from(parent.context),parent,false)
    )

    override fun getItemCount()= category.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bind(category[position])
}