package com.mushafimad.sampleapp

import android.content.Intent
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.mushafimad.core.domain.models.MushafType
import com.mushafimad.ui.mushaf.MushafView
import com.mushafimad.ui.theme.ColorSchemeType
import com.mushafimad.ui.theme.ReadingTheme

object DeliveryRoutes {
    const val HOME = "delivery_home"
    const val DYNAMIC = "delivery_dynamic"
    const val ASSET_PACK = "delivery_asset_pack"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DeliveryHomeScreen(navController: NavHostController) {
    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Delivery Modes Demo") })
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(24.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text("How is the Mushaf delivered?", style = MaterialTheme.typography.headlineSmall)
            Text(
                "Choose a delivery strategy. The library is not used directly in the base APK.",
                style = MaterialTheme.typography.bodyMedium
            )

            Spacer(Modifier.height(8.dp))

            DeliveryCard(
                title = "Dynamic Feature (on-demand)",
                subtitle = "Mushaf UI lives in an on-demand module. Base APK stays small.",
                onClick = { navController.navigate(DeliveryRoutes.DYNAMIC) }
            )

            DeliveryCard(
                title = "Asset Pack (on-demand)",
                subtitle = "Images are in an Asset Pack. Code stays thin; updates stay small.",
                onClick = { navController.navigate(DeliveryRoutes.ASSET_PACK) }
            )
        }
    }
}

@Composable
private fun DeliveryCard(title: String, subtitle: String, onClick: () -> Unit) {
    Card(onClick = onClick, modifier = Modifier.fillMaxWidth()) {
        Column(Modifier.padding(16.dp)) {
            Text(title, style = MaterialTheme.typography.titleMedium)
            Spacer(Modifier.height(4.dp))
            Text(subtitle, style = MaterialTheme.typography.bodySmall)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DynamicFeatureDemoScreen(navController: NavHostController) {
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
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(24.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                "This opens the on-demand :mushaf_feature module.",
                style = MaterialTheme.typography.bodyLarge
            )
            Spacer(Modifier.height(16.dp))
            Button(onClick = {
                try {
                    val intent = Intent().setClassName(
                        context.packageName,
                        "com.mushafimad.feature.MushafFeatureActivity"
                    )
                    context.startActivity(intent)
                } catch (_: Exception) {
                    // Module not installed — request it with SplitInstallManager in production
                }
            }) {
                Text("Open Mushaf Feature")
            }
            Spacer(Modifier.height(8.dp))
            Text(
                "If the module is not installed, request it with SplitInstallManager first.",
                style = MaterialTheme.typography.bodySmall
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AssetPackDemoScreen(navController: NavHostController) {
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
                "Images load from the on-demand Asset Pack when available.\n" +
                        "If the pack is not downloaded yet, the library falls back.",
                modifier = Modifier.padding(16.dp),
                style = MaterialTheme.typography.bodyMedium
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
