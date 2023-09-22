package com.example.bogungym

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatDelegate
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.bogungym.databinding.ActivityMainBinding
import com.example.bogungym.ui.HomeFragmentDirections
import com.example.bogungym.ui.login.FirebaseViewModel


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding


    private lateinit var navController: NavController

//
//    private lateinit var actionBarDrawerToggle: ActionBarDrawerToggle
//
//    private lateinit var drawableLayout: DrawerLayout



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.Theme_Bogun)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)




//        drawableLayout = findViewById(R.id.drawerLayout)
//
//
//
//
//        actionBarDrawerToggle= ActionBarDrawerToggle(this,drawableLayout,binding.myToolbar,R.string.open,R.string.close)
//        actionBarDrawerToggle.isDrawerIndicatorEnabled = true
//
//        drawableLayout.addDrawerListener(actionBarDrawerToggle)
//        actionBarDrawerToggle.syncState()


        val navHostFragment: NavHostFragment =
            supportFragmentManager.findFragmentById(R.id.navHostFragmentFCV) as NavHostFragment
        navController = navHostFragment.navController

        binding.bottomNavBMV.setupWithNavController(navController)



        navController.addOnDestinationChangedListener { _, destination, _ ->



            binding.titleTV.text = destination.label
            if(destination.id == R.id.passwordResetFragment || destination.id == R.id.signUpFragment || destination.id == R.id.loginFragment || destination.id == R.id.profileFragment || destination.id == R.id.onboardingFragment) {
                binding.bottomNavBMV.visibility = View.GONE
                binding.appBarLayout.visibility = View.GONE
            } else {
                binding.bottomNavBMV.visibility = View.VISIBLE
                binding.appBarLayout.visibility = View.VISIBLE
            }

            if(destination.id == R.id.homeFragment) {
                val isNightMode =
                    AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES

                if (isNightMode){
                    binding.logoIVDark.visibility = View.VISIBLE
                    binding.logoIVLight.visibility = View.GONE
                } else{
                    binding.logoIVLight.visibility = View.VISIBLE
                    binding.logoIVDark.visibility = View.GONE
                }
                binding.profileIV.visibility = View.VISIBLE
                binding.hamburgerMenu.visibility = View.VISIBLE
                binding.backIcon.visibility = View.GONE
                binding.titleTV.visibility = View.GONE
            } else {
                binding.profileIV.visibility = View.GONE
                binding.hamburgerMenu.visibility = View.GONE
                binding.backIcon.visibility = View.VISIBLE
                binding.titleTV.visibility = View.VISIBLE
                binding.logoIVDark.visibility = View.GONE
                binding.logoIVLight.visibility = View.GONE
            }

        }











        binding.backIcon.setOnClickListener {
            onSupportNavigateUp()
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