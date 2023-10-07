package com.example.bogungym

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.activity.viewModels
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.view.get
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider

import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration

import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController

import androidx.navigation.ui.setupWithNavController
import com.example.bogungym.databinding.ActivityMainBinding
import com.example.bogungym.ui.CustomFragment
import com.example.bogungym.ui.login.ProfileFragment

import com.google.android.material.navigation.NavigationView


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private lateinit var navController: NavController
    private lateinit var hamNavigationView: NavigationView
    private lateinit var actionBarDrawerToggle: ActionBarDrawerToggle
    private lateinit var drawableLayout: DrawerLayout
    private lateinit var appBarConfiguration: AppBarConfiguration




    fun toolbarChange(title: String){

        supportActionBar?.title = title
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setTheme(R.style.Theme_Bogun)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)




        drawableLayout = binding.drawableLayout
        hamNavigationView = binding.hamburgerNav
        actionBarDrawerToggle =
            ActionBarDrawerToggle(
                this,
                drawableLayout,
                binding.myToolbar,
                R.string.open,
                R.string.close
            )
        drawableLayout.addDrawerListener(actionBarDrawerToggle)
        actionBarDrawerToggle.syncState()
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        hamNavigationView.setNavigationItemSelectedListener() {
            when (it.itemId) {
                R.id.profileFragment -> findNavController(R.id.navHostFragmentFCV).navigate(R.id.profileFragment)
                R.id.customFragment2 -> findNavController(R.id.navHostFragmentFCV).navigate(R.id.customFragment2)
                R.id.settingsFragment -> findNavController(R.id.navHostFragmentFCV).navigate(R.id.settingsFragment)
                R.id.aboutUsFragment -> findNavController(R.id.navHostFragmentFCV).navigate(R.id.aboutUsFragment)
            }

            return@setNavigationItemSelectedListener false
        }


        val navHostFragment: NavHostFragment =
            supportFragmentManager.findFragmentById(R.id.navHostFragmentFCV) as NavHostFragment
        navController = navHostFragment.navController

        binding.bottomNavBMV.setupWithNavController(navController)

        val headerView = hamNavigationView.getHeaderView(0)

        val isDarkMode = (resources.configuration.uiMode and android.content.res.Configuration.UI_MODE_NIGHT_MASK) == android.content.res.Configuration.UI_MODE_NIGHT_YES



        val lightImage: ImageView = headerView.findViewById(R.id.light_image)
        val darkImage: ImageView = headerView.findViewById(R.id.dark_image)


        if (isDarkMode){
            lightImage.visibility = View.GONE
            darkImage.visibility = View.VISIBLE
        } else{
            lightImage.visibility = View.VISIBLE
            darkImage.visibility = View.GONE
        }









        navController.addOnDestinationChangedListener { _, destination, _ ->




            if (destination.id == R.id.aboutUsFragment ||destination.id == R.id.workoutSaveFragment ||destination.id == R.id.passwordResetFragment || destination.id == R.id.signUpFragment || destination.id == R.id.loginFragment || destination.id == R.id.profileFragment || destination.id == R.id.onboardingFragment) {
                binding.bottomNavBMV.visibility = View.GONE
                binding.appBarLayout.visibility = View.GONE
            } else {
                binding.bottomNavBMV.visibility = View.VISIBLE
                binding.appBarLayout.visibility = View.VISIBLE
            }

            if (destination.id == R.id.homeFragment) {


                if (isDarkMode) {
                    binding.logoIVDark.visibility = View.VISIBLE
                    binding.logoIVLight.visibility = View.GONE
                } else {
                    binding.logoIVLight.visibility = View.VISIBLE
                    binding.logoIVDark.visibility = View.GONE
                }
                binding.profileIV.visibility = View.VISIBLE

            } else {
                binding.profileIV.visibility = View.GONE

                binding.logoIVDark.visibility = View.GONE
                binding.logoIVLight.visibility = View.GONE
            }


            if (destination.id == R.id.customFragment2 || destination.id == R.id.addWorkoutFragment){
                binding.appBarLayout.visibility = View.GONE
            }

        }



        binding.profileIV.setOnClickListener {
            val navController = findNavController(R.id.navHostFragmentFCV)
            navController.navigate(R.id.profileFragment)
        }


        binding.bottomNavBMV.setOnItemSelectedListener { item ->
            NavigationUI.onNavDestinationSelected(item, navController)

            navController.popBackStack(item.itemId, false)
            return@setOnItemSelectedListener true
        }


        onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                navController.navigateUp()
            }


        })

        setSupportActionBar(binding.myToolbar)
        hamNavigationView.setupWithNavController(navController)

        appBarConfiguration = AppBarConfiguration(navController.graph, drawableLayout)
        setupActionBarWithNavController(navController, appBarConfiguration)

    }




    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.navHostFragmentFCV)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }
}