package com.example.guianavegacion.CoreNavigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.example.guianavegacion.Core.Navigation.Detail
import com.example.guianavegacion.Core.Navigation.Home
import com.example.guianavegacion.Core.Navigation.Login
import com.example.guianavegacion.DetailScreen
import com.example.guianavegacion.LoginScreen
import com.example.guianavegacion.HomeScreen

@Composable
fun NavigationWrapper() {

    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = Login) {
        composable<Login>{
            LoginScreen() { navController.navigate(Home) }
        }
        composable<Home>{
            HomeScreen{name->navController.navigate(Detail(name = name))}

        }

        composable<Detail>{backStackEntry->
            val detail:Detail=backStackEntry.toRoute()
            DetailScreen(name=detail.name){
                navController.navigate(Login){
                    popUpTo(Login){
                        inclusive=true
                    }
                }}
        }
    }
}