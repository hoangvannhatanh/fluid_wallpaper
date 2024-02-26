package com.fozechmoblive.fluidwallpaper.livefluid.ui.component.wallpaper.tab;

import android.graphics.drawable.Drawable;
import android.view.View;

;
import com.fozechmoblive.fluidwallpaper.livefluid.R;
import com.fozechmoblive.fluidwallpaper.livefluid.ui.component.wallpaper.WallpaperActivity;
import com.magicfluids.Config;
import com.magicfluids.ConfigID;


public class TabSimulation extends TabSet.TabPage {
    @Override
    public String getName() {
        return activity.getString(R.string.text_sim);
    }

    @Override
    public void askForColorPicker(Config.IntVal intVal) {
        super.askForColorPicker(intVal);
    }

    @Override
    public Drawable getIcon() {
        return super.getIcon();
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

    public TabSimulation(Config config, WallpaperActivity wallpaperActivity) {
        super(config, wallpaperActivity);
    }

    @Override
    public View createContent() {
        super.createContent();
        addNamedSpinner(this.config.getIntVal(ConfigID.GPU_ANIMATION), activity.getString(R.string.text_image_quality), new String[]{activity.getString(R.string.text_lowest), activity.getString(R.string.text_low), activity.getString(R.string.text_medium), activity.getString(R.string.text_high), activity.getString(R.string.text_very_high), activity.getString(R.string.text_best)});
        addNamedSpinner(this.config.getIntVal(ConfigID.QUALITY_BASE_VALUE), activity.getString(R.string.text_simulation_resolution), new String[]{activity.getString(R.string.text_low), activity.getString(R.string.text_medium), activity.getString(R.string.text_high)});
        addNamedSpinner(this.config.getIntVal(ConfigID.EFFECTS_QUALITY), activity.getString(R.string.text_glow_shadow_quality), new String[]{activity.getString(R.string.text_low), activity.getString(R.string.text_medium), activity.getString(R.string.text_high), activity.getString(R.string.text_best)});
        addSeparator();
        setFullOnly(true);
        addNamedSpinner(this.config.getIntVal(ConfigID.FLUID_TYPE), activity.getString(R.string.text_fluid_type), new String[]{activity.getString(R.string.text_smoke), activity.getString(R.string.text_water), activity.getString(R.string.text_jello)});
        addNamedSeekBar(this.config.getFloatVal(ConfigID.SIM_SPEED), activity.getString(R.string.text_simulation_speed));
        addNamedSeekBar(this.config.getFloatVal(ConfigID.VEL_LIFE_TIME), activity.getString(R.string.text_energy));
        addNamedSeekBar(this.config.getFloatVal(ConfigID.SWIRLINESS), activity.getString(R.string.text_swirliness));
        addNamedSeekBar(this.config.getFloatVal(ConfigID.VEL_NOISE), activity.getString(R.string.text_random_motion));
        addNamedSeekBar(this.config.getFloatVal(ConfigID.GRAVITY), activity.getString(R.string.text_gravity));
        addNamedSpinner(this.config.getIntVal(ConfigID.BORDER_MODE), activity.getString(R.string.text_border_mode), new String[]{activity.getString(R.string.text_wall), activity.getString(R.string.text_wrap), activity.getString(R.string.text_wrap_and_mirror)});
        refreshState();
        return this.rootView;
    }
}
