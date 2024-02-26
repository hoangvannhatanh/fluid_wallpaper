package com.fozechmoblive.fluidwallpaper.livefluid.ui.bases

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.res.Configuration
import android.graphics.Color
import android.graphics.LinearGradient
import android.graphics.Shader
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.Window
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.lifecycleScope
import com.fozechmoblive.fluidwallpaper.livefluid.BuildConfig
import com.fozechmoblive.fluidwallpaper.livefluid.R

import com.fozechmoblive.fluidwallpaper.livefluid.app.AppConstants
import com.fozechmoblive.fluidwallpaper.livefluid.utils.EasyPreferences
import com.fozechmoblive.fluidwallpaper.livefluid.utils.ITGTrackingHelper
import com.fozechmoblive.fluidwallpaper.livefluid.utils.Routes
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import timber.log.Timber
import java.util.Locale

private const val TAG = "BaseActivity"

abstract class BaseActivity<VB : ViewDataBinding> : AppCompatActivity() {
    lateinit var mBinding: VB
    lateinit var prefs: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        prefs = EasyPreferences.defaultPrefs(this)

        setLocal()

        requestWindow()
        val layoutView = getLayoutActivity()
        mBinding = DataBindingUtil.setContentView(this, layoutView)
        Log.d(TAG, "onCreate: name Class: ${this::class.java.simpleName}")
//        ITGTrackingHelper.addScreenTrack(this::class.java.simpleName)
//        if (intent.getStringExtra(AppConstants.KEY_TRACKING_SCREEN_FROM) != null) {
//            Routes.addTrackingMoveScreen(
//                intent.getStringExtra(AppConstants.KEY_TRACKING_SCREEN_FROM).toString(),
//                this::class.java.simpleName
//            )
//        }
        mBinding.lifecycleOwner = this

        initViews()
        onResizeViews()
        onClickViews()
        observerData()
    }

    open fun setUpViews() {}

    abstract fun getLayoutActivity(): Int

    open fun requestWindow() {}

    open fun initViews() {}

    open fun onResizeViews() {}

    open fun onClickViews() {}

    open fun observerData() {}

    fun setLocal() {
        val language: String? = prefs.getString(AppConstants.KEY_LANGUAGE, "")
        if (language == "") {
            val config = Configuration()
            val locale = Locale.getDefault()
            Locale.setDefault(locale)
            config.locale = locale
            resources.updateConfiguration(config, resources.displayMetrics)
        } else {
            if (language.equals("", ignoreCase = true)) return
            val locale = Locale(language)
            Locale.setDefault(locale)
            val config = Configuration()
            config.locale = locale
            resources.updateConfiguration(config, resources.displayMetrics)
        }
    }


    override fun onResume() {
        super.onResume()
        hideNavigationBar()
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        hideNavigationBar()

    }

    private fun hideNavigationBar() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            WindowCompat.setDecorFitsSystemWindows(window, false)
            WindowInsetsControllerCompat(window, window.decorView).let { controller ->
                controller.hide(WindowInsetsCompat.Type.systemBars())
                controller.systemBarsBehavior =
                    WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
            }
        } else {
            hideSystemUIBeloR()
        }

    }

    private fun hideSystemUIBeloR() {
        val decorView: View = window.decorView
        val uiOptions = decorView.systemUiVisibility
        var newUiOptions = uiOptions
        newUiOptions = newUiOptions or View.SYSTEM_UI_FLAG_LOW_PROFILE
        newUiOptions = newUiOptions or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
        newUiOptions = newUiOptions or View.SYSTEM_UI_FLAG_IMMERSIVE
        newUiOptions = newUiOptions or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
        decorView.systemUiVisibility = newUiOptions
    }

    class CreatingDialog constructor(context: Context) : Dialog(context, R.style.ThemeDialog) {
        init {
            setCancelable(false)
            requestWindowFeature(Window.FEATURE_NO_TITLE)
            setContentView(R.layout.dialog_show_loading)
            val window = window
            if (window != null) {
                getWindow()?.setBackgroundDrawableResource(android.R.color.transparent)
            }
        }
    }

    class CreatingSuccessDialog constructor(context: Context) :
        Dialog(context, R.style.ThemeDialog) {
        init {
            setCancelable(false)
            requestWindowFeature(Window.FEATURE_NO_TITLE)
            setCancelable(true)
            setContentView(R.layout.dialog_show_success)
            val window = window
            if (window != null) {
                getWindow()?.setBackgroundDrawableResource(android.R.color.transparent)
            }
        }

        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            val textShader: Shader = LinearGradient(
                0f,
                0f,
                findViewById<TextView>(R.id.text_set_success).paint.measureText(context?.getString(R.string.text_title_onboarding_1)),
                20f,
                intArrayOf(Color.parseColor("#EE0979"), Color.parseColor("#FF6A00")),
                floatArrayOf(0f, 1f),
                Shader.TileMode.CLAMP
            )
            findViewById<TextView>(R.id.text_set_success).paint.shader = textShader

        }
    }

    fun showSuccessDialog() {
        if (!mSuccessDialog.isShowing && !isFinishing) {
            Timber.d("showLoading mCreatingDialog")
            mSuccessDialog.show()
            hideNavigationBar()

            lifecycleScope.launch(Dispatchers.IO) {
                delay(2000)
                withContext(Dispatchers.Main) {
                    if (mSuccessDialog.isShowing) {
                        mSuccessDialog.dismiss()
                    }
                }
            }
        }
    }


    fun hideSuccessLoading() {
        if (mSuccessDialog.isShowing && !isFinishing) {
            Timber.d("hideProgress mCreatingDialog")
            mSuccessDialog.dismiss()
            hideNavigationBar()

        }
    }


    fun showCreateLoading() {
        if (!mCreatingDialog.isShowing && !isFinishing) {
            Timber.d("showLoading mCreatingDialog")
            mCreatingDialog.show()
            hideNavigationBar()

        }
    }


    fun hideCreateLoading() {
        if (mCreatingDialog.isShowing && !isFinishing) {
            Timber.d("hideProgress mCreatingDialog")
            mCreatingDialog.dismiss()
            hideNavigationBar()
        }
    }

    private val mCreatingDialog: Dialog by lazy {
        CreatingDialog(this)
    }

    private val mSuccessDialog: Dialog by lazy {
        CreatingSuccessDialog(this)
    }


    fun shareApp() {
        try {
            val shareIntent = Intent(Intent.ACTION_SEND)
            shareIntent.type = "text/plain"
            shareIntent.putExtra(
                Intent.EXTRA_SUBJECT, resources.getString(R.string.app_name)
            )
            var shareMessage = "\nLet me recommend you this application\n\n"
            shareMessage =
                "${shareMessage}https://play.google.com/store/apps/details?id=${BuildConfig.APPLICATION_ID}"
            shareIntent.putExtra(Intent.EXTRA_TEXT, shareMessage)
            shareIntent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(Intent.createChooser(shareIntent, "Choose one"))
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}