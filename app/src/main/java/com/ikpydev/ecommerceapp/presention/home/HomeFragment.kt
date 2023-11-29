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
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.widget.ViewPager2
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
import com.ikpydev.ecommerceapp.utils.BaseFragment
import com.ikpydev.ecommerceapp.utils.HorizontalMarginItemDecoration
import com.ikpydev.ecommerceapp.utils.setLightStatusBar
import com.zhpan.indicator.enums.IndicatorSlideMode
import com.zhpan.indicator.enums.IndicatorStyle
import dagger.hilt.android.AndroidEntryPoint
import kotlin.math.abs

@AndroidEntryPoint
class HomeFragment : BaseFragment<HomeFragmentBinding>(HomeFragmentBinding::inflate) {

    private val viewModel by viewModels<HomeViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        subscribeToLiveData()
        initUi()
    }

    private fun initUi() = with(binding) {
        setLightStatusBar()
        error.retry.setOnClickListener {
            viewModel.getHome()
        }
        indicator.apply {
            val normalColor = ContextCompat.getColor(requireContext(), R.color.indicator_unchecked)
            val checkedColor = ContextCompat.getColor(requireContext(), R.color.indicator_checked)
            setSliderColor(normalColor, checkedColor)
            setSliderWidth(resources.getDimension(R.dimen.dp_20))
            setSliderHeight(resources.getDimension(R.dimen.dp_4))
            setSlideMode(IndicatorSlideMode.WORM)
            setIndicatorStyle(IndicatorStyle.ROUND_RECT)
            notifyDataChanged()
        }

        banners.offscreenPageLimit = 1

// Add a PageTransformer that translates the next and previous items horizontally
// towards the center of the screen, which makes them visible
        val nextItemVisiblePx = resources.getDimension(R.dimen.viewpager_next_item_visible)
        val currentItemHorizontalMarginPx =
            resources.getDimension(R.dimen.viewpager_current_item_horizontal_margin)
        val pageTranslationX = nextItemVisiblePx + currentItemHorizontalMarginPx
        val pageTransformer = ViewPager2.PageTransformer { page: View, position: Float ->
            page.translationX = -pageTranslationX * position
            // Next line scales the item's height. You can remove it if you don't want this effect
            page.scaleY = 1 - (0.25f * abs(position))
            // If you want a fading effect uncomment the next line:
            // page.alpha = 0.25f + (1 - abs(position))
        }
        banners.setPageTransformer(pageTransformer)

// The ItemDecoration gives the current (centered) item horizontal margin so that
// it doesn't occupy the whole screen width. Without it the items overlap
        val itemDecoration = HorizontalMarginItemDecoration(
            requireContext(),
            R.dimen.viewpager_current_item_horizontal_margin
        )
        banners.addItemDecoration(itemDecoration)
    }

    private fun subscribeToLiveData() = with(binding) {
        viewModel.loading.observe(viewLifecycleOwner) {
            loading.root.isVisible = it

        }
        viewModel.error.observe(viewLifecycleOwner) {
            error.root.isVisible = it
        }
        viewModel.home.observe(viewLifecycleOwner) {
            home.isVisible = it != null
            it ?: return@observe


            val name = it.user.firstName ?: it.user.username
            greeting.text = getString(R.string.home_greeting, name)
            count.text = it.notification_count.toString()
            Glide.with(root.context).load(it.user.avatar).into(avatar)

            banners.adapter = BannerAdapter(it.banners, this@HomeFragment::onBannerClick)

            showAll.setOnClickListener {
                findNavController().navigate(HomeFragmentDirections.toCategoriesFragment())
            }
            searchContainer.search.setOnFocusChangeListener { view, focused ->
                if (focused.not()) return@setOnFocusChangeListener
                findNavController().navigate(HomeFragmentDirections.toSearchFragment())

            }
            indicator.setupWithViewPager(banners)
            indicator.apply {
                notifyDataChanged()
            }

            categories.adapter =
                HomeCategoryAdapter(it.categories, this@HomeFragment::onCategoryClick)

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
        findNavController().navigate(HomeFragmentDirections.toProductFragment(category))

    }

    private fun showAll(section: Section) {

    }

    private fun onClickProduct(product: Product) {
        findNavController().navigate(HomeFragmentDirections.toDetailFragment(product.id))
    }

    private fun liked(product: Product) {

    }
}