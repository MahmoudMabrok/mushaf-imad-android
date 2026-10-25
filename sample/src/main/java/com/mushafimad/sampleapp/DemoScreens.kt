package com.mushafimad.sampleapp

import android.content.Intent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.mushafimad.core.domain.models.MushafType
import com.mushafimad.ui.mushaf.MushafView
import com.mushafimad.ui.theme.ColorSchemeType
import com.mushafimad.ui.theme.ReadingTheme

/**
 * Home screen that lets the user try the three delivery modes.
 */
@Composable
fun DemoHomeScreen(
    onOpenDirect: () -> Unit,
    onOpenDynamicFeature: () -> Unit,
    onOpenAssetPack: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp, Alignment.CenterVertically),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Mushaf Delivery Modes", style = MaterialTheme.typography.headlineMedium)
        Text(
            "Choose how the Mushaf is delivered to the user.",
            style = MaterialTheme.typography.bodyMedium
        )

        Button(onClick = onOpenDirect, modifier = Modifier.fillMaxWidth()) {
            Text("1. Library directly (bundled)")
        }
        Text("Images + code inside the base APK. Simplest, largest download.", style = MaterialTheme.typography.bodySmall)

        Button(onClick = onOpenDynamicFeature, modifier = Modifier.fillMaxWidth()) {
            Text("2. Dynamic Feature (on-demand)")
        }
        Text("Code + images in an on-demand module. Base APK stays small.", style = MaterialTheme.typography.bodySmall)

        Button(onClick = onOpenAssetPack, modifier = Modifier.fillMaxWidth()) {
            Text("3. Asset Pack (on-demand)")
        }
        Text("Images in an Asset Pack, code in a thin feature. Smallest updates.", style = MaterialTheme.typography.bodySmall)
    }
}

/**
 * Simple Mushaf screen used by the "direct" and "asset pack" demos.
 * (Asset Pack loading is handled inside the library when the pack is present.)
 */
@Composable
fun DirectMushafScreen() {
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
