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

/**
 * Routes for the delivery-mode demos.
 */
object DeliveryRoutes {
    const val HOME = "delivery_home"
    const val DIRECT = "delivery_direct"
    const val DYNAMIC = "delivery_dynamic"
    const val ASSET_PACK = "delivery_asset_pack"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DeliveryHomeScreen(navController: NavHostController) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Delivery Modes") },
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
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text("How is the Mushaf delivered?", style = MaterialTheme.typography.headlineSmall)
            Text("Each option shows a different size / update trade-off.", style = MaterialTheme.typography.bodyMedium)

            Spacer(Modifier.height(8.dp))

            DeliveryCard(
                title = "1. Library directly",
                subtitle = "Images + code bundled in the base APK. Simplest integration, largest download.",
                onClick = { navController.navigate(DeliveryRoutes.DIRECT) }
            )

            DeliveryCard(
                title = "2. Dynamic Feature",
                subtitle = "On-demand module. Base APK stays small. Users download Mushaf only when they open it.",
                onClick = { navController.navigate(DeliveryRoutes.DYNAMIC) }
            )

            DeliveryCard(
                title = "3. Asset Pack",
                subtitle = "Images in an on-demand Asset Pack. Code stays in a thin module. Smallest updates.",
                onClick = { navController.navigate(DeliveryRoutes.ASSET_PACK) }
            )
        }
    }
}

@Composable
private fun DeliveryCard(title: String, subtitle: String, onClick: () -> Unit) {
    Card(
        onClick = onClick,
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(Modifier.padding(16.dp)) {
            Text(title, style = MaterialTheme.typography.titleMedium)
            Spacer(Modifier.height(4.dp))
            Text(subtitle, style = MaterialTheme.typography.bodySmall)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DirectDeliveryScreen(navController: NavHostController) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Direct (bundled)") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, "Back")
                    }
                }
            )
        }
    ) { padding ->
        Box(Modifier.padding(padding)) {
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
                "This mode uses the on-demand :mushaf-feature module.",
                style = MaterialTheme.typography.bodyLarge
            )
            Spacer(Modifier.height(16.dp))
            Button(onClick = {
                // In a real app you would call SplitInstallManager first.
                // For the sample we just start the feature Activity (it will fail if the module is not installed).
                try {
                    val intent = Intent().setClassName(
                        context.packageName,
                        "com.mushafimad.feature.MushafFeatureActivity"
                    )
                    context.startActivity(intent)
                } catch (_: Exception) {
                    // Module not installed yet — in production request it via SplitInstallManager
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
                "Images are loaded from the on-demand Asset Pack when available.\n" +
                        "If the pack is not yet downloaded, the library falls back to bundled assets.",
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
