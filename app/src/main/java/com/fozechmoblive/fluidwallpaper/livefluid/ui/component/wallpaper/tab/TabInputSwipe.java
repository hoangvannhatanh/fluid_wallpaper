package com.fozechmoblive.fluidwallpaper.livefluid.ui.component.wallpaper.tab;

import android.graphics.drawable.Drawable;
import android.view.View;

import com.google.android.gms.ads.RequestConfiguration;
;
import com.fozechmoblive.fluidwallpaper.livefluid.R;
import com.fozechmoblive.fluidwallpaper.livefluid.ui.component.wallpaper.WallpaperActivity;
import com.magicfluids.Config;
import com.magicfluids.ConfigID;



public class TabInputSwipe extends TabSet.TabPage {
    @Override 
    public String getName() {
        return RequestConfiguration.MAX_AD_CONTENT_RATING_UNSPECIFIED;
    }

    @Override 
    public  void askForColorPicker(Config.IntVal intVal) {
        super.askForColorPicker(intVal);
    }

    @Override 
    public  boolean isCreated() {
        return super.isCreated();
    }

    @Override 
    public  void notifyConfigValueChanged(int i) {
        super.notifyConfigValueChanged(i);
    }

    @Override 
    public  void refreshState() {
        super.refreshState();
    }

    public TabInputSwipe(Config config, WallpaperActivity wallpaperActivity) {
        super(config, wallpaperActivity);
    }

    @Override 
    public View createContent() {
        super.createContent();
        addNamedSpinner(this.config.getIntVal(ConfigID.INPUT_SWIPE_MODE), activity.getString(R.string.text_action),
                new String[]{activity.getString(R.string.text_stream), activity.getString(R.string.inverse_stream)});
        addNamedSeekBar(this.config.getFloatVal(ConfigID.FORCE), activity.getString(R.string.text_speed));
        addNamedSeekBar(this.config.getFloatVal(ConfigID.INPUT_SIZE), activity.getString(R.string.text_size));
        addNamedRegularSwitch(this.config.getBoolVal(ConfigID.INPUT_SWIPE_CONSTANT), activity.getString(R.string.text_constant_speed));
        addSeparator();
        addNamedIntSeekBar(this.config.getIntVal(ConfigID.NUM_SOURCES), 5, activity.getString(R.string.text_autoplay_sources), null);
        addNamedSeekBar(this.config.getFloatVal(ConfigID.SOURCE_SPEED), activity.getString(R.string.text_autoplay_speed));
        Section addNamedInnerSectionSwitch = addNamedInnerSectionSwitch(this.config.getBoolVal(ConfigID.FLASH_ENABLED), activity.getString(R.string.text_autoplay_burst), null);
        increaseMargin();
        addNamedSeekBar(this.config.getFloatVal(ConfigID.FLASH_FREQUENCY), activity.getString(R.string.text_burst_frequency), addNamedInnerSectionSwitch);
        decreaseMargin();
        addNamedRegularSwitch(this.config.getBoolVal(ConfigID.AUTO_ON_RESUME), activity.getString(R.string.text_burst_on_app_resume));
//        addText("Multiple sources will appear briefly whenever you open or resume the app.", true, 0, null);
        refreshState();
        return this.rootView;
    }

    @Override 
    public Drawable getIcon() {
        return this.activity.getResources().getDrawable(R.drawable.but_hand_swipe);
    }
}
