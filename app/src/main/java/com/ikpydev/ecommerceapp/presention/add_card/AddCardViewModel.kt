package com.ikpydev.ecommerceapp.presention.add_card

import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import android.widget.TextView
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ikpydev.ecommerceapp.domain.module.Card
import com.ikpydev.ecommerceapp.domain.repo.OrderRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddCardViewModel @Inject constructor(private val orderRepository: OrderRepository) :
    ViewModel() {

    fun setCard(card: Card) = viewModelScope.launch(Dispatchers.IO) {

        orderRepository.setCard(card)

    }


    fun editTextControl(edit: EditText, text: TextView, id: String? = null) {
        edit.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(
                charSequence: CharSequence?,
                start: Int,
                count: Int,
                after: Int
            ) {

            }

            override fun onTextChanged(
                charSequence: CharSequence?,
                start: Int,
                before: Int,
                count: Int
            ) {

            }

            override fun afterTextChanged(editable: Editable?) {

                if (id == "CARD_NAME") {
                    // Katta xarf kiritilgan bo'lsa, oldiga " " qo'shish
                    val newText = editable.toString()

                    // Kiritilgan matnni textView ga joylash
                    if (newText.isBlank() || newText.isNotEmpty()) {
                        text.text = newText
                    } else text.text = " "
                } else if (id == "CARD_NUMBER") {
                    val enteredText = editable.toString()

                    // Katta xarf kiritilgan bo'lsa, oldiga " " qo'shish
                    val newText = if (enteredText.all { it.isDigit() }) {
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


                    text.text = newText

                } else {
                    val enteredText = editable.toString()

                    // Katta xarf kiritilgan bo'lsa, oldiga " " qo'shish
                    val newText = if (enteredText.length == 4 && enteredText.all { it.isDigit() }) {
                        enteredText.substring(0, 2) + "/" + enteredText.substring(2)
                    } else {
                        enteredText
                    }


                    text.text = newText

                }
            }
        })
    }
}


