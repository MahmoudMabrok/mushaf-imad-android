package com.mushafimad.ui.mushaf

import android.content.Context
import android.content.res.AssetManager
import io.mockk.every
import io.mockk.mockk
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TemporaryFolder
import java.io.ByteArrayInputStream
import java.io.FileNotFoundException

class LineImageSourceTest {

    @get:Rule
    val tmp = TemporaryFolder()

    private val context: Context = mockk(relaxed = true)

    @Test
    fun `file source reads page and line from directory`() {
        val dir = tmp.newFolder("quran-images")
        dir.resolve("3").mkdirs()
        dir.resolve("3/7.png").writeBytes(byteArrayOf(1, 2, 3))

        val source = FileLineImageSource(dir)

        assertEquals(listOf<Byte>(1, 2, 3), source.open(context, 3, 7)!!.use { it.readBytes().toList() })
        assertNull(source.open(context, 3, 8))
    }

    @Test
    fun `asset source opens root slash page slash line png`() {
        val assets: AssetManager = mockk()
        every { assets.open("quran-images/1/0.png") } returns ByteArrayInputStream(byteArrayOf(9))
        every { assets.open("quran-images/1/1.png") } throws FileNotFoundException()
        every { context.assets } returns assets

        val source = AssetLineImageSource()

        assertEquals(9, source.open(context, 1, 0)!!.use { it.read() })
        assertNull(source.open(context, 1, 1))
    }

    @Test
    fun `fallback source returns first available and survives throwing sources`() {
        val dir = tmp.newFolder("pack")
        dir.resolve("2").mkdirs()
        dir.resolve("2/0.png").writeBytes(byteArrayOf(5))

        val throwing = LineImageSource { _, _, _ -> throw IllegalStateException("boom") }
        val empty = LineImageSource { _, _, _ -> null }
        val source = FallbackLineImageSource(throwing, empty, FileLineImageSource(dir))

        assertEquals(5, source.open(context, 2, 0)!!.use { it.read() })
        assertNull(source.open(context, 2, 1))
    }

    @Test
    fun `default global source reads bundled assets`() {
        assertEquals(AssetLineImageSource::class, MushafLineImages.source::class)
    }
}
