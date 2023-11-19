package com.ikpydev.ecommerceapp.presention.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.ikpydev.ecommerceapp.R
import com.ikpydev.ecommerceapp.data.api.product.dto.Banner
import com.ikpydev.ecommerceapp.data.api.product.dto.Category
import com.ikpydev.ecommerceapp.data.api.product.dto.Product
import com.ikpydev.ecommerceapp.data.api.product.dto.Section
import com.ikpydev.ecommerceapp.databinding.HomeFragmentBinding
import com.ikpydev.ecommerceapp.presention.home.adapter.BannerAdapter
import com.ikpydev.ecommerceapp.presention.home.adapter.HomeCategoryAdapter
import com.ikpydev.ecommerceapp.presention.home.adapter.SectionAdapter
import com.zhpan.indicator.enums.IndicatorSlideMode
import com.zhpan.indicator.enums.IndicatorStyle
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private val viewModel by viewModels<HomeViewModel>()

    private lateinit var binding: HomeFragmentBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = HomeFragmentBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        subscribeToLiveData()
        initUi()
    }

    private fun initUi() = with(binding) {

        retry.setOnClickListener {
            viewModel.getHome()
        }

        indicator.apply {
            val normalColor = ContextCompat.getColor(requireContext(), R.color.indicator_unchecked)
            val checkedColor = ContextCompat.getColor(requireContext(), R.color.indicator_checked)
            setSliderColor(normalColor, checkedColor)
            setSliderWidth(resources.getDimension(R.dimen.dp_20))
            setSliderHeight(resources.getDimension(R.dimen.dp_4))
            setSlideMode(IndicatorSlideMode.WORM)
            setIndicatorStyle(IndicatorStyle.CIRCLE)
            notifyDataChanged()
        }
    }

    private fun subscribeToLiveData() = with(binding) {
        viewModel.loading.observe(viewLifecycleOwner) {
            progress.isVisible = it

        }
        viewModel.error.observe(viewLifecycleOwner) {
            error.isVisible = it
        }
        viewModel.home.observe(viewLifecycleOwner) {
            home.isVisible = it != null
            it ?: return@observe



            val name = it.user.firstName ?: it.user.username
            greeting.text = getString(R.string.home_greeting, name)
            Glide.with(root.context).load(it.user.avatar).into(avatar)

            banners.adapter = BannerAdapter(it.banners, this@HomeFragment::onBannerClick)
            indicator.setupWithViewPager(banners)
            indicator.apply {
                notifyDataChanged()
            }

            categories.adapter =
                HomeCategoryAdapter(it.categories, this@HomeFragment::onCategoryClick)
            Toast.makeText(root.context, "${it.sections.size}", Toast.LENGTH_SHORT).show()

            section.adapter = SectionAdapter(
                it.sections,
                this@HomeFragment::showAll,
                this@HomeFragment::onClickProduct,
                this@HomeFragment::liked
            )


        }
    }

    private fun onBannerClick(banner: Banner) {

    }

    private fun onCategoryClick(category: Category) {

    }

    private fun showAll(section: Section) {

    }

    private fun onClickProduct(product: Product) {

    }

    private fun liked(product: Product) {

    }
}