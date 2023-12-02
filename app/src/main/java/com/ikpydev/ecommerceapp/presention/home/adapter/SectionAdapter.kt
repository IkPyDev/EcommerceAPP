package com.ikpydev.ecommerceapp.presention.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ikpydev.ecommerceapp.data.api.product.dto.Product
import com.ikpydev.ecommerceapp.data.api.product.dto.Section
import com.ikpydev.ecommerceapp.data.api.product.dto.SectionType
import com.ikpydev.ecommerceapp.databinding.ItemSectionHorizantalBinding
import com.ikpydev.ecommerceapp.databinding.ItemSectionVerticalBinding

class SectionAdapter(
    private val section: List<Section>,
    private val showAll: (section: Section) -> Unit,
    private val onClick: (product: Product) -> Unit,
    private val wishlist: (product: Product) -> Unit
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    inner class HorizontalHolder(private val binding: ItemSectionHorizantalBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(section: Section) = with(binding) {

            title.text = section.title
            showAll.setOnClickListener {

                this@SectionAdapter.showAll(section)

            }
            products.adapter = HorizontalAdapter(section.products, onClick, wishlist)

        }
    }

    inner class VerticalHolder(private val binding: ItemSectionVerticalBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(section: Section) = with(binding) {

            title.text = section.title
            showAll.setOnClickListener {

                this@SectionAdapter.showAll(section)

            }
            products.adapter = VerticalAdapter(section.products, onClick, wishlist)


        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return when (SectionType.values()[viewType]) {
            SectionType.horizontal -> HorizontalHolder(
                ItemSectionHorizantalBinding.inflate(
                    inflater,
                    parent,
                    false
                )
            )

            SectionType.vertical -> VerticalHolder(
                ItemSectionVerticalBinding.inflate(
                    inflater,
                    parent,
                    false
                )
            )
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is HorizontalHolder -> holder.bind(section[position])
            is VerticalHolder -> holder.bind(section[position])

        }
    }

    override fun getItemViewType(position: Int) = section[position].type.ordinal
    override fun getItemCount() = section.size
}



