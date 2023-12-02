package com.ikpydev.ecommerceapp.presention.main

import android.os.Bundle
import android.view.View
import android.view.animation.AnticipateOvershootInterpolator
import android.view.animation.CycleInterpolator
import android.view.animation.DecelerateInterpolator
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintSet
import androidx.core.view.forEachIndexed
import androidx.core.view.isVisible
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupWithNavController
import androidx.transition.ChangeBounds
import androidx.transition.Transition
import androidx.transition.TransitionManager
import com.ikpydev.ecommerceapp.NavMainDirections
import com.ikpydev.ecommerceapp.R
import com.ikpydev.ecommerceapp.databinding.ActivityMainBinding
import com.ikpydev.ecommerceapp.domain.module.Destination
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private val viewModel by viewModels<MainViewModel>()
    private lateinit var binding: ActivityMainBinding

    private val navController get()  = (supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment).navController
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initUi()
        subscribToLiveData()
    }

    private fun initUi() = with(binding) {
        navigation.setupWithNavController(navController)

        navigation.setOnItemSelectedListener {

            var index:Int = 0
            navigation.menu.forEachIndexed{itemIndex, item ->
                if (it.itemId != item.itemId ) return@forEachIndexed
                index= itemIndex
            }
            ConstraintSet().apply {
                clone(indicatorContainer)
                setHorizontalBias(indicator.id, index * 0.25f)

                val transition: Transition = ChangeBounds()
                transition.interpolator = DecelerateInterpolator(1.0f)
                transition.duration = 1000


                TransitionManager.beginDelayedTransition(indicatorContainer, transition)

                applyTo(indicatorContainer)

            }

            NavigationUI.onNavDestinationSelected(
                it,
                navController
            )

            return@setOnItemSelectedListener false
        }
        navController.addOnDestinationChangedListener { _,destination,_ ->
            navigation.isVisible = listOf(
                R.id.onboardingFragment,
                R.id.signUpFragment,
                R.id.singInFragment,
                R.id.detailFragment
            ).all { it !=destination.id }
        }


    }

    private fun subscribToLiveData() {
        viewModel.events.observe(this) {

            when (it) {

                is MainViewModel.Event.NavigateTo -> navigateTo(it.destination)
            }
        }
    }

    private fun navigateTo(destination: Destination) {
        if(navController.currentDestination?.id == R.id.detailFragment)return
        when (destination) {
            Destination.Auth -> navController.navigate(NavMainDirections.toSingInFragment())
            Destination.Onboarding -> navController.navigate(NavMainDirections.toOnboardingFragment())
            Destination.Home -> navController.navigate(NavMainDirections.toHomeFragment())
        }
    }


}