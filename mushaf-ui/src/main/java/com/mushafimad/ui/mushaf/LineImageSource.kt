package com.mushafimad.ui.mushaf

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import java.io.File
import java.io.InputStream

/**
 * Where the Mushaf line images (`{page}/{line}.png`, 1440x232) come from.
 *
 * Since 0.4.0 the ~113 MB of page images are no longer packed inside the
 * `mushaf-core` AAR. A consumer chooses how to ship them:
 *
 * - **Optional artifact** - add `mushaf-images` as a dependency. Its assets merge into
 *   the APK under `assets/quran-images/`, which the default [AssetLineImageSource] reads.
 * - **Play Asset Delivery, install-time pack** - put `quran-images/` in an asset pack
 *   module with `deliveryType = "install-time"`. Install-time packs are merged into the
 *   app's [android.content.res.AssetManager], so the default source reads them too and
 *   app updates no longer re-download the images.
 * - **fast-follow / on-demand pack, or a downloaded folder** - set
 *   `MushafLineImages.source = FileLineImageSource(dir)` where `dir` contains
 *   `{page}/{line}.png` (e.g. `File(packLocation.assetsPath(), "quran-images")`).
 * - Anything else - implement [LineImageSource] yourself.
 */
fun interface LineImageSource {
    /**
     * Open the PNG for [page] (1..604) and [line] (0..14), or return `null` when it is
     * not available. The caller closes the stream. Called off the main thread.
     */
    fun open(context: Context, page: Int, line: Int): InputStream?
}

/**
 * Reads `"$root/{page}/{line}.png"` through [Context.getAssets]. Works for images merged from
 * the `mushaf-images` artifact, the app's own assets, and install-time asset packs.
 */
class AssetLineImageSource @JvmOverloads constructor(
    private val root: String = DEFAULT_ROOT
) : LineImageSource {
    override fun open(context: Context, page: Int, line: Int): InputStream? =
        try {
            context.assets.open("$root/$page/$line.png")
        } catch (_: Exception) {
            null
        }

    companion object {
        const val DEFAULT_ROOT: String = "quran-images"
    }
}

/** Reads `{dir}/{page}/{line}.png` from the file system. */
class FileLineImageSource(private val dir: File) : LineImageSource {
    override fun open(context: Context, page: Int, line: Int): InputStream? {
        val file = File(dir, "$page/$line.png")
        return if (file.isFile) file.inputStream() else null
    }
}

/** Tries each source in order and returns the first image found. */
class FallbackLineImageSource(private val sources: List<LineImageSource>) : LineImageSource {
    constructor(vararg sources: LineImageSource) : this(sources.toList())

    override fun open(context: Context, page: Int, line: Int): InputStream? {
        for (source in sources) {
            val stream = try {
                source.open(context, page, line)
            } catch (_: Exception) {
                null
            }
            if (stream != null) return stream
        }
        return null
    }
}

/**
 * Global configuration of where line images are read from. Set [source] once, e.g. in
 * `Application.onCreate`, before any Mushaf screen is shown. Defaults to
 * [AssetLineImageSource] reading `assets/quran-images/`.
 */
object MushafLineImages {
    @Volatile
    @JvmStatic
    var source: LineImageSource = AssetLineImageSource()

    /** Loads and decodes one line image with the configured [source]; `null` if missing. */
    @JvmStatic
    fun load(context: Context, page: Int, line: Int): Bitmap? =
        try {
            source.open(context, page, line)?.use { BitmapFactory.decodeStream(it) }
        } catch (_: Exception) {
            null
        }

    /** True when the configured [source] can provide the given line image. */
    @JvmStatic
    fun isAvailable(context: Context, page: Int = 1, line: Int = 0): Boolean =
        try {
            source.open(context, page, line)?.use { true } ?: false
        } catch (_: Exception) {
            false
        }
}
