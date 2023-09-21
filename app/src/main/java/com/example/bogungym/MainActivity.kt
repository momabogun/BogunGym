package com.example.bogungym

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.bogungym.databinding.ActivityMainBinding
import com.example.bogungym.ui.HomeFragmentDirections
import com.example.bogungym.ui.login.FirebaseViewModel

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding


    private lateinit var navController: NavController



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.Theme_Bogun)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        val navHostFragment: NavHostFragment =
            supportFragmentManager.findFragmentById(R.id.navHostFragmentFCV) as NavHostFragment
        navController = navHostFragment.navController

        binding.bottomNavBMV.setupWithNavController(navController)
        setSupportActionBar(binding.myToolbar)


        navController.addOnDestinationChangedListener { _, destination, _ ->
            if(destination.id == R.id.signUpFragment || destination.id == R.id.loginFragment) {
                binding.bottomNavBMV.visibility = View.GONE
                binding.appBarLayout.visibility = View.GONE
            } else {
                binding.bottomNavBMV.visibility = View.VISIBLE
                binding.appBarLayout.visibility = View.VISIBLE
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
                Toast.makeText(this@MainActivity, "Back Pressed", Toast.LENGTH_LONG).show()
            }


        })
    }


    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() || super.onSupportNavigateUp()
    }
}