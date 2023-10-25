package com.ps.movie

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.ps.movie.base.BaseActivity
import com.ps.movie.navigation.AppNavHost
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

