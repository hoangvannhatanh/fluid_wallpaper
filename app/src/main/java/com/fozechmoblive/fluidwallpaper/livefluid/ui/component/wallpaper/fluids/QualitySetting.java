package com.fozechmoblive.fluidwallpaper.livefluid.ui.component.wallpaper.fluids;

import android.os.Build;

import com.magicfluids.Config;
import com.magicfluids.ConfigID;

import java.util.ArrayList;


public class QualitySetting {
    public static ArrayList<String> EffectsSettings;
    public static ArrayList<String> PaintResSettings;
    public static ArrayList<String> SimResSettings;

    public static void init() {
        if (SimResSettings != null) {
            return;
        }
        ArrayList<String> arrayList = new ArrayList<>();
        SimResSettings = arrayList;
        arrayList.add("Low");
        SimResSettings.add("Medium (recommended)");
        SimResSettings.add("High");
        ArrayList<String> arrayList2 = new ArrayList<>();
        PaintResSettings = arrayList2;
        arrayList2.add("Lowest (no GPU support)");
        PaintResSettings.add("Low");
        PaintResSettings.add("Medium");
        PaintResSettings.add("High");
        PaintResSettings.add("Very high");
        PaintResSettings.add("Best");
        ArrayList<String> arrayList3 = new ArrayList<>();
        EffectsSettings = arrayList3;
        arrayList3.add("Low");
        EffectsSettings.add("Medium");
        EffectsSettings.add("High");
        EffectsSettings.add("Best");
    }

    private static int getNumberOfCores() {
        if (Build.VERSION.SDK_INT >= 17) {
            return Runtime.getRuntime().availableProcessors();
        }
        return 1;
    }

    public static void setQualitySettingsToDefault(Config config) {
        int i;
        int i2;
        int i3 = 0;
        int i4 = 2;
        int i5 = 1;
        if (getNumberOfCores() <= 2) {
            i = 1;
            i2 = 0;
        } else {
            i = 2;
            i4 = 3;
            i2 = 1;
        }
        if (Build.VERSION.SDK_INT <= 19) {
            i2 = 0;
        } else {
            i5 = i;
            i3 = i4;
        }
        config.setInt(ConfigID.GPU_ANIMATION, i3);
        config.setInt(ConfigID.QUALITY_BASE_VALUE, i2);
        config.setInt(ConfigID.EFFECTS_QUALITY, i5);
    }
}
