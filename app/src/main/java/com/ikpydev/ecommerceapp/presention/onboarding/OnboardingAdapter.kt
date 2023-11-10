package com.ikpydev.ecommerceapp.presention.onboarding

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat.getString
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.ikpydev.ecommerceapp.R
import com.ikpydev.ecommerceapp.databinding.ItemOnboardingPageBinding
import javax.inject.Inject


class OnboardingAdapter : RecyclerView.Adapter<OnboardingAdapter.ViewHolder>() {

    class ViewHolder @Inject constructor(private val binding: ItemOnboardingPageBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(page: Triple<Int, Int, Int>) = with(binding) {

            Glide.with(root.context).load(page.first).into(image)

            title.text = root.context.getString(page.second)
            description.text = root.context.getString(page.third)

        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(
        ItemOnboardingPageBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position])
    }

    companion object {
        private val items = listOf(
            Triple(
                R.drawable.onboarding_image_0,
                R.string.onboarding_title_0,
                R.string.onboarding_description_0
            ),
            Triple(
                R.drawable.onboarding_image_1,
                R.string.onboarding_title_1,
                R.string.onboarding_description_1
            )
        )
    }
}