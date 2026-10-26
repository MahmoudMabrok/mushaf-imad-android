package com.mushafimad.sampleapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    val navController = rememberNavController()
                    NavHost(
                        navController = navController,
                        startDestination = DeliveryRoutes.HOME
                    ) {
                        composable(DeliveryRoutes.HOME) {
                            DeliveryHomeScreen(navController)
                        }
                        composable(DeliveryRoutes.DYNAMIC) {
                            DynamicFeatureDemoScreen(navController)
                        }
                        composable(DeliveryRoutes.ASSET_PACK) {
                            AssetPackDemoScreen(navController)
                        }
                    }
                }
            }
        }
    }
}
