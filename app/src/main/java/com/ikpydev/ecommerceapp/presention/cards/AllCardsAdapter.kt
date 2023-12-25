package com.ikpydev.ecommerceapp.presention.cards

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ikpydev.ecommerceapp.databinding.ItemAllCardsBinding
import com.ikpydev.ecommerceapp.domain.module.Card

class AllCardsAdapter(
    private val cards: List<Card>,
    private val onClick: (card: Card) -> Unit
) : RecyclerView.Adapter<AllCardsAdapter.ViewHolder>() {

    inner class ViewHolder(private val binding: ItemAllCardsBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(card: Card) = with(binding) {
            cardNumber.text = card.cardNumber
            data.text = card.data
            name.text = card.cardName

            root.setOnClickListener {
                onClick(card)
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(
        ItemAllCardsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )


    override fun getItemCount() = cards.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(cards[position])
    }
}