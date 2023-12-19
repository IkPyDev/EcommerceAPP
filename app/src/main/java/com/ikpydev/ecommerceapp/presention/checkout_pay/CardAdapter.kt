package com.ikpydev.ecommerceapp.presention.checkout_pay

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ikpydev.ecommerceapp.databinding.ViewCardBinding
import com.ikpydev.ecommerceapp.domain.module.Card

class CardAdapter(
    private val cards: List<Card>,
    private val onClick: (card: Card) -> Unit
) : RecyclerView.Adapter<CardAdapter.ViewHolder>() {

    inner class ViewHolder(private val binding: ViewCardBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(card: Card) = with(binding) {

            val day = if (card.data.length == 4 && card.data.all { it.isDigit() }) {
                card.data.substring(0, 2) + "/" + card.data.substring(2)
            } else {
                card.data
            }

            name.text = card.cardName
            data.text = day
            val enteredText = card.cardNumber

            // Katta xarf kiritilgan bo'lsa, oldiga " " qo'shish
            val number = if (enteredText.all { it.isDigit() }) {
                val formattedText =
                    enteredText.replace("\\s".toRegex(), "") // Probellarni olib tashlash
                if (formattedText.length >= 4) {
                    val spacedText = formattedText.chunked(4).joinToString(" ")
                    spacedText
                } else {
                    formattedText
                }
            } else {
                enteredText
            }
            cardNumber.text = number




            root.setOnClickListener {
                onClick(card)
            }

        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ViewCardBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemCount() = cards.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bind(cards[position])

}