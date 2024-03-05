package com.fozechmoblive.fluidwallpaper.livefluid.ui.component.wallpaper

import android.Manifest
import android.annotation.SuppressLint
import android.app.ActivityManager
import android.app.Dialog
import android.content.ComponentName
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.opengl.GLSurfaceView
import android.os.Build
import android.os.Looper
import android.support.v4.media.session.PlaybackStateCompat
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.WindowManager
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import androidx.work.impl.Scheduler
import com.facebook.shimmer.ShimmerFrameLayout
import com.fozechmoblive.fluidwallpaper.livefluid.R
import com.magicfluids.Config
import com.magicfluids.NativeInterface

import com.fozechmoblive.fluidwallpaper.livefluid.app.AppConstants
import com.fozechmoblive.fluidwallpaper.livefluid.databinding.ActivityWallpaperLiveViewBinding
import com.fozechmoblive.fluidwallpaper.livefluid.databinding.DialogOptionEditModeBinding
import com.fozechmoblive.fluidwallpaper.livefluid.extentions.setBackGroundDrawable
import com.fozechmoblive.fluidwallpaper.livefluid.models.PresetModel
import com.fozechmoblive.fluidwallpaper.livefluid.services.WallpaperService
import com.fozechmoblive.fluidwallpaper.livefluid.ui.bases.BaseActivity
import com.fozechmoblive.fluidwallpaper.livefluid.ui.bases.ext.click
import com.fozechmoblive.fluidwallpaper.livefluid.ui.component.wallpaper.fluids.GLES20Renderer
import com.fozechmoblive.fluidwallpaper.livefluid.utils.Routes
import com.fozechmoblive.fluidwallpaper.livefluid.utils.TypePresetModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class WallpaperLiveViewActivity : BaseActivity<ActivityWallpaperLiveViewBinding>() {

    private var presetModel: PresetModel? = null
    private lateinit var orientationSensor: com.fozechmoblive.fluidwallpaper.livefluid.ui.component.wallpaper.fluids.OrientationSensor
    private var mGLSurfaceView: GLSurfaceView? = null
    private var nativeInterface: NativeInterface? = null
    private var renderer: GLES20Renderer? = null
    private var settingsController: com.fozechmoblive.fluidwallpaper.livefluid.ui.component.wallpaper.fluids.SettingsController? = null
    private var shimmerSmall: ShimmerFrameLayout? = null
    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted: Boolean ->
        if (isGranted) {
            shareImage()
        } else {
            // Explain to the user that the feature is unavailable because the
            // feature requires a permission that the user has denied. At the
            // same time, respect the user's decision. Don't link to system
            // settings in an effort to convince the user to change their
            // decision.
        }
    }

    @Volatile
    var activePause = false

    override fun getLayoutActivity(): Int = R.layout.activity_wallpaper_live_view

    override fun initViews() {
        super.initViews()
        loadConfigPreset()
        loadDataSettingController()
    }

    override fun onClickViews() {
        super.onClickViews()
        binding.imageBack.setOnClickListener {
            finish()
        }

        binding.btnSetThemes.click {
            applyWallpaper()
        }

        binding.imageSetting.click {
            showOptionEditMode()
        }

        binding.imageShare.setOnClickListener {
            if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.Q) {
                when (PackageManager.PERMISSION_GRANTED) {
                    ContextCompat.checkSelfPermission(
                        this, Manifest.permission.READ_MEDIA_IMAGES
                    ) -> {
                        shareImage()
                    }

                    else -> {
                        // You can directly ask for the permission.
                        // The registered ActivityResultCallback gets the result of this request.
                        requestPermissionLauncher.launch(
                            Manifest.permission.READ_MEDIA_IMAGES
                        )
                    }
                }
            } else {
                shareImage()
            }

        }

    }

    private fun applyWallpaper() {
        applyCurrentSettingsToLwp()
        setLiveWallpaper()
        onSettingsChanged()
    }

    private fun setLiveWallpaper() {
        try {
            val componentName = ComponentName(
                packageName, WallpaperService::class.java.name
            )
            val intent = Intent("android.service.wallpaper.CHANGE_LIVE_WALLPAPER")
            intent.putExtra(
                "android.service.wallpaper.extra.LIVE_WALLPAPER_COMPONENT", componentName
            )
            startActivity(intent)
        } catch (unused: Exception) {
            Toast.makeText(this, "error:" + getText(R.string.not_supported), Toast.LENGTH_SHORT)
                .show()
        }
    }

    private fun applyCurrentSettingsToLwp() {
        applySettingsToLwp(true, -1)
    }

    @SuppressLint("RestrictedApi")
    private fun loadDataSettingController() {
        lifecycleScope.launch(Dispatchers.IO) {
            settingsController = com.fozechmoblive.fluidwallpaper.livefluid.ui.component.wallpaper.fluids.SettingsController()
            binding.surfaceView.preserveEGLContextOnPause = wantToPreserveEGLContext()
            mGLSurfaceView = binding.surfaceView
            nativeInterface = NativeInterface()
            nativeInterface?.setAssetManager(assets)
            orientationSensor =
                com.fozechmoblive.fluidwallpaper.livefluid.ui.component.wallpaper.fluids.OrientationSensor(
                    this@WallpaperLiveViewActivity,
                    application
                )
            mGLSurfaceView?.setEGLContextClientVersion(2)
            val gLSurfaceView = mGLSurfaceView
            val gLES20Renderer = GLES20Renderer(
                this@WallpaperLiveViewActivity, null, nativeInterface!!, orientationSensor
            )

            renderer = gLES20Renderer
            gLSurfaceView!!.setRenderer(gLES20Renderer)
            renderer!!.setInitialScreenSize(300, Scheduler.MAX_GREEDY_SCHEDULER_LIMIT)
            nativeInterface?.onCreate(300, Scheduler.MAX_GREEDY_SCHEDULER_LIMIT, false)
            onSettingsChanged()
        }
    }

    private fun shareImage() {
        showCreateLoading()
        lifecycleScope.launch(Dispatchers.IO) {
            renderer?.orderScreenshot()
        }
    }

    private fun showOptionEditMode() {
        val dialog = Dialog(this@WallpaperLiveViewActivity)
        var isEditMode = false
        hideNavigation(dialog)
        val selectBinding = DialogOptionEditModeBinding.inflate(LayoutInflater.from(this@WallpaperLiveViewActivity))
        dialog.setContentView(selectBinding.root)
        val window = dialog.window
        window?.setLayout(
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.WRAP_CONTENT
        )
        window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        val windowAttributes = window?.attributes
        windowAttributes?.gravity = Gravity.CENTER

        selectBinding.ivClose.setOnClickListener {
            dialog.dismiss()
            hideNavigation(dialog)
        }

        selectBinding.loBasic.setOnClickListener {
            selectBinding.loBasic.setBackGroundDrawable(R.drawable.border_item_background)
            selectBinding.loAdvanced.setBackGroundDrawable(R.drawable.border_item_background_dis)
            isEditMode = false
        }

        selectBinding.loAdvanced.setOnClickListener {
            selectBinding.loBasic.setBackGroundDrawable(R.drawable.border_item_background_dis)
            selectBinding.loAdvanced.setBackGroundDrawable(R.drawable.border_item_background)
            isEditMode = true
        }

        selectBinding.btnOk.setOnClickListener {
            dialog.dismiss()
            hideNavigation(dialog)
            moveToPresetActivity(isEditMode)
        }
        dialog.setCancelable(false)
        hideNavigation(dialog)
        dialog.show()
    }

    private fun hideNavigation(dialog: Dialog) {
        dialog.window?.decorView?.systemUiVisibility =
            View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
    }

    private fun moveToPresetActivity(isEditMode: Boolean) {
        Routes.startPresetActivity(this, presetModel!!, false, isEditMode)
    }

    private fun applySettingsToLwp(z: Boolean, i: Int) {
        if (z) {
            Config.LWPCurrent.copyValuesFrom(Config.Current)
        } else {
            com.fozechmoblive.fluidwallpaper.livefluid.ui.component.wallpaper.fluids.SettingsStorage.loadConfigFromInternalPreset(presetModel?.name, assets, Config.LWPCurrent)

        }
        com.fozechmoblive.fluidwallpaper.livefluid.ui.component.wallpaper.fluids.SettingsStorage.saveSessionConfig(
            this, Config.LWPCurrent, com.fozechmoblive.fluidwallpaper.livefluid.ui.component.wallpaper.fluids.SettingsStorage.SETTINGS_LWP_NAME
        )
        Config.LWPCurrent.ReloadRequired = true
        Config.LWPCurrent.ReloadRequiredPreview = true

    }

    private fun loadConfigPreset() {
        if (intent.hasExtra(AppConstants.KEY_PRESET_MODEL)) {
            presetModel = intent.getParcelableExtra<PresetModel>(AppConstants.KEY_PRESET_MODEL) as PresetModel
            if (presetModel?.typePresetModel == TypePresetModel.CUSTOM) {
                com.fozechmoblive.fluidwallpaper.livefluid.ui.component.wallpaper.fluids.SettingsStorage.loadConfigPresetCustom(presetModel?.pathFluidCustom, Config.Current)
            } else
                com.fozechmoblive.fluidwallpaper.livefluid.ui.component.wallpaper.fluids.SettingsStorage.loadConfigFromInternalPreset(presetModel?.name, assets, Config.Current)
        } else {
            nativeInterface?.randomizeConfig(Config.Current)
        }

    }

    private fun onSettingsChanged(presetModel: String? = "") {
        nativeInterface?.updateConfig(Config.Current)
    }

    private fun wantToPreserveEGLContext(): Boolean {
        val memoryInfo = ActivityManager.MemoryInfo()
        (getSystemService(ACTIVITY_SERVICE) as ActivityManager).getMemoryInfo(memoryInfo)
        val j = memoryInfo.totalMem / PlaybackStateCompat.ACTION_SET_CAPTIONING_ENABLED
        val z = j > 3000
        return z
    }

    override fun onResume() {
        super.onResume()
        android.os.Handler(Looper.getMainLooper()).postDelayed({
            mGLSurfaceView?.onResume()
            nativeInterface?.onResume()
        }, 200)
    }

}