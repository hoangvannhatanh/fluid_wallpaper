package com.fozechmoblive.fluidwallpaper.livefluid.ui.component.wallpaper.tab;

import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;

;
import com.fozechmoblive.fluidwallpaper.livefluid.R;
import com.fozechmoblive.fluidwallpaper.livefluid.ui.component.wallpaper.CustomThemeSettingActivity;
import com.magicfluids.Config;
import com.magicfluids.ConfigID;


public class TabColors extends TabSet.TabPage {
    final int NUM_COLORS;
    final int NUM_COLORS_BACKGR;
    Button[] buttonColor;
    Button[] buttonColorBackgr;
    CheckBox[] checkColorActive;
    CheckBox[] checkColorBackgrActive;
    final ConfigID colorActiveBaseID;
    final ConfigID colorBackgrActiveBaseID;
    final ConfigID colorBackgrBaseID;
    final ConfigID colorBaseID;

    @Override
    public String getName() {
        return activity.getString(R.string.text_colors);
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

    public TabColors(Config config, CustomThemeSettingActivity wallpaperActivity) {
        super(config, wallpaperActivity);
        this.NUM_COLORS = 6;
        this.NUM_COLORS_BACKGR = 3;
        this.colorActiveBaseID = ConfigID.COLOR_ACTIVE0;
        this.colorBaseID = ConfigID.COLOR0;
        this.colorBackgrActiveBaseID = ConfigID.DCOLOR_ACTIVE0;
        this.colorBackgrBaseID = ConfigID.DCOLOR0;
        this.checkColorActive = new CheckBox[6];
        this.buttonColor = new Button[6];
        this.checkColorBackgrActive = new CheckBox[3];
        this.buttonColorBackgr = new Button[3];
    }

    @Override
    public View createContent() {
        super.createContent();
        setFullOnly(true);
        addNamedSpinner(this.config.getIntVal(ConfigID.COLOR_OPTION),
                activity.getString(R.string.text_color_mode), new String[]{activity.getString(R.string.text_random_colors),
                        activity.getString(R.string.text_color_palette), activity.getString(R.string.text_palette_with_background),
                        activity.getString(R.string.text_triply_colors), activity.getString(R.string.text_color_scale),
                        activity.getString(R.string.text_magic_palette)});
        setFullOnly(true);
        Section addSection = addSection(new TabSet.IntValue(this.config.getIntVal(ConfigID.COLOR_OPTION), 0), null);
        increaseMargin();
        addNamedSeekBar(this.config.getFloatVal(ConfigID.RANDOM_SATURATION), activity.getString(R.string.text_color_saturation), addSection);
        decreaseMargin();
        Section addSection2 = addSection(new TabSet.IntValue(this.config.getIntVal(ConfigID.COLOR_OPTION), 1), null);
        increaseMargin();
        addCheckedColorButtonGroup(this.checkColorActive, this.buttonColor, this.colorActiveBaseID, this.colorBaseID, addSection2);
        decreaseMargin();
        Section addSection3 = addSection(new TabSet.IntValue(this.config.getIntVal(ConfigID.COLOR_OPTION), 2), null);
        increaseMargin();
        addCheckedColorButtonGroup(this.checkColorBackgrActive, this.buttonColorBackgr, this.colorBackgrActiveBaseID, this.colorBackgrBaseID, addSection3);
        addColorButton(this.config.getIntVal(ConfigID.BACKGROUND_COLOR), activity.getString(R.string.text_background_color), addSection3);
        decreaseMargin();
        Section addSection4 = addSection(new TabSet.IntValue(this.config.getIntVal(ConfigID.COLOR_OPTION), 4), null);
        increaseMargin();
        addColorScaleButtons(addSection4);
        addNamedRegularSwitch(this.config.getBoolVal(ConfigID.CARTOON_COLORS), activity.getString(R.string.text_cartoon_colors), addSection4);
        decreaseMargin();
        Section addSection5 = addSection(new TabSet.IntValue(this.config.getIntVal(ConfigID.COLOR_OPTION), 5), null);
        increaseMargin();
        addNamedSeekBar(this.config.getFloatVal(ConfigID.MAGIC_PALETTE_G), activity.getString(R.string.text_magic_1), addSection5);
        addNamedSeekBar(this.config.getFloatVal(ConfigID.MAGIC_PALETTE_B), activity.getString(R.string.text_magic_2), addSection5);
        addNamedSeekBar(this.config.getFloatVal(ConfigID.MAGIC_PALETTE_BASE_SHIFT), activity.getString(R.string.text_color_shift), addSection5);
        addNamedSeekBar(this.config.getFloatVal(ConfigID.MAGIC_PALETTE_BASE_SHIFT_SPEED), activity.getString(R.string.text_shift_speed), addSection5);
        addNamedRegularSwitch(this.config.getBoolVal(ConfigID.MAGIC_PALETTE_BLACK_BACKGR), activity.getString(R.string.text_black_background), addSection5);
        decreaseMargin();
        setFullOnly(false);
        addSeparator();
        final Config.IntVal intVal = this.config.getIntVal(ConfigID.COLOR_OPTION);
        addNamedSpinner(this.config.getIntVal(ConfigID.COLOR_CHANGE), activity.getString(R.string.text_color_change), new String[]{activity.getString(R.string.text_on_click), activity.getString(R.string.text_continuously)}, addSection(() -> intVal.Value != 5, null));
        addNamedRegularSwitch(this.config.getBoolVal(ConfigID.INVERT_COLORS), activity.getString(R.string.text_invert_colors));
        addNamedRegularSwitch(this.config.getBoolVal(ConfigID.OVERBRIGHT_COLORS), "Overbright colors", addSection(() -> intVal.Value <= 1, null));
        refreshState();
        return this.rootView;
    }

    private void addCheckedColorButtonGroup(CheckBox[] checkBoxArr, Button[] buttonArr, ConfigID configID, ConfigID configID2, Section section) {
        int i = 0;
        while (i < checkBoxArr.length) {
            View inflate = this.activity.getLayoutInflater().inflate(R.layout.config_checked_color_button, this.rootView, false);
            checkBoxArr[i] = inflate.findViewById(R.id.checkColorButton);
            buttonArr[i] = inflate.findViewById(R.id.colorButton);
            if (getCurrentMarginInDP() > 0) {
                LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) checkBoxArr[i].getLayoutParams();
                layoutParams.leftMargin = getCurrentMarginInDP();
                checkBoxArr[i].setLayoutParams(layoutParams);
            }
            this.rootView.addView(inflate);
            initSwitch(checkBoxArr[i], this.config.getBoolVal(ConfigID.values[configID.ordinal() + i]), null);
            Button button = buttonArr[i];
            Config.IntVal intVal = this.config.getIntVal(ConfigID.values[configID2.ordinal() + i]);
            StringBuilder sb = new StringBuilder();
            sb.append("Color ");
            i++;
            sb.append(i);
            initColorButton(button, intVal, sb.toString(), null);
            if (section != null) {
                section.add(inflate);
            }
        }
    }

    private void refreshCheckedColorButtonGroup(CheckBox[] checkBoxArr, Button[] buttonArr, ConfigID configID, ConfigID configID2) {
        for (int i = 0; i < checkBoxArr.length; i++) {
            this.config.getBool(configID.ordinal() + i);
        }
        for (int i2 = 0; i2 < checkBoxArr.length; i2++) {
            boolean bool = this.config.getBool(configID.ordinal() + i2);
            checkBoxArr[i2].setEnabled(false);
            buttonArr[i2].setVisibility(bool ? View.VISIBLE : View.INVISIBLE);
        }
    }

    private void addColorScaleButtons(Section section) {
        Section addNamedInnerSectionSwitch = addNamedInnerSectionSwitch(this.config.getBoolVal(ConfigID.MULTI_COLOR_DOUBLE), activity.getString(R.string.text_double_palette), section);
        View inflate = this.activity.getLayoutInflater().inflate(R.layout.layout_color_scale, this.rootView, false);
        addNamedInnerSectionSwitch.add(inflate.findViewById(R.id.colorScaleSecondPalette));
        if (getCurrentMarginInDP() > 0) {
            LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) inflate.getLayoutParams();
            layoutParams.leftMargin = getCurrentMarginInDP();
            inflate.setLayoutParams(layoutParams);
        }
        initColorButton(inflate.findViewById(R.id.colorScaleButtonBackgr), this.config.getIntVal(ConfigID.MULTI_COLOR0), activity.getString(R.string.text_background_color), null);
        initColorButton(inflate.findViewById(R.id.colorScaleButton00), this.config.getIntVal(ConfigID.MULTI_COLOR1), activity.getString(R.string.text_1_low), null);
        initColorButton(inflate.findViewById(R.id.colorScaleButton01), this.config.getIntVal(ConfigID.MULTI_COLOR2), activity.getString(R.string.text_1_med), null);
        initColorButton(inflate.findViewById(R.id.colorScaleButton02), this.config.getIntVal(ConfigID.MULTI_COLOR3), activity.getString(R.string.text_1_high), null);
        initColorButton(inflate.findViewById(R.id.colorScaleButton03), this.config.getIntVal(ConfigID.MULTI_COLOR4), activity.getString(R.string.text_1_full), null);
        initColorButton(inflate.findViewById(R.id.colorScaleButton10), this.config.getIntVal(ConfigID.MULTI_COLOR5), activity.getString(R.string.text_2_low), null);
        initColorButton(inflate.findViewById(R.id.colorScaleButton11), this.config.getIntVal(ConfigID.MULTI_COLOR6), activity.getString(R.string.text_2_med), null);
        initColorButton(inflate.findViewById(R.id.colorScaleButton12), this.config.getIntVal(ConfigID.MULTI_COLOR7), activity.getString(R.string.text_2_high), null);
        initColorButton(inflate.findViewById(R.id.colorScaleButton13), this.config.getIntVal(ConfigID.MULTI_COLOR8), activity.getString(R.string.text_2_full), null);
        this.rootView.addView(inflate);
        if (section != null) {
            section.add(inflate);
        }
    }

    @Override
    public void refreshState() {
        super.refreshState();
        refreshCheckedColorButtonGroup(this.checkColorActive, this.buttonColor, this.colorActiveBaseID, this.colorBaseID);
        refreshCheckedColorButtonGroup(this.checkColorBackgrActive, this.buttonColorBackgr, this.colorBackgrActiveBaseID, this.colorBackgrBaseID);
    }
}
