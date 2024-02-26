package com.fozechmoblive.fluidwallpaper.livefluid.ui.component.wallpaper

import android.annotation.SuppressLint
import android.app.ActivityManager
import android.content.BroadcastReceiver
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.graphics.Bitmap
import android.net.Uri
import android.opengl.GLSurfaceView
import android.os.Handler
import android.os.Looper
import android.support.v4.media.session.PlaybackStateCompat
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.lifecycle.lifecycleScope
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import androidx.work.impl.Scheduler
import com.ads.control.ads.ITGAd
import com.ads.control.ads.ITGAdCallback
import com.ads.control.ads.wrapper.ApAdError
import com.fozechmoblive.fluidwallpaper.livefluid.BuildConfig
import com.fozechmoblive.fluidwallpaper.livefluid.R
import com.fozechmoblive.fluidwallpaper.livefluid.ads.AdsManager
import com.fozechmoblive.fluidwallpaper.livefluid.ads.CheckTimeShowAdsInter
import com.fozechmoblive.fluidwallpaper.livefluid.ads.RemoteConfigUtils
import com.fozechmoblive.fluidwallpaper.livefluid.app.AppConstants
import com.fozechmoblive.fluidwallpaper.livefluid.app.AppConstants.KEY_IS_CUSTOM
import com.fozechmoblive.fluidwallpaper.livefluid.app.AppConstants.KEY_NAME_EFFECT
import com.fozechmoblive.fluidwallpaper.livefluid.databinding.ActivityWallpaperBinding
import com.fozechmoblive.fluidwallpaper.livefluid.models.PresetModel
import com.fozechmoblive.fluidwallpaper.livefluid.models.Status
import com.fozechmoblive.fluidwallpaper.livefluid.services.WallpaperService
import com.fozechmoblive.fluidwallpaper.livefluid.ui.bases.BaseActivity
import com.fozechmoblive.fluidwallpaper.livefluid.ui.bases.ext.click
import com.fozechmoblive.fluidwallpaper.livefluid.ui.bases.ext.goneView
import com.fozechmoblive.fluidwallpaper.livefluid.ui.bases.ext.showRateDialog
import com.fozechmoblive.fluidwallpaper.livefluid.ui.bases.ext.showToastByString
import com.fozechmoblive.fluidwallpaper.livefluid.ui.bases.ext.visibleView
import com.fozechmoblive.fluidwallpaper.livefluid.ui.component.dialog.DialogSetName
import com.fozechmoblive.fluidwallpaper.livefluid.ui.component.wallpaper.fluids.GLES20Renderer
import com.fozechmoblive.fluidwallpaper.livefluid.ui.component.wallpaper.fluids.OrientationSensor
import com.fozechmoblive.fluidwallpaper.livefluid.ui.component.wallpaper.fluids.QualitySetting
import com.fozechmoblive.fluidwallpaper.livefluid.ui.component.wallpaper.fluids.SettingsController
import com.fozechmoblive.fluidwallpaper.livefluid.ui.component.wallpaper.fluids.SettingsStorage
import com.fozechmoblive.fluidwallpaper.livefluid.utils.EasyPreferences.set
import com.fozechmoblive.fluidwallpaper.livefluid.utils.Routes
import com.fozechmoblive.fluidwallpaper.livefluid.utils.SharePrefUtils
import com.fozechmoblive.fluidwallpaper.livefluid.utils.TypePresetModel
import com.magicfluids.Config
import com.magicfluids.ConfigID
import com.magicfluids.NativeInterface
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File
import java.io.FileOutputStream
import java.io.IOException


class WallpaperActivity : BaseActivity<ActivityWallpaperBinding>() {

    private var presetModel: PresetModel? = null
    private lateinit var orientationSensor: OrientationSensor
    private var mGLSurfaceView: GLSurfaceView? = null
    private var nativeInterface: NativeInterface? = null
    private var renderer: GLES20Renderer? = null
    private val intentFilter = IntentFilter(AppConstants.ACTION_DESTROY_WALLPAPER_SERVICE)
    private var settingsController: SettingsController? = null
    private var isCustom = false
    private var presetNameCustom: String = ""
    private var dialogSetName: DialogSetName? = null
    var config = Config()


    private val serviceDestroyedReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            if (intent?.action == AppConstants.ACTION_DESTROY_WALLPAPER_SERVICE) {

                if (!SharePrefUtils.getBoolean(
                        AppConstants.IS_RATED,
                        false
                    ) && !SharePrefUtils.getBoolean(AppConstants.IS_FIRST_RATED, false)
                ) {
                    if (RemoteConfigUtils.getIsShowRate()) {
                        showRateDialog(this@WallpaperActivity, false)
                        SharePrefUtils.putBoolean(AppConstants.IS_FIRST_RATED, true)

                    } else {
                        showSuccessDialog()

                    }
                } else {
                    showSuccessDialog()
                }

                if (presetModel?.name?.isNotEmpty()!!) {
                    prefs[KEY_NAME_EFFECT] = presetModel?.name
                }
                sendLocalBroadcast()
            }
        }
    }


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

    private fun shareImage() {
        showCreateLoading()
        lifecycleScope.launch(Dispatchers.IO) {
            renderer?.orderScreenshot()
        }
    }

    override fun getLayoutActivity(): Int = R.layout.activity_wallpaper
    override fun initViews() {
        super.initViews()

        loadDataSettingController()
        registerReceiver(serviceDestroyedReceiver, intentFilter)

        lifecycleScope.launch(Dispatchers.IO) {

            withContext(Dispatchers.Main) {
                showCreateLoading()
                loadConfigPreset()
            }

            delay(2000)

            val job = launch(Dispatchers.Main) {
                showSettings()
                hideCreateLoading()
            }

            launch(Dispatchers.Main) {
                job.join()
                if (job.isCompleted) {
                    initAdsBanner()
                    AdsManager.loadInterSetWallpaper(this@WallpaperActivity)
                }
            }

        }

    }


    private fun initAdsBanner() {
        if (RemoteConfigUtils.getOnBannerAll()) {
            ITGAd.getInstance()
                .loadBanner(this, BuildConfig.admob_banner_all, object : ITGAdCallback() {
                    override fun onAdLoaded() {
                        super.onAdLoaded()

                    }

                    override fun onAdFailedToLoad(adError: ApAdError?) {
                        super.onAdFailedToLoad(adError)
                        mBinding.frBanner.removeAllViews()
                    }
                })
        } else mBinding.frBanner.removeAllViews()
    }


    @SuppressLint("RestrictedApi")
    private fun loadDataSettingController() {

        settingsController =
            SettingsController()
        mBinding.surfaceView.preserveEGLContextOnPause = wantToPreserveEGLContext()
        mGLSurfaceView = mBinding.surfaceView
        nativeInterface = NativeInterface()
        nativeInterface?.setAssetManager(assets)
        orientationSensor =
            OrientationSensor(
                this@WallpaperActivity,
                application
            )
        mGLSurfaceView?.setEGLContextClientVersion(2)
        val gLSurfaceView = mGLSurfaceView
        val gLES20Renderer = GLES20Renderer(
            this@WallpaperActivity, this@WallpaperActivity, nativeInterface!!, orientationSensor
        )
        renderer = gLES20Renderer
        gLSurfaceView!!.setRenderer(gLES20Renderer)
        renderer!!.setInitialScreenSize(300, Scheduler.MAX_GREEDY_SCHEDULER_LIMIT)
        nativeInterface?.onCreate(300, Scheduler.MAX_GREEDY_SCHEDULER_LIMIT, false)
        onSettingsChanged()

    }

    private fun showSettings() {
        QualitySetting.init()
        settingsController!!.initControls(this@WallpaperActivity, config)
        mBinding.settingsView.visibleView()
        mBinding.imageSetting.goneView()
        mBinding.imageClose.visibleView()
        mBinding.imageShare.goneView()
        mBinding.imageBack.goneView()
        mBinding.textSetWallpaper.visibleView()
        mBinding.textSetWallpaperBottom.goneView()

    }

    override fun onClickViews() {
        super.onClickViews()
        mBinding.imageBack.setOnClickListener {
            finish()
        }
        mBinding.imageSetting.setOnClickListener {
            showSettings()
        }
        mBinding.imageClose.setOnClickListener {
            mBinding.imageSetting.visibleView()
            mBinding.imageShare.goneView()
            mBinding.settingsView.goneView()
            mBinding.imageClose.goneView()
            mBinding.imageBack.visibleView()
            mBinding.textSetWallpaper.goneView()
            mBinding.textSetWallpaperBottom.visibleView()

        }

        mBinding.textSetWallpaperBottom.click {
            if (isCustom) {
                showDialogCreatePreset()
            } else
                applyWallpaper()
        }
        mBinding.textSetWallpaper.click {
            if (isCustom) {
                showDialogCreatePreset()
            } else
                applyWallpaper()
        }
        mBinding.imageShare.click {
//            if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.Q) {
//                when (PackageManager.PERMISSION_GRANTED) {
//                    ContextCompat.checkSelfPermission(
//                        this, Manifest.permission.READ_EXTERNAL_STORAGE
//                    ) -> {
//                        shareImage()
//                    }
//
//                    else -> {
//                        // You can directly ask for the permission.
//                        // The registered ActivityResultCallback gets the result of this request.
//                        requestPermissionLauncher.launch(
//                            Manifest.permission.READ_EXTERNAL_STORAGE
//                        )
//                    }
//                }
//            } else {
//                shareImage()
//            }
        }
    }

    private fun showDialogCreatePreset() {

        dialogSetName = DialogSetName(this@WallpaperActivity, onCreatePreset = {

            val directory = File(
                this.externalCacheDir,
                "Fluids_Custom"
            )

            if (File(directory.path + "/" + it).exists()) {
                showToastByString(getString(R.string.error_file_exists))
            } else {

                dialogSetName?.dismiss()
                presetNameCustom = it
                applySettingsToLwp(true, -1)
                showCreateLoading()
                lifecycleScope.launch(Dispatchers.IO) {
                    renderer?.orderScreenshot()
                }
            }


        })
        config.getBoolVal(ConfigID.FLASH_ENABLED).Value = true
        onSettingsChanged()
        dialogSetName?.show()


        dialogSetName?.setOnDismissListener {
            config.getBoolVal(ConfigID.FLASH_ENABLED).Value = false
            onSettingsChanged()
        }
    }

    fun onScreenshotSaved(uri: Uri?) {
//        CommonAlerts.showShareImage(this, uri)
    }

    fun saveConfigValuesToExportFolder(bitmap: Bitmap) {
        val configString: String =
            config.displayConfigValues()!! // Gọi hàm displayConfigValues() để lấy chuỗi cấu hình

        // Tạo thư mục "Download/export" trong thư mục lưu trữ chung
        val exportDirectory = File(
            this.externalCacheDir,
            "Fluids_Custom"
        )
        if (!exportDirectory.exists()) {
            exportDirectory.mkdirs() // Tạo thư mục nếu nó chưa tồn tại
        }

        // Tạo tên thư mục con bằng cách kết hợp presetName và thời gian hiện tại
        val subDirectoryName = presetNameCustom
        val subDirectory = File(exportDirectory, subDirectoryName)
        subDirectory.mkdirs() // Tạo thư mục con

        // Tạo tệp trong thư mục con với tên "config.txt"
        val configFileName = "${presetNameCustom}.txt"
        val configFile = File(subDirectory, configFileName)

        try {
            // Mở luồng đầu ra tới tệp config.txt
            val configFos = FileOutputStream(configFile)

            // Ghi chuỗi configString vào tệp
            configFos.write(configString.toByteArray())

            // Đóng luồng đầu ra của config.txt
            configFos.close()

            // Lưu ảnh vào thư mục con với tên "image.png"
            val imageFileName = "${presetNameCustom}.png"
            val imageFile = File(subDirectory, imageFileName)
            val imageFos = FileOutputStream(imageFile)
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, imageFos)
            imageFos.close()

            var presetModelCustom = PresetModel(
                0,
                presetNameCustom,
                Status.FREE,
                false,
                typePresetModel = TypePresetModel.CUSTOM,
                pathFluidCustom = exportDirectory.path + "/" + presetNameCustom + "/" + presetNameCustom + ".txt",
                pathImageCustom = exportDirectory.path + "/" + presetNameCustom + "/" + presetNameCustom + ".png"
            )

            Routes.startCreateDoneActivity(this@WallpaperActivity, presetModelCustom)

        } catch (e: IOException) {
            e.printStackTrace()
            // Xử lý lỗi nếu có
        }
    }


    private fun applyWallpaper() {
        if (AdsManager.mInterstitialAdSetWallpaper != null && AdsManager.mInterstitialAdSetWallpaper!!.isReady && CheckTimeShowAdsInter.isTimeShow) {

            ITGAd.getInstance().forceShowInterstitial(
                this@WallpaperActivity,
                AdsManager.mInterstitialAdSetWallpaper,
                object : ITGAdCallback() {
                    override fun onAdFailedToLoad(adError: ApAdError?) {
                        super.onAdFailedToLoad(adError)
                        applyCurrentSettingsToLwp()
                        setLiveWallpaper()
                        AdsManager.mInterstitialAdSetWallpaper = null
                        AdsManager.loadInterSetWallpaper(this@WallpaperActivity)
                    }

                    override fun onAdClosed() {
                        super.onAdClosed()
                        CheckTimeShowAdsInter.logShowed()
                        applyCurrentSettingsToLwp()
                        setLiveWallpaper()
                        AdsManager.mInterstitialAdSetWallpaper = null
                        AdsManager.loadInterSetWallpaper(this@WallpaperActivity)
                        
                    }

                    override fun onAdFailedToShow(adError: ApAdError?) {
                        super.onAdFailedToShow(adError)
                        applyCurrentSettingsToLwp()
                        setLiveWallpaper()
                        AdsManager.mInterstitialAdSetWallpaper = null
                        AdsManager.loadInterSetWallpaper(this@WallpaperActivity)
                    }
                },
                true
            )

        } else {
            applyCurrentSettingsToLwp()
            setLiveWallpaper()
        }

        onSettingsChanged()
    }

    private fun applyCurrentSettingsToLwp() {
        applySettingsToLwp(true, -1)

    }

    private fun applySettingsToLwp(z: Boolean, i: Int) {
        if (z) {
            Config.LWPCurrent.copyValuesFrom(config)
        } else {
            SettingsStorage.loadConfigFromInternalPreset(
                presetModel?.name,
                assets,
                config
            )

        }
        SettingsStorage.saveSessionConfig(
            this, Config.LWPCurrent, SettingsStorage.SETTINGS_LWP_NAME
        )
        Config.LWPCurrent.ReloadRequired = true
        Config.LWPCurrent.ReloadRequiredPreview = true

    }

    private fun loadConfigPreset() {
        if (intent.hasExtra(AppConstants.KEY_PRESET_MODEL)) {
            presetModel =
                intent.getParcelableExtra<PresetModel>(AppConstants.KEY_PRESET_MODEL) as PresetModel
            if (presetModel?.typePresetModel == TypePresetModel.CUSTOM) {
                SettingsStorage.loadConfigPresetCustom(presetModel?.pathFluidCustom, config)
            } else
                SettingsStorage.loadConfigFromInternalPreset(
                    presetModel?.name,
                    assets,
                    config
                )
        } else {
            nativeInterface?.randomizeConfig(config)
        }

        if (intent.hasExtra(KEY_IS_CUSTOM)) {
            isCustom = intent.getBooleanExtra(KEY_IS_CUSTOM, false)

            if (isCustom) {
                mBinding.imageShare.goneView()
                mBinding.textSetWallpaper.text = getString(R.string.next)
                mBinding.textSetWallpaperBottom.text = getString(R.string.next)
            }
        }

    }

    fun onSettingsChanged(presetModel: String? = "") {
        nativeInterface?.updateConfig(config)
    }

    private fun wantToPreserveEGLContext(): Boolean {
        val memoryInfo = ActivityManager.MemoryInfo()
        (getSystemService(ACTIVITY_SERVICE) as ActivityManager).getMemoryInfo(memoryInfo)
        val j = memoryInfo.totalMem / PlaybackStateCompat.ACTION_SET_CAPTIONING_ENABLED
        val z = j > 3000
        return z
    }

    fun updateFrameTime(s: String) {

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

    fun isActivePaused(): Boolean {
        return this.activePause
    }

    override fun onResume() {
        super.onResume()
        Handler(Looper.getMainLooper()).postDelayed({
            mGLSurfaceView?.onResume()
            nativeInterface?.onResume()
        }, 200)


    }

    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(serviceDestroyedReceiver)

    }


    private fun sendLocalBroadcast() {
        prefs[KEY_NAME_EFFECT] = presetModel?.name
        val intent = Intent(AppConstants.ACTION_CHANGE_DATA)
        intent.putExtra(AppConstants.KEY_CHANGE_DATA, true)
        LocalBroadcastManager.getInstance(this).sendBroadcast(intent)
    }

}