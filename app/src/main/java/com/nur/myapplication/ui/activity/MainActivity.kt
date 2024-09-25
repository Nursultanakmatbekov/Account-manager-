package com.nur.myapplication.ui.activity

import android.accounts.AccountManager
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import com.nur.myapplication.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var accountManager: AccountManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        accountManager = AccountManager.get(this)

        val token = getToken()

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController

        if (token == null) {
            navController.navigate(R.id.singInFragment)
        } else {
            navController.navigate(R.id.homeFragment)
        }
    }

    private fun getToken(): String? {
        val accounts = accountManager.getAccountsByType("My Application")
        return if (accounts.isNotEmpty()) {
            accountManager.peekAuthToken(accounts[0], "full_access")
        } else {
            null
        }
    }
}
