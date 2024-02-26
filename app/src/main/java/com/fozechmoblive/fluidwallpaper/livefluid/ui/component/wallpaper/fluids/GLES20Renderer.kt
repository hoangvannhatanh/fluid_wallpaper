package com.fozechmoblive.fluidwallpaper.livefluid.ui.component.wallpaper.fluids

import android.app.Activity
import android.content.ContentResolver
import android.content.ContentValues
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.opengl.GLES20
import android.opengl.GLSurfaceView
import android.os.Build
import android.os.Environment
import android.provider.MediaStore
import android.widget.Toast
import androidx.core.view.ViewCompat
import com.fozechmoblive.fluidwallpaper.livefluid.R
import com.magicfluids.NativeInterface
import com.fozechmoblive.fluidwallpaper.livefluid.ui.component.wallpaper.WallpaperActivity
import com.fozechmoblive.fluidwallpaper.livefluid.ui.component.wallpaper.WallpaperLiveViewActivity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.IOException
import java.nio.ByteBuffer
import javax.microedition.khronos.egl.EGLConfig
import javax.microedition.khronos.opengles.GL10

class GLES20Renderer(
    private val activityShare: Activity? = null,
    mainActivity: WallpaperActivity?,
    nativeInterface: NativeInterface,
    orientationSensor: com.fozechmoblive.fluidwallpaper.livefluid.ui.component.wallpaper.fluids.OrientationSensor
) : GLSurfaceView.Renderer {
    private val activity: Activity?
    private val nativeInterface: NativeInterface
    private val orientationSensor: com.fozechmoblive.fluidwallpaper.livefluid.ui.component.wallpaper.fluids.OrientationSensor
    private var screenHeight = 0
    private var screenWidth = 0
    private val screenshotLock = Any()
    private var takeScreenshot = false
    private var ignoreNextFrameTime = false
    private var avgTimeNumSamples = 0
    private var avgTimeSamplesSum: Long = 0
    private var lastNanoTime: Long = 0
    private var maxTime: Long = 0
    var input = com.fozechmoblive.fluidwallpaper.livefluid.ui.component.wallpaper.fluids.Input()

    @Volatile
    var isActivePaused = false

    init {
        activity = mainActivity
        this.nativeInterface = nativeInterface
        this.orientationSensor = orientationSensor
    }

    fun setInitialScreenSize(i: Int, i2: Int) {
        screenWidth = i
        screenHeight = i2
    }

    fun orderScreenshot() {
        synchronized(screenshotLock) { takeScreenshot = true }
    }

    fun checkScreenshotOrder(): Boolean {
        synchronized(screenshotLock) {
            if (takeScreenshot) {
                takeScreenshot = false
                return true
            }
            return false
        }
    }

    private fun saveBitmap29(bitmap: Bitmap, str: String) {
        val uri: Uri
        val uri2: Uri?
        val str2 = Environment.DIRECTORY_PICTURES + File.separator + "Magic Fluids"
        val contentResolver: ContentResolver = activity!!.getContentResolver()
        uri = if (Build.VERSION.SDK_INT >= 29) {
            MediaStore.Images.Media.getContentUri("external_primary")
        } else {
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI
        }
        val contentValues = ContentValues()
        contentValues.put("_display_name", "MagicFluids$str.jpg")
        contentValues.put("mime_type", "image/jpeg")
        contentValues.put("relative_path", str2)
        uri2 = try {
            contentResolver.insert(uri, contentValues)
        } catch (e: Exception) {
            null
        }
        try {
            if (uri2 == null) {
                throw IOException("Failed to create new MediaStore record.")
            }
            val openOutputStream = contentResolver.openOutputStream(uri2)
                ?: throw IOException("Failed to get output stream.")
            if (!bitmap.compress(Bitmap.CompressFormat.JPEG, 100, openOutputStream)) {
                throw IOException("Failed to save bitmap.")
            }
            if (openOutputStream != null) {
                openOutputStream.close()
            }
            val finalUri: Uri = uri2
            activity.runOnUiThread(Runnable
            // java.lang.Runnable
            {
                if (activity is WallpaperActivity) {
                    activity.onScreenshotSaved(finalUri)
                }
            })
        } catch (e2: Exception) {
            if (uri2 != null) {
                contentResolver.delete(uri2, null, null)
            }
            activity.runOnUiThread(Runnable
            // java.lang.Runnable
            { Toast.makeText(activity, "Unable to save screenshot", Toast.LENGTH_LONG).show() })
        }
    }

    private fun saveBitmap(bitmap: Bitmap) {
//        shareBitmap(bitmap)
        activityShare?.let {
            GlobalScope.launch(Dispatchers.Main){
                if (it is WallpaperActivity){
                    it.saveConfigValuesToExportFolder(bitmap)
                    it.hideCreateLoading()
                }
                if (it is WallpaperLiveViewActivity) {
                    it.hideCreateLoading()
                }
            }
        }
    }



    private fun shareBitmap(bitmap: Bitmap) {
        try {

            activityShare?.let { activity ->
                // Tạo một Intent để chia sẻ
                val shareIntent = Intent(Intent.ACTION_SEND)

                // Đặt loại dữ liệu là ảnh
                shareIntent.type = "image/*"

                // Chuyển đổi Bitmap thành ByteArray và đặt nó làm dữ liệu của Intent
                val bytes = ByteArrayOutputStream()
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, bytes)
                val path = MediaStore.Images.Media.insertImage(
                    activity.contentResolver, bitmap, activity.getString(
                        R.string.app_name
                    ), null
                )
                val imageUri = Uri.parse(path)
                shareIntent.putExtra(Intent.EXTRA_STREAM, imageUri)

                // Bắt đầu Activity để chia sẻ
                activity.startActivity(Intent.createChooser(shareIntent, "Share Image"))
            }

        } catch (e: java.lang.Exception) {
            e.printStackTrace()
        }
    }


    fun screenshot() {
        val i = screenWidth
        val i2 = screenHeight
        val iArr = IntArray(i * i2)
        val allocate = ByteBuffer.allocate(i * i2 * 4)
        GLES20.glGetError()
        GLES20.glPixelStorei(3333, 1)
        val glGetError = GLES20.glGetError()
        com.fozechmoblive.fluidwallpaper.livefluid.ui.component.wallpaper.fluids.LogUtil.i("screenshot()", "glPixelStorei result: $glGetError")
        GLES20.glReadPixels(0, 0, screenWidth, screenHeight, 6408, 5121, allocate)
        val glGetError2 = GLES20.glGetError()
        com.fozechmoblive.fluidwallpaper.livefluid.ui.component.wallpaper.fluids.LogUtil.i("screenshot()", "glReadPixels result: $glGetError2")
        val array = allocate.array()
        var i3 = 0
        var z = false
        while (true) {
            val i4 = screenHeight
            if (i3 >= i4) {
                saveBitmap(Bitmap.createBitmap(iArr, screenWidth, i4, Bitmap.Config.ARGB_8888))
                com.fozechmoblive.fluidwallpaper.livefluid.ui.component.wallpaper.fluids.LogUtil.i("screenshot()", "wtf: " + java.lang.Boolean.toString(z))
                return
            }
            var i5 = 0
            while (true) {
                val i6 = screenWidth
                if (i5 < i6) {
                    val i7 = screenHeight
                    val b = array[((i7 - 1 - i3) * i6 + i5) * 4]
                    val b2 = array[((i7 - 1 - i3) * i6 + i5) * 4 + 1]
                    val b3 = array[((i7 - 1 - i3) * i6 + i5) * 4 + 2]
                    if (b < 0 || b2 < 0 || b3 < 0) {
                        z = true
                    }
                    iArr[screenWidth * i3 + i5] =
                        (byteToUint(b) shl 16) + ViewCompat.MEASURED_STATE_MASK + (byteToUint(b2) shl 8) + byteToUint(
                            b3
                        )
                    i5++
                } else {
                    break
                }
            }
            i3++
        }
    }

    // android.opengl.GLSurfaceView.Renderer
    override fun onDrawFrame(gl10: GL10) {
        val nanoTime = System.nanoTime()
        val j = nanoTime - lastNanoTime
        avgTimeSamplesSum += j
        avgTimeNumSamples++
        if (j > maxTime) {
            maxTime = j
        }
        lastNanoTime = nanoTime
        if (avgTimeNumSamples == 25) {
            activity?.let {
                if (activity is WallpaperActivity) {
                    activity.updateFrameTime((avgTimeSamplesSum.toFloat() / 25.0f / 1000000.0f).toString() + " Max: " + maxTime.toFloat() / 1000000.0f)
                }
            }
            avgTimeSamplesSum = 0L
            avgTimeNumSamples = 0
            maxTime = 0L
        }
        com.fozechmoblive.fluidwallpaper.livefluid.ui.component.wallpaper.fluids.InputBuffer.Instance.getCurrentInputState(input)
        for (i in 0 until input.NumEvents) {
            if (i < 32 || input.Events[i].Type != 2) {
                nativeInterface.onMotionEvent(input.Events[i])
            }
        }
        val presetActivity: Activity? = activity
        nativeInterface.updateApp(
            ignoreNextFrameTime,
            presetActivity != null && if (presetActivity is WallpaperActivity) presetActivity.isActivePaused() else false,
            orientationSensor.AccelerationX,
            orientationSensor.AccelerationY,
            orientationSensor.Orientation
        )
        ignoreNextFrameTime = false
        if (checkScreenshotOrder()) {
            screenshot()
            ignoreNextFrameTime = true
        }
    }

    // android.opengl.GLSurfaceView.Renderer
    override fun onSurfaceChanged(gl10: GL10, i: Int, i2: Int) {
        screenWidth = i
        screenHeight = i2
        nativeInterface.windowChanged(i, i2)
        com.fozechmoblive.fluidwallpaper.livefluid.ui.component.wallpaper.fluids.LogUtil.i("GLES20RENDERER", "onSurfaceChanged$i $i2")
    }

    fun checkGPUExtensions() {
        com.fozechmoblive.fluidwallpaper.livefluid.ui.component.wallpaper.fluids.LogUtil.i("GLEXT:", GLES20.glGetString(7939))
        val glGetString = GLES20.glGetString(7939)
        nativeInterface.setAvailableGPUExtensions(
            glGetString.contains("GL_OES_texture_half_float") && glGetString.contains(
                "GL_OES_texture_half_float_linear"
            ), glGetString.contains("GL_EXT_color_buffer_half_float")
        )
    }

    // android.opengl.GLSurfaceView.Renderer
    override fun onSurfaceCreated(gl10: GL10, eGLConfig: EGLConfig) {
        checkGPUExtensions()
        if (isLWP) {
            return
        }
        com.fozechmoblive.fluidwallpaper.livefluid.ui.component.wallpaper.fluids.LogUtil.i("GLES20RENDERER", "onSurfaceCreated: Not a wallpaper. Reloading resources")
        onEGLContextCreated()
    }

    private val isLWP: Boolean
        private get() = activity == null

    fun onEGLContextCreated() {
        nativeInterface.onGLContextRestarted()
    }

    companion object {
        fun byteToUint(b: Byte): Int {
            return if (b < 0) b + 256 else b.toInt()
        }
    }
}