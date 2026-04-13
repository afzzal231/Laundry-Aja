package com.afzzal0039.laundryaja

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.rememberNavController
import com.afzzal0039.laundryaja.Navigation.NavGraph
import com.afzzal0039.laundryaja.ui.theme.LaundryAjaTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LaundryAjaTheme {
                val navController = rememberNavController()
                NavGraph(navController = navController)
            }
        }
    }
}

