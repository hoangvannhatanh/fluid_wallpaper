package com.fozechmoblive.fluidwallpaper.livefluid.ui.component.wallpaper.tab;

import android.graphics.drawable.Drawable;
import android.view.View;

;
import com.fozechmoblive.fluidwallpaper.livefluid.R;
import com.fozechmoblive.fluidwallpaper.livefluid.ui.component.wallpaper.WallpaperActivity;
import com.magicfluids.Config;
import com.magicfluids.ConfigID;


public class TabFluid extends TabSet.TabPage {
    @Override 
    public String getName() {
        return activity.getString(R.string.text_fluid);
    }

    @Override 
    public  void askForColorPicker(Config.IntVal intVal) {
        super.askForColorPicker(intVal);
    }

    @Override 
    public  Drawable getIcon() {
        return super.getIcon();
    }

    @Override 
    public  boolean isCreated() {
        return super.isCreated();
    }

    @Override 
    public  void refreshState() {
        super.refreshState();
    }

    public TabFluid(Config config, WallpaperActivity wallpaperActivity) {
        super(config, wallpaperActivity);
    }

    @Override 
    public View createContent() {
        super.createContent();
        Section addNamedSectionSwitch = addNamedSectionSwitch(this.config.getBoolVal(ConfigID.PAINT_ENABLED), activity.getString(R.string.text_draw_with_paint));
        addNamedSeekBar(this.config.getFloatVal(ConfigID.FLUID_AMOUNT), activity.getString(R.string.text_amount), addNamedSectionSwitch);
        addNamedSeekBar(this.config.getFloatVal(ConfigID.FLUID_LIFE_TIME), activity.getString(R.string.text_lifetime), addNamedSectionSwitch);
        addSeparator();
        setFullOnly(true);
        Section addNamedSectionSwitch2 = addNamedSectionSwitch(this.config.getBoolVal(ConfigID.PARTICLES_ENABLED), activity.getString(R.string.text_draw_with_particles));
        addNamedSpinner(this.config.getIntVal(ConfigID.PARTICLES_MODE), activity.getString(R.string.text_mode), new String[]{activity.getString(R.string.text_add_on_touch), activity.getString(R.string.text_fill_screen)}, addNamedSectionSwitch2);
        addNamedSpinner(this.config.getIntVal(ConfigID.PARTICLES_SHAPE), activity.getString(R.string.text_shape), new String[]{activity.getString(R.string.text_dot), activity.getString(R.string.text_line), activity.getString(R.string.text_bubble), activity.getString(R.string.text_trail)}, addNamedSectionSwitch2);
        Section addSection = addSection(new TabSet.IntValue(this.config.getIntVal(ConfigID.PARTICLES_SHAPE), 3), addNamedSectionSwitch2);
        increaseMargin();
        addNamedSeekBar(this.config.getFloatVal(ConfigID.PARTICLES_TRAIL_LENGTH), activity.getString(R.string.text_trail_length), addSection);
        decreaseMargin();
        addNamedSeekBar(this.config.getFloatVal(ConfigID.PARTICLES_PER_SEC), activity.getString(R.string.text_amount), addNamedSectionSwitch2);
        addNamedSeekBar(this.config.getFloatVal(ConfigID.PARTICLES_LIFE_TIME_SEC), activity.getString(R.string.text_lifetime), addNamedSectionSwitch2);
        addNamedSeekBar(this.config.getFloatVal(ConfigID.PARTICLES_SIZE), activity.getString(R.string.text_size), addNamedSectionSwitch2);
        addNamedSeekBar(this.config.getFloatVal(ConfigID.PARTICLES_INTENSITY), activity.getString(R.string.text_intensity), addNamedSectionSwitch2);
        addNamedSeekBar(this.config.getFloatVal(ConfigID.PARTICLES_SMOOTHNESS), activity.getString(R.string.text_smoothness), addNamedSectionSwitch2);
        addNamedSpinner(this.config.getIntVal(ConfigID.PARTICLES_MIXING_MODE), activity.getString(R.string.text_color_mixing), new String[]{activity.getString(R.string.text_add_up), activity.getString(R.string.text_cover), activity.getString(R.string.text_cover_edge)}, addNamedSectionSwitch2);
        addText(activity.getString(R.string.text_how_colors_mix_when_particles_appear_on_top_of_paint_or_each_other), true, 0, addNamedSectionSwitch2);
        refreshState();
        return this.rootView;
    }

    @Override 
    public void notifyConfigValueChanged(int i) {
        if (!this.config.getBool(ConfigID.PAINT_ENABLED) && !this.config.getBool(ConfigID.PARTICLES_ENABLED)) {
            this.config.setBool(ConfigID.PAINT_ENABLED, true);
        }
        super.notifyConfigValueChanged(i);
    }
}
