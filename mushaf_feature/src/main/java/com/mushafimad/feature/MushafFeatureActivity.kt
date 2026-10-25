package com.mushafimad.feature

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.mushafimad.core.domain.models.MushafType
import com.mushafimad.ui.mushaf.MushafView
import com.mushafimad.ui.theme.ColorSchemeType
import com.mushafimad.ui.theme.ReadingTheme

/**
 * Minimal Activity that lives inside the on-demand dynamic feature.
 * Once the feature is installed, the host app can start this Activity.
 */
class MushafFeatureActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Surface(modifier = Modifier.fillMaxSize()) {
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
}
