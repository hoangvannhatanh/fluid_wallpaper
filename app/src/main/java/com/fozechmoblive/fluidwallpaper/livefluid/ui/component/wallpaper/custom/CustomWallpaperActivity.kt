package com.fozechmoblive.fluidwallpaper.livefluid.ui.component.wallpaper.custom

import android.annotation.SuppressLint
import android.app.ActivityManager
import android.opengl.GLSurfaceView
import android.os.Looper
import android.support.v4.media.session.PlaybackStateCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.work.impl.Scheduler
import com.fozechmoblive.fluidwallpaper.livefluid.R
import com.fozechmoblive.fluidwallpaper.livefluid.databinding.ActivityCustomWallpaperBinding
import com.fozechmoblive.fluidwallpaper.livefluid.models.PresetModel
import com.fozechmoblive.fluidwallpaper.livefluid.ui.bases.BaseActivity
import com.fozechmoblive.fluidwallpaper.livefluid.ui.component.wallpaper.fluids.GLES20Renderer
import com.fozechmoblive.fluidwallpaper.livefluid.utils.CommonData
import com.fozechmoblive.fluidwallpaper.livefluid.utils.Routes
import com.magicfluids.Config
import com.magicfluids.NativeInterface

class CustomWallpaperActivity : BaseActivity<ActivityCustomWallpaperBinding>() {

    private lateinit var wallpaperAdapter: WallpaperCustomAdapter
    private var listPresetModelTotal = arrayListOf<PresetModel>()
    private lateinit var orientationSensor: com.fozechmoblive.fluidwallpaper.livefluid.ui.component.wallpaper.fluids.OrientationSensor
    private var mGLSurfaceView: GLSurfaceView? = null
    private var nativeInterface: NativeInterface? = null
    private var renderer: GLES20Renderer? = null

    private var presetModel: PresetModel? = null

    override fun getLayoutActivity(): Int = R.layout.activity_custom_wallpaper


    @SuppressLint("RestrictedApi")
    override fun initViews() {
        super.initViews()
        loadDataPreset()
        mBinding.listWallpaperView.rcvCustom.apply {
            layoutManager = LinearLayoutManager(
                this@CustomWallpaperActivity,
                LinearLayoutManager.HORIZONTAL,
                false
            )
            adapter = wallpaperAdapter
        }


        mBinding.surfaceView.preserveEGLContextOnPause = wantToPreserveEGLContext()
        mGLSurfaceView = mBinding.surfaceView
        nativeInterface = NativeInterface()
        nativeInterface?.setAssetManager(assets)
        orientationSensor =
            com.fozechmoblive.fluidwallpaper.livefluid.ui.component.wallpaper.fluids.OrientationSensor(
                this@CustomWallpaperActivity,
                application
            )
        mGLSurfaceView?.setEGLContextClientVersion(2)
        val gLSurfaceView = mGLSurfaceView
        val gLES20Renderer = GLES20Renderer(
            this@CustomWallpaperActivity, null, nativeInterface!!, orientationSensor
        )

        renderer = gLES20Renderer
        gLSurfaceView!!.setRenderer(gLES20Renderer)
        renderer!!.setInitialScreenSize(300, Scheduler.MAX_GREEDY_SCHEDULER_LIMIT)
        nativeInterface?.onCreate(300, Scheduler.MAX_GREEDY_SCHEDULER_LIMIT, false)

        updateConfig()
    }

    private fun loadConfigPreset() {
        val presetName = presetModel?.name
        com.fozechmoblive.fluidwallpaper.livefluid.ui.component.wallpaper.fluids.SettingsStorage.loadConfigFromInternalPreset(presetName, assets, Config.Current)

    }

    private fun wantToPreserveEGLContext(): Boolean {
        val memoryInfo = ActivityManager.MemoryInfo()
        (getSystemService(ACTIVITY_SERVICE) as ActivityManager).getMemoryInfo(memoryInfo)
        val j = memoryInfo.totalMem / PlaybackStateCompat.ACTION_SET_CAPTIONING_ENABLED
        return j > 3000
    }

    override fun onResume() {
        super.onResume()
        android.os.Handler(Looper.getMainLooper()).postDelayed({
            mGLSurfaceView?.onResume()
            nativeInterface?.onResume()
        }, 200)

    }

    override fun onDestroy() {
        super.onDestroy()
        nativeInterface?.onDestroy()

    }

    private fun loadDataPreset() {
        listPresetModelTotal.clear()
        listPresetModelTotal.addAll(CommonData.getListPreset())
        presetModel = listPresetModelTotal[0]
        mBinding.txtTitle.text = presetModel?.name
        loadConfigPreset()

        wallpaperAdapter =
            WallpaperCustomAdapter(choosePosition = 0, onClickItemSound = { title, position ->
                mBinding.txtTitle.text = title
                presetModel = listPresetModelTotal[position]


                loadConfigPreset()
                updateConfig()
            })
        wallpaperAdapter.submitData(listPresetModelTotal)
        wallpaperAdapter.setMList(listPresetModelTotal)
    }

    override fun onClickViews() {
        super.onClickViews()

        mBinding.imageBack.setOnClickListener {
            finish()
        }

        mBinding.listWallpaperView.txtNext.setOnClickListener {
            moveToPresetActivity()
        }


    }

    private fun moveToPresetActivity() {
        Routes.startPresetActivity(this, presetModel!!, true)
    }

    private fun updateConfig() {
        nativeInterface?.updateConfig(Config.Current)
    }
}