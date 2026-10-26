package com.mushafimad.sampledelivery

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.mushafimad.core.domain.models.MushafType
import com.mushafimad.ui.mushaf.MushafView
import com.mushafimad.ui.theme.ColorSchemeType
import com.mushafimad.ui.theme.ReadingTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    val navController = rememberNavController()
                    NavHost(navController, startDestination = "home") {
                        composable("home") { DeliveryHome(navController) }
                        composable("dynamic") { DynamicScreen(navController) }
                        composable("asset") { AssetPackScreen(navController) }
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun DeliveryHome(navController: androidx.navigation.NavHostController) {
    Scaffold(topBar = { TopAppBar(title = { Text("Delivery Modes Demo") }) }) { padding ->
        Column(
            Modifier.fillMaxSize().padding(padding).padding(24.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text("How is the Mushaf delivered?", style = MaterialTheme.typography.headlineSmall)
            Text("This sample only demonstrates on-demand delivery.", style = MaterialTheme.typography.bodyMedium)
            Spacer(Modifier.height(8.dp))

            Card(onClick = { navController.navigate("dynamic") }, modifier = Modifier.fillMaxWidth()) {
                Column(Modifier.padding(16.dp)) {
                    Text("Dynamic Feature", style = MaterialTheme.typography.titleMedium)
                    Text("Mushaf UI in an on-demand module.", style = MaterialTheme.typography.bodySmall)
                }
            }

            Card(onClick = { navController.navigate("asset") }, modifier = Modifier.fillMaxWidth()) {
                Column(Modifier.padding(16.dp)) {
                    Text("Asset Pack", style = MaterialTheme.typography.titleMedium)
                    Text("Images in an on-demand Asset Pack.", style = MaterialTheme.typography.bodySmall)
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun DynamicScreen(navController: androidx.navigation.NavHostController) {
    val context = LocalContext.current
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Dynamic Feature") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, "Back")
                    }
                }
            )
        }
    ) { padding ->
        Column(
            Modifier.fillMaxSize().padding(padding).padding(24.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("Opens the on-demand :mushaf_feature module.")
            Spacer(Modifier.height(16.dp))
            Button(onClick = {
                try {
                    context.startActivity(
                        Intent().setClassName(
                            context.packageName,
                            "com.mushafimad.feature.MushafFeatureActivity"
                        )
                    )
                } catch (_: Exception) {}
            }) { Text("Open Feature") }
            Spacer(Modifier.height(8.dp))
            Text("Request the module with SplitInstallManager if not installed.", style = MaterialTheme.typography.bodySmall)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun AssetPackScreen(navController: androidx.navigation.NavHostController) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Asset Pack") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, "Back")
                    }
                }
            )
        }
    ) { padding ->
        Column(Modifier.padding(padding)) {
            Text(
                "Images load from the on-demand Asset Pack when available.",
                modifier = Modifier.padding(16.dp)
            )
            MushafView(
                readingTheme = ReadingTheme.COMFORTABLE,
                colorScheme = ColorSchemeType.DEFAULT,
                mushafType = MushafType.HAFS_1441,
                initialPage = 1,
                showNavigationControls = true,
                showPageInfo = true,
                modifier = Modifier.fillMaxSize()
            )
        }
    }
}
