package com.example.drawerlayout.ui.main

import android.content.ClipData
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.google.android.material.navigation.NavigationView
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import androidx.drawerlayout.widget.DrawerLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.view.menu.MenuView
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.drawerlayout.R
import com.example.drawerlayout.common.entities.LoggedInUserView
import com.example.drawerlayout.common.utils.LoginInterface
import com.example.drawerlayout.databinding.ActivityMainBinding
import com.example.drawerlayout.ui.login.viewModel.LoginViewModel
import com.example.drawerlayout.ui.register.viewModel.RegisterViewModel

class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding

    private lateinit var mLoginViewModel: LoginViewModel
    private lateinit var mRegisterViewModel: RegisterViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.appBarMain.toolbar)
        val drawerLayout: DrawerLayout = binding.drawerLayout
        val navView: NavigationView = binding.navView
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.nav_home, R.id.nav_products, R.id.nav_login, R.id.nav_register
            ), drawerLayout
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

        binding.logout.setOnClickListener {
            mLoginViewModel.logOut()
            mRegisterViewModel.logOut()
            with(navView.menu) {
                findItem(R.id.nav_login).isVisible = true
                findItem(R.id.nav_register).isVisible = true
            }
            binding.logout.visibility = View.GONE
            Toast.makeText(this, "Gracias por usar nuestra app", Toast.LENGTH_LONG).show()
        }

        setupViewModel()
    }

    private fun setupViewModel() {
        val navView: NavigationView = binding.navView
        mLoginViewModel = ViewModelProvider(this)[LoginViewModel::class.java]
        mRegisterViewModel = ViewModelProvider(this)[RegisterViewModel::class.java]
        mLoginViewModel.loginResult.observe(this) { loginResult ->
            loginResult.success?.let {
                Log.i("que onda wacho", it.toString())
                with(navView.menu) {
                    findItem(R.id.nav_login).isVisible = false
                    findItem(R.id.nav_register).isVisible = false
                }
                binding.logout.visibility = View.VISIBLE
                updateUiWithUser(it, true)
            }
            loginResult.logout?.let {
                updateUiWithUser(it, false)
            }
        }
        mRegisterViewModel.registerResult.observe(this) { loginResult ->
            loginResult.success?.let {
                Log.i("que onda wacho", it.toString())
                with(navView.menu) {
                    findItem(R.id.nav_login).isVisible = false
                    findItem(R.id.nav_register).isVisible = false
                }
                binding.logout.visibility = View.VISIBLE
                updateUiWithUser(it, true)
            }
            loginResult.logout?.let {
                updateUiWithUser(it, false)
            }
        }
    }

    private fun updateUiWithUser(model: LoggedInUserView, active: Boolean) {
        val navView: NavigationView = binding.navView
        val header: View = navView.getHeaderView(0)

        val title = header.findViewById<TextView>(R.id.userLogged)
        val email = header.findViewById<TextView>(R.id.emailUserLogged)
        val imageUser = header.findViewById<ImageView>(R.id.imageUserLogged)

        title.text = model.user
        email.text = model.email

        Glide.with(imageUser.context)
            .load(model.urlImg)
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .centerCrop()
            .circleCrop()
            .error("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSyONMl-oynWzLcxAAnvZLNgCyrwu7p9UhgcA&usqp=CAU")
            .into(imageUser)
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }
}