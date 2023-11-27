package com.ikpydev.ecommerceapp.presention.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.navigation.fragment.NavHostFragment
import com.ikpydev.ecommerceapp.NavMainDirections
import com.ikpydev.ecommerceapp.R
import com.ikpydev.ecommerceapp.databinding.ActivityMainBinding
import com.ikpydev.ecommerceapp.domain.module.Destination
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private val viewModel by viewModels<MainViewModel>()
    private lateinit var binding: ActivityMainBinding

    val navController get()  = (supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment).navController
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        window.decorView.systemUiVisibility =
            View.SYSTEM_UI_FLAG_HIDE_NAVIGATION

        subscibToLiveData()
    }

    private fun subscibToLiveData() {
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