package com.fozechmoblive.fluidwallpaper.livefluid.ui.bases

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Color
import android.graphics.LinearGradient
import android.graphics.Shader
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.lifecycleScope
import com.fozechmoblive.fluidwallpaper.livefluid.R

import com.fozechmoblive.fluidwallpaper.livefluid.utils.EasyPreferences
import com.fozechmoblive.fluidwallpaper.livefluid.utils.LocaleHelper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import timber.log.Timber

abstract class BaseActivity<VB : ViewDataBinding> : AppCompatActivity() {
    lateinit var binding: VB
    lateinit var prefs: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        initWindow()
        fullScreenCall()
        super.onCreate(savedInstanceState)

        prefs = EasyPreferences.defaultPrefs(this)

        requestWindow()

        val layoutView = getLayoutActivity()
        binding = DataBindingUtil.setContentView(this, layoutView)
        binding.lifecycleOwner = this

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
            mSuccessDialog.show()

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
        }
    }


    fun showCreateLoading() {
        if (!mCreatingDialog.isShowing && !isFinishing) {
            mCreatingDialog.show()
        }
    }


    fun hideCreateLoading() {
        if (mCreatingDialog.isShowing && !isFinishing) {
            mCreatingDialog.dismiss()
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
            val shareIntent = Intent(Intent.ACTION_SEND).apply {
                type = "text/plain"
                putExtra(Intent.EXTRA_SUBJECT, resources.getString(R.string.app_name))
            }
            var shareMessage = "Let me recommend you this application\nDownload now:\n\n"
            shareMessage =
                shareMessage + "https://play.google.com/store/apps/details?id=" + packageName
            shareIntent.putExtra(Intent.EXTRA_TEXT, shareMessage)
            startActivity(Intent.createChooser(shareIntent, "choose one"))
        } catch (e: java.lang.Exception) {
            e.printStackTrace()
        }
    }


    open fun initWindow() {
        window.apply {
            val background: Drawable = ColorDrawable(Color.parseColor("#FFFFFF"))
            addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            statusBarColor = resources.getColor(android.R.color.black)
            setBackgroundDrawable(background)
            decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            window.setFlags(
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
            )
        }
    }

    private fun fullScreenCall() {
        val decorView = window.decorView
        val uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
        decorView.systemUiVisibility = uiOptions
    }

    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)
        if (hasFocus) {
            fullScreenImmersive(window)
        }
    }

    private fun fullScreenImmersive(window: Window?) {
        if (window != null) {
            fullScreenImmersive(window.decorView)
        }
    }

    private fun fullScreenImmersive(view: View) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            val uiOptions =
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or
                        View.SYSTEM_UI_FLAG_HIDE_NAVIGATION or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
            view.systemUiVisibility = uiOptions
        }
    }

    override fun attachBaseContext(base: Context) {
        super.attachBaseContext(LocaleHelper.setLocale(base))
    }

    override fun onResume() {
        super.onResume()
        val windowInsetsControllerOne: WindowInsetsControllerCompat? =
            if (Build.VERSION.SDK_INT >= 30) {
                ViewCompat.getWindowInsetsController(window.decorView)
            } else {
                WindowInsetsControllerCompat(window, binding.root)
            }
        if (windowInsetsControllerOne == null) {
            return
        }
        windowInsetsControllerOne.systemBarsBehavior = WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
        windowInsetsControllerOne.hide(WindowInsetsCompat.Type.navigationBars())
        windowInsetsControllerOne.hide(WindowInsetsCompat.Type.systemGestures())
        window.decorView.setOnSystemUiVisibilityChangeListener { i: Int ->
            if (i == 0) {
                Handler(Looper.getMainLooper()).postDelayed({
                    val windowInsetsControllerTwo: WindowInsetsControllerCompat? =
                        if (Build.VERSION.SDK_INT >= 30) {
                            ViewCompat.getWindowInsetsController(window.decorView)
                        } else {
                            WindowInsetsControllerCompat(window, binding.root)
                        }
                    if (windowInsetsControllerTwo != null) {
                        windowInsetsControllerTwo.hide(WindowInsetsCompat.Type.navigationBars())
                        windowInsetsControllerTwo.hide(WindowInsetsCompat.Type.systemGestures())
                    }
                }, 2000)
            }
        }
    }
}