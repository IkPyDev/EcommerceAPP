package com.ikpydev.ecommerceapp.presention.detail

import android.os.Bundle
import android.view.View
import androidx.core.app.ShareCompat
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.google.android.material.snackbar.Snackbar
import com.ikpydev.ecommerceapp.R
import com.ikpydev.ecommerceapp.common.Constats
import com.ikpydev.ecommerceapp.data.api.product.dto.Product
import com.ikpydev.ecommerceapp.databinding.DetailFragmentBinding
import com.ikpydev.ecommerceapp.utils.BaseFragment
import com.zhpan.indicator.enums.IndicatorSlideMode
import com.zhpan.indicator.enums.IndicatorStyle
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailFragment : BaseFragment<DetailFragmentBinding>(DetailFragmentBinding::inflate) {

    private val viewModel by viewModels<DetailViewModel>()
    private val args by navArgs<DetailFragmentArgs>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.getProduct(args.id)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUi()
        subscribeToLiveData()
    }

    private fun initUi() = with(binding) {

        back.setOnClickListener {
            findNavController().popBackStack()
        }
        error.retry.setOnClickListener {
            viewModel.getProduct(args.id)
        }
        indicator.apply {
            val normalColor = ContextCompat.getColor(requireContext(), R.color.indicator_unchecked)
            val checkedColor = ContextCompat.getColor(requireContext(), R.color.indicator_checked)
            setSliderColor(normalColor, checkedColor)
            setSliderWidth(resources.getDimension(R.dimen.dp_12))
            setSliderHeight(resources.getDimension(R.dimen.dp_6))
            setSlideMode(IndicatorSlideMode.WORM)
            setIndicatorStyle(IndicatorStyle.ROUND_RECT)
            notifyDataChanged()
        }

        plus.setOnClickListener {
            viewModel.increment()
        }
        minus.setOnClickListener {
            viewModel.decrement()
        }
        share.setOnClickListener {
            ShareCompat.IntentBuilder(requireActivity())
                .setType("text/plain")
                .setChooserTitle("Share")
                .setText(Constats.HOST + "product/${args.id}")
                .startChooser();
        }

        favorite.setOnClickListener {
            viewModel.toggleWishlist()
        }
        add.setOnClickListener {
            viewModel.set()
            Snackbar.make(root,R.string.detail_add_cart,Snackbar.LENGTH_SHORT).show()
            findNavController().popBackStack()
        }

    }

    private fun subscribeToLiveData() = with(binding) {
        viewModel.loading.observe(viewLifecycleOwner) {
            loading.root.isVisible = it
            setButtonVisibility()
        }
        viewModel.error.observe(viewLifecycleOwner) {
            error.root.isVisible = it
            setButtonVisibility()
        }

        viewModel.detail.observe(viewLifecycleOwner) {
            val color = if (it.wishlist) R.color.orange else R.color.dark_text
            val res = ResourcesCompat.getColor(resources, color, null)
            favorite.setColorFilter(res)

            banners.adapter = DetailAdapter(it.images)
            indicator.setupWithViewPager(banners)
            indicator.apply {
                setPageSize(it.images.size)
                notifyDataChanged()
            }

            categoryTitle.text = it.category
            name.text = it.title

            price.text = getString(R.string.price, it.price - (it.discount ?: 0.0))
            oldPrice.isVisible = it.discount != null
            oldPrice.text = getString(R.string.detail_old_price, it.discount ?: 0.0)
            reviews.text = getString(R.string.detail_product_reviews, it.rating, it.reviews)

            deliver.isVisible = it.freeDelivery
            deliverIcon.isVisible = it.freeDelivery
            products.adapter = RelatedAdapter(it.related, this@DetailFragment::onClick)
        }
        viewModel.count.observe(viewLifecycleOwner) {
            item.text = it.toString()
        }
        viewModel.wishlist.observe(viewLifecycleOwner){
            setWishlist(it)
        }
    }

    private fun DetailFragmentBinding.setButtonVisibility() {
        add.isVisible = viewModel.loading.value == false && viewModel.error.value == false
        buttonDivider.isVisible = viewModel.loading.value == false && viewModel.error.value == false
    }


    private fun onClick(product: Product) {
        findNavController().navigate(DetailFragmentDirections.toDetailFragment(product.id))
    }

    private fun setWishlist(wishlist:Boolean) = with(binding){
        val color = if (wishlist) R.color.orange else R.color.dark_text
        val resolved = ContextCompat.getColor(requireContext(),color)
        favorite.setColorFilter(resolved)
    }
}