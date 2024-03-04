package com.fozechmoblive.fluidwallpaper.livefluid.services;

import android.content.Intent;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;

import androidx.work.impl.Scheduler;
import com.fozechmoblive.fluidwallpaper.livefluid.app.AppConstants;
import com.fozechmoblive.fluidwallpaper.livefluid.ui.component.wallpaper.fluids.GLES20Renderer;
import com.fozechmoblive.fluidwallpaper.livefluid.ui.component.wallpaper.fluids.InputBuffer;
import com.fozechmoblive.fluidwallpaper.livefluid.ui.component.wallpaper.fluids.OrientationSensor;
import com.fozechmoblive.fluidwallpaper.livefluid.ui.component.wallpaper.fluids.QualitySetting;
import com.fozechmoblive.fluidwallpaper.livefluid.ui.component.wallpaper.fluids.SettingsStorage;
import com.magicfluids.Config;
import com.magicfluids.ConfigID;
import com.magicfluids.NativeInterface;

/* loaded from: classes.dex */
public class WallpaperService extends GLWallpaperServiceRBG {
    public static OpenGLES2Engine mostRecentEngine;
    private boolean isSetWallpaper = false;

    @Override
    public Engine onCreateEngine() {
        return new OpenGLES2Engine();
    }

    /* loaded from: classes.dex */
    public class OpenGLES2Engine extends GLEngine {
        public NativeInterface ntv;
        private OrientationSensor orientationSensor;
        private GLES20Renderer renderer;

        OpenGLES2Engine() {
            super();
        }

        private void logE(String str) {
            Log.i("LWP", toString() + ":" + str);
        }

        @Override
        public void onCreate(SurfaceHolder surfaceHolder) {
            super.onCreate(surfaceHolder);
            WallpaperService.mostRecentEngine = this;
            setTouchEventsEnabled(true);
            this.ntv = new NativeInterface();
            setEGLContextClientVersion(2);
            WallpaperService wallpaperService = WallpaperService.this;
            OrientationSensor orientationSensor = new OrientationSensor(wallpaperService, wallpaperService.getApplication());
            this.orientationSensor = orientationSensor;
            GLES20Renderer gLES20Renderer = new GLES20Renderer(null,null, this.ntv, orientationSensor);
            this.renderer = gLES20Renderer;
            setRenderer(gLES20Renderer);
            this.renderer.setInitialScreenSize(300, Scheduler.MAX_GREEDY_SCHEDULER_LIMIT);
            this.ntv.setAssetManager(WallpaperService.this.getAssets());
            this.ntv.onCreate(300, Scheduler.MAX_GREEDY_SCHEDULER_LIMIT, true);
//            Preset.init();
            QualitySetting.init();
            SettingsStorage.loadSessionConfig(WallpaperService.this, Config.LWPCurrent, SettingsStorage.SETTINGS_LWP_NAME);
//            if (RunManager.getNumLwpRuns(NewWallpaperService.this) == 0) {
//                Preset.List.get(0).fillConfig(Config.LWPCurrent, NewWallpaperService.this.getAssets());
//                QualitySetting.setQualitySettingsToDefault(Config.LWPCurrent);
//            }
            this.ntv.updateConfig(Config.LWPCurrent);
            logE("GLEngine onCreate end. NTV ID: " + this.ntv.getID());
        }

        @Override // android.service.wallpaper.WallpaperService.Engine
        public void onDesiredSizeChanged(int i, int i2) {
            this.ntv.windowChanged(i, i2);
            logE("GLEngine onDesiredSizeChanged. NTV ID: " + this.ntv.getID());
            super.onDesiredSizeChanged(i, i2);
        }

        @Override

        public void onVisibilityChanged(boolean visible) {
            super.onVisibilityChanged(visible);
            logE("GLEngine onVisibilityChanged to " + visible + ", NTV ID: " + this.ntv.getID());

            if (visible) {
                this.ntv.updateConfig(Config.LWPCurrent);
                if (!isPreview() && Config.LWPCurrent.ReloadRequired) {
                    this.ntv.clearScreen();
                    Config.LWPCurrent.ReloadRequired = false;
                    isSetWallpaper = true;

//                    Bundle bundle = new Bundle();
//                    bundle.putBoolean(AppConstants.KEY_SET_WALLPAPER_SUCCESS, true);
//                    Routes.INSTANCE.startMainActivity(NewWallpaperService.this, bundle);

                }
                if (isPreview() && Config.LWPCurrent.ReloadRequiredPreview) {
                    this.ntv.clearScreen();
                    Config.LWPCurrent.ReloadRequiredPreview = false;
                }
                this.ntv.onResume();
                if (Config.LWPCurrent.getBool(ConfigID.RANDOMIZE_ON_RESUME)) {
                    this.ntv.randomizeConfig(Config.LWPCurrent);
                }
                if (Config.LWPCurrent.getFloat(ConfigID.GRAVITY) > 3.0E-4f) {
                    this.orientationSensor.register();
                    return;
                } else {
                    this.orientationSensor.unregister();
                    return;
                }
            }
            this.ntv.onPause();
            this.orientationSensor.unregister();
        }

        @Override
        public void onDestroy() {
            super.onDestroy();
            if (isSetWallpaper){
                Intent intent = new Intent(AppConstants.ACTION_DESTROY_WALLPAPER_SERVICE);
                sendBroadcast(intent);
            }
            this.ntv.onDestroy();
            this.orientationSensor.unregister();
        }

        @Override // android.service.wallpaper.WallpaperService.Engine
        public void onTouchEvent(MotionEvent motionEvent) {
            InputBuffer.Instance.addEvent(motionEvent);
        }
    }
}
