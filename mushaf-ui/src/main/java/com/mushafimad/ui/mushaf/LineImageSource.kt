package com.mushafimad.ui.mushaf

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import com.google.android.play.core.assetpacks.AssetPackManagerFactory
import java.io.File
import java.io.InputStream

/**
 * Loads Quran line images.
 *
 * Preference order:
 * 1. On-demand Asset Pack ("mushaf_assets") if installed
 * 2. Bundled assets (fallback for consumers who still ship the images inside the AAR)
 */
internal object LineImageSource {

    private const val PACK_NAME = "mushaf_assets"
    private const val ASSET_ROOT = "quran-images"

    fun load(context: Context, page: Int, line: Int): Bitmap? {
        // 1. Try Asset Pack
        try {
            val packManager = AssetPackManagerFactory.getInstance(context)
            val location = packManager.getPackLocation(PACK_NAME)
            if (location != null) {
                val file = File(location.assetsPath(), "$ASSET_ROOT/$page/$line.png")
                if (file.exists()) {
                    return BitmapFactory.decodeFile(file.absolutePath)
                }
            }
        } catch (_: Throwable) {
            // Asset Pack not available or not installed — fall through
        }

        // 2. Fall back to bundled assets
        return try {
            val path = "$ASSET_ROOT/$page/$line.png"
            context.assets.open(path).use { stream: InputStream ->
                BitmapFactory.decodeStream(stream)
            }
        } catch (_: Exception) {
            null
        }
    }
}
