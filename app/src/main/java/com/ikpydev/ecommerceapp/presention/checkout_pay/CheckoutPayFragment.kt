package com.ikpydev.ecommerceapp.presention.checkout_pay

import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.viewpager2.widget.ViewPager2
import com.ikpydev.ecommerceapp.R
import com.ikpydev.ecommerceapp.databinding.CheckoutPayFragmentBinding
import com.ikpydev.ecommerceapp.domain.module.Card
import com.ikpydev.ecommerceapp.utils.BaseFragment
import com.ikpydev.ecommerceapp.utils.HorizontalMarginItemDecoration
import com.zhpan.indicator.enums.IndicatorSlideMode
import com.zhpan.indicator.enums.IndicatorStyle
import dagger.hilt.android.AndroidEntryPoint
import kotlin.math.abs

@AndroidEntryPoint
class CheckoutPayFragment:BaseFragment<CheckoutPayFragmentBinding>(CheckoutPayFragmentBinding::inflate) {

    private val viewModel by viewModels<CheckoutPayViewModel>()
    private val args by navArgs<CheckoutPayFragmentArgs>()
    private lateinit var selectCard: Card
    private var idCard: Int = 0
    private lateinit var adapter: List<Card>


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        subscribeToLiveData()
        initUi()
    }

    private fun subscribeToLiveData() = with(binding) {
        viewModel.cards.observe(viewLifecycleOwner) {


            if (it.isNotEmpty()) {


                empty.isVisible = false

                adapter = it
                cards.isVisible = true
                cards.adapter = CardAdapter(adapter, this@CheckoutPayFragment::setCard)
                cards.offscreenPageLimit = 1

// Add a PageTransformer that translates the next and previous items horizontally
// towards the center of the screen, which makes them visible
                val nextItemVisiblePx =
                    resources.getDimension(R.dimen.viewpager_next_item_visible_1)
                val currentItemHorizontalMarginPx =
                    resources.getDimension(R.dimen.viewpager_current_item_horizontal_margin_1)
                val pageTranslationX = nextItemVisiblePx + currentItemHorizontalMarginPx
                val pageTransformer = ViewPager2.PageTransformer { page: View, position: Float ->
                    page.translationX = -pageTranslationX * position
                    // Next line scales the item's height. You can remove it if you don't want this effect
                    page.scaleY = 1 - (0.25f * abs(position))
                    // If you want a fading effect uncomment the next line:
                    // page.alpha = 0.25f + (1 - abs(position))
                }
                cards.setPageTransformer(pageTransformer)

// The ItemDecoration gives the current (centered) item horizontal margin so that
// it doesn't occupy the whole screen width. Without it the items overlap
                val itemDecoration = HorizontalMarginItemDecoration(
                    requireContext(),
                    R.dimen.viewpager_current_item_horizontal_margin_1
                )
                cards.addItemDecoration(itemDecoration)

                cards.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
                    override fun onPageSelected(position: Int) {
                        idCard = position
                        indicator.onPageSelected(position)
                    }
                })


            } else {
                empty.isVisible = true
                cards.isVisible = false

            }
            indicator.setupWithViewPager(cards)
            indicator.apply {
                notifyDataChanged()
            }

        }

    }

    private fun initUi() = with(binding) {
        back.setOnClickListener {
            findNavController().popBackStack()
        }
        addCard.setOnClickListener {
            findNavController().navigate(CheckoutPayFragmentDirections.toAddCardFragment())
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
        next.setOnClickListener {

            val card = getCardAtIndex(adapter, idCard)

            viewModel.createOrder(args.userInfo.promo, args.userInfo, card!!)
            findNavController().navigate(CheckoutPayFragmentDirections.toHomeFragment())
        }


    }

    private fun setCard(card: Card) {

    }

    private fun getCardAtIndex(cards: List<Card>, index: Int): Card? {
        return if (index in 0 until cards.size) {
            cards[index]
        } else {
            null
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.getCards()
    }

}