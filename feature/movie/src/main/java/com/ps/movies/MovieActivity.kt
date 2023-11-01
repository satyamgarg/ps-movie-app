package com.ps.movies

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.ps.movies.base.BaseActivity
import com.ps.movies.navigation.AppNavHost
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MovieActivity : BaseActivity() {
    private lateinit var navController: NavHostController

    @Composable
    override fun BuildContent() {
        navController = rememberNavController()
        AppNavHost(navController = navController)
    }
}

