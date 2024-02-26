package com.fozechmoblive.fluidwallpaper.livefluid.ui.component.wallpaper.tab;

import android.graphics.drawable.Drawable;
import android.view.View;

;
import com.fozechmoblive.fluidwallpaper.livefluid.R;
import com.fozechmoblive.fluidwallpaper.livefluid.ui.component.wallpaper.WallpaperActivity;
import com.magicfluids.Config;
import com.magicfluids.ConfigID;


public class TabEffects extends TabSet.TabPage {
    private View switchMirrorColoring;
    private View switchMultiColoring;

    @Override
    public String getName() {
        return "EFFECTS";
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

    public TabEffects(Config config, WallpaperActivity wallpaperActivity) {
        super(config, wallpaperActivity);
        this.switchMultiColoring = null;
        this.switchMirrorColoring = null;
    }

    @Override
    public View createContent() {
        super.createContent();
        addNamedSeekBar(this.config.getFloatVal(ConfigID.PE_EDGE_INTENSITY), activity.getString(R.string.text_intensity), addNamedSectionSwitch(this.config.getBoolVal(ConfigID.PE_EDGE), activity.getString(R.string.text_highlight_edges_details)));
        addSeparator();
        Section addNamedSectionSwitch = addNamedSectionSwitch(this.config.getBoolVal(ConfigID.PE_SCATTER), activity.getString(R.string.text_scattering));
        addNamedSeekBar(this.config.getFloatVal(ConfigID.PE_SCATTER_INTENSITY), activity.getString(R.string.text_intensity), addNamedSectionSwitch);
        addNamedSpinner(this.config.getIntVal(ConfigID.PE_SCATTER_TYPE), activity.getString(R.string.text_direction), new String[]{activity.getString(R.string.text_random), activity.getString(R.string.text_along_motion)}, addNamedSectionSwitch);
        addSeparator();
        setFullOnly(true);
        Section addNamedSectionSwitch2 = addNamedSectionSwitch(this.config.getBoolVal(ConfigID.PE_MULTIIMAGE), activity.getString(R.string.text_multi_image));
        addNamedSeekBar(this.config.getFloatVal(ConfigID.PE_MULTIIMAGE_INTENSITY), activity.getString(R.string.text_intensity), addNamedSectionSwitch2);
        this.switchMultiColoring = addNamedRegularSwitch(this.config.getBoolVal(ConfigID.PE_MULTIIMAGE_COLORING), activity.getString(R.string.text_mix_colors), addNamedSectionSwitch2);
        addSeparator();
        Section addNamedSectionSwitch3 = addNamedSectionSwitch(this.config.getBoolVal(ConfigID.PE_MIRROR), activity.getString(R.string.text_mirror));
        addNamedSpinner(this.config.getIntVal(ConfigID.PE_MIRROR_TYPE), activity.getString(R.string.text_pattern), new String[]{activity.getString(R.string.text_horizontal), activity.getString(R.string.text_vertical), activity.getString(R.string.text_diagonal), activity.getString(R.string.text_kaleidoscope)}, addNamedSectionSwitch3);
        this.switchMirrorColoring = addNamedRegularSwitch(this.config.getBoolVal(ConfigID.PE_MIRROR_COLORING), activity.getString(R.string.text_mix_colors), addNamedSectionSwitch3);
        addSeparator();
        Section addNamedSectionSwitch4 = addNamedSectionSwitch(this.config.getBoolVal(ConfigID.SHADING_ENABLED), activity.getString(R.string.text_3d_effect));
        addNamedSeekBar(this.config.getFloatVal(ConfigID.SHADING_BUMPINESS), activity.getString(R.string.text_intensity), addNamedSectionSwitch4);
        addNamedSeekBar(this.config.getFloatVal(ConfigID.SHADING_FLUID_BRIGHTNESS), activity.getString(R.string.text_color_brightness), addNamedSectionSwitch4);
        addNamedSeekBar(this.config.getFloatVal(ConfigID.SHADING_SPECULAR), activity.getString(R.string.text_brightness), addNamedSectionSwitch4);
        addNamedSeekBar(this.config.getFloatVal(ConfigID.SHADING_SPEC_POWER), activity.getString(R.string.text_blur_focus), addNamedSectionSwitch4);
        addNamedRegularSwitch(this.config.getBoolVal(ConfigID.SHADING_SPEC_ONLY_GLOW), activity.getString(R.string.text_only_reflections_glow), addNamedSectionSwitch4);
        addSeparator();
        Section addNamedSectionSwitch5 = addNamedSectionSwitch(this.config.getBoolVal(ConfigID.GLOW), activity.getString(R.string.text_glow_shadow));
        addNamedSeekBar(this.config.getFloatVal(ConfigID.GLOW_LEVEL_STRENGTH0), activity.getString(R.string.text_near), addNamedSectionSwitch5);
        addNamedSeekBar(this.config.getFloatVal(ConfigID.GLOW_LEVEL_STRENGTH1), activity.getString(R.string.text_mid), addNamedSectionSwitch5);
        addNamedSeekBar(this.config.getFloatVal(ConfigID.GLOW_LEVEL_STRENGTH2), activity.getString(R.string.text_far), addNamedSectionSwitch5);
        addNamedSeekBar(this.config.getFloatVal(ConfigID.GLOW_THRESHOLD), activity.getString(R.string.text_threshold), addNamedSectionSwitch5);
        Section addNamedInnerSectionSwitch = addNamedInnerSectionSwitch(this.config.getBoolVal(ConfigID.SHADOW_SOURCE), activity.getString(R.string.text_shadow), addNamedSectionSwitch5);
        increaseMargin();
        addNamedSeekBar(this.config.getFloatVal(ConfigID.SHADOW_INTENSITY), activity.getString(R.string.text_intensity), addNamedInnerSectionSwitch);
        addNamedSeekBar(this.config.getFloatVal(ConfigID.SHADOW_FALLOFF_LENGTH), activity.getString(R.string.text_length), addNamedInnerSectionSwitch);
        addNamedRegularSwitch(this.config.getBoolVal(ConfigID.SHADOW_SELF), activity.getString(R.string.text_self_shadow), addNamedInnerSectionSwitch);
        addNamedRegularSwitch(this.config.getBoolVal(ConfigID.SHADOW_INVERSE), activity.getString(R.string.text_inverse_shadows_rays), addNamedInnerSectionSwitch);
        decreaseMargin();
        Section addNamedInnerSectionSwitch2 = addNamedInnerSectionSwitch(this.config.getBoolVal(ConfigID.LIGHT_SOURCE), activity.getString(R.string.text_light), addNamedSectionSwitch5);
        increaseMargin();
        addNamedSeekBar(this.config.getFloatVal(ConfigID.LIGHT_INTENSITY), activity.getString(R.string.text_intensity), addNamedInnerSectionSwitch2);
        addNamedSeekBar(this.config.getFloatVal(ConfigID.LIGHT_RADIUS), activity.getString(R.string.text_size), addNamedInnerSectionSwitch2);
        addNamedColorButton(this.config.getIntVal(ConfigID.LIGHT_COLOR), activity.getString(R.string.text_light_color), addNamedInnerSectionSwitch2);
        decreaseMargin();
        addText(activity.getString(R.string.text_custom_light_position), false, 5, addNamedSectionSwitch5);
        addText(activity.getString(R.string.text_used_by_shadow), true, 2, addNamedSectionSwitch5);
        increaseMargin();
        addNamedSeekBar(this.config.getFloatVal(ConfigID.LIGHT_SOURCE_POSX), activity.getString(R.string.text_position_x), addNamedSectionSwitch5);
        addNamedSeekBar(this.config.getFloatVal(ConfigID.LIGHT_SOURCE_POSY), activity.getString(R.string.text_position_y), addNamedSectionSwitch5);
        addNamedSeekBar(this.config.getFloatVal(ConfigID.LIGHT_SOURCE_SPEED), activity.getString(R.string.text_speed), addNamedSectionSwitch5);
        decreaseMargin();
        addSeparator();
        Section addNamedSectionSwitch6 = addNamedSectionSwitch(this.config.getBoolVal(ConfigID.USE_DETAIL_TEXTURE), activity.getString(R.string.text_fluid_texture));
        addNamedSpinner(this.config.getIntVal(ConfigID.DETAIL_TEXTURE), activity.getString(R.string.text_texture), new String[]{activity.getString(R.string.text_water), activity.getString(R.string.text_fire), activity.getString(R.string.text_ice), activity.getString(R.string.text_cloud_1), activity.getString(R.string.text_cloud_2), activity.getString(R.string.text_veins), activity.getString(R.string.text_something_1), activity.getString(R.string.text_something_2), activity.getString(R.string.text_something_3), activity.getString(R.string.text_something_4)}, addNamedSectionSwitch6);
        addNamedSeekBar(this.config.getFloatVal(ConfigID.DETAIL_UV_SCALE), activity.getString(R.string.text_scale), addNamedSectionSwitch6);
        refreshState();
        return this.rootView;
    }


    @Override
    public void updateVisibility() {
        super.updateVisibility();
        if (this.config.getInt(ConfigID.COLOR_OPTION) >= 4) {
            this.switchMultiColoring.setVisibility(View.GONE);
            this.switchMirrorColoring.setVisibility(View.GONE);
        }
    }
}
