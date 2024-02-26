package com.fozechmoblive.fluidwallpaper.livefluid.ui.component.wallpaper.tab;

import android.graphics.drawable.Drawable;
import android.view.View;

import com.google.android.gms.ads.RequestConfiguration;
;
import com.fozechmoblive.fluidwallpaper.livefluid.R;
import com.fozechmoblive.fluidwallpaper.livefluid.ui.component.wallpaper.WallpaperActivity;
import com.magicfluids.Config;
import com.magicfluids.ConfigID;

public class TabInputHold extends TabSet.TabPage {
    @Override
    public String getName() {
        return RequestConfiguration.MAX_AD_CONTENT_RATING_UNSPECIFIED;
    }

    @Override
    public void askForColorPicker(Config.IntVal intVal) {
        super.askForColorPicker(intVal);
    }

    @Override
    public boolean isCreated() {
        return super.isCreated();
    }

    @Override
    public void notifyConfigValueChanged(int i) {
        super.notifyConfigValueChanged(i);
    }

    @Override
    public void refreshState() {
        super.refreshState();
    }

    public TabInputHold(Config config, WallpaperActivity wallpaperActivity) {
        super(config, wallpaperActivity);
    }

    @Override
    public View createContent() {
        super.createContent();
        addNamedSpinner(this.config.getIntVal(ConfigID.INPUT_TOUCH_MODE), "Action", new String[]{"Stream", "2-way blower", "Rotating blower", "Vortex type 1", "Vortex type 2", "Source", "Sink", "Source / sink", "None"});
        addNamedSeekBar(this.config.getFloatVal(ConfigID.TOUCH_INPUT_FORCE), "Speed");
        addNamedSeekBar(this.config.getFloatVal(ConfigID.TOUCH_INPUT_SIZE), "Size");
        addSeparator();
        addNamedIntSeekBar(this.config.getIntVal(ConfigID.NUM_HOLD_SOURCES), 5, "Autoplay sources", null);
        refreshState();
        return this.rootView;
    }

    @Override
    public Drawable getIcon() {
        return this.activity.getResources().getDrawable(R.drawable.but_hand_tap);
    }
}
