package com.example.userstask.ui

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.example.userstask.R
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var navController: NavController
    private lateinit var mainViewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        navController = Navigation.findNavController(this, R.id.nav_host_fragment)
        mainViewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        mainViewModel.loadUsersData(PageType.LOAD_FROM_API)
        mainViewModel.loadingInProgress.observe(this, Observer {
            progress_bar.visibility = if (it) View.VISIBLE else View.GONE
            nav_host_fragment.view?.visibility = if (it) View.GONE else View.VISIBLE
        })

        bottom_nav.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.users -> {
                    mainViewModel.loadUsersData(PageType.LOAD_FROM_API)
                    true
                }
                R.id.saved_users -> {
                    mainViewModel.loadUsersData(PageType.LOAD_FROM_DB)
                    true
                }
                else -> false
            }
        }

    }

    override fun onSupportNavigateUp(): Boolean {
        return NavigationUI.navigateUp(navController, null)
    }
}
