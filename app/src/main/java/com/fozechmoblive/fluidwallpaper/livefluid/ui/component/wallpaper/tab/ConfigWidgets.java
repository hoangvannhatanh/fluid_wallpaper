package com.fozechmoblive.fluidwallpaper.livefluid.ui.component.wallpaper.tab;

import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.SeekBar;

import androidx.core.view.ViewCompat;
import androidx.viewpager.widget.ViewPager;

import com.fozechmoblive.fluidwallpaper.livefluid.ui.component.wallpaper.WallpaperActivity;
import com.fozechmoblive.fluidwallpaper.livefluid.ui.component.wallpaper.viewpager.CustomPagerAdapter;
import com.magicfluids.Config;


public class ConfigWidgets {


    public static abstract class Widget {
        TabSet.TabPage parentTab;


        public abstract void update();

        Widget(TabSet.TabPage tabPage) {
            this.parentTab = tabPage;
        }
    }


    public static class ConfigSeekBar extends Widget {
        Config.FloatVal configValue;
        SeekBar seekBarView;


        public ConfigSeekBar(SeekBar seekBar, final Config.FloatVal floatVal, WallpaperActivity wallpaperActivity, TabSet.TabPage tabPage) {
            super(tabPage);
            this.configValue = floatVal;
            this.seekBarView = seekBar;
            seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() { // from class: com.magicfluids.GUI.ConfigWidgets.ConfigSeekBar.1
                @Override // android.widget.SeekBar.OnSeekBarChangeListener
                public void onStartTrackingTouch(SeekBar seekBar2) {
                }

                @Override // android.widget.SeekBar.OnSeekBarChangeListener
                public void onStopTrackingTouch(SeekBar seekBar2) {
                }

                @Override // android.widget.SeekBar.OnSeekBarChangeListener
                public void onProgressChanged(SeekBar seekBar2, int i, boolean z) {
                    floatVal.setPercent(i);
                    ConfigSeekBar.this.parentTab.notifyConfigValueChanged(1);
                }
            });
        }

        @Override
        public void update() {
            this.seekBarView.setProgress(this.configValue.getPercent());
        }
    }


    public static class ConfigSeekBarInt extends Widget {
        Config.IntVal configValue;
        SeekBar seekBarView;


        public ConfigSeekBarInt(SeekBar seekBar, final Config.IntVal intVal, int i, WallpaperActivity wallpaperActivity, TabSet.TabPage tabPage) {
            super(tabPage);
            this.configValue = intVal;
            this.seekBarView = seekBar;
            seekBar.setMax(i);
            this.seekBarView.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() { // from class: com.magicfluids.GUI.ConfigWidgets.ConfigSeekBarInt.1
                @Override // android.widget.SeekBar.OnSeekBarChangeListener
                public void onStartTrackingTouch(SeekBar seekBar2) {
                }

                @Override // android.widget.SeekBar.OnSeekBarChangeListener
                public void onStopTrackingTouch(SeekBar seekBar2) {
                }

                @Override // android.widget.SeekBar.OnSeekBarChangeListener
                public void onProgressChanged(SeekBar seekBar2, int i2, boolean z) {
                    intVal.Value = i2;
                    ConfigSeekBarInt.this.parentTab.notifyConfigValueChanged(1);
                }
            });
        }

        @Override
        public void update() {
            this.seekBarView.setProgress(this.configValue.Value);
        }
    }

    static void updateButtonColor(Button button, int i) {
        button.setBackgroundColor(i);
        if (((16711680 & i) >> 16) + ((65280 & i) >> 8) + (i & 255) < 200) {
            button.setTextColor(-1);
        } else {
            button.setTextColor(ViewCompat.MEASURED_STATE_MASK);
        }
    }


    public static class ConfigColorButton extends Widget {
        Button buttonView;
        Config.IntVal configValue;


        public ConfigColorButton(Button button, Config.IntVal intVal, WallpaperActivity wallpaperActivity, final TabSet.TabPage tabPage) {
            super(tabPage);
            this.configValue = intVal;
            this.buttonView = button;
            button.setOnClickListener(view -> tabPage.askForColorPicker(ConfigColorButton.this.configValue));
        }

        @Override
        public void update() {
            ConfigWidgets.updateButtonColor(this.buttonView, this.configValue.Value);
        }
    }


    public static class ConfigSpinner extends Widget {
        Config.IntVal configValue;
        ViewPager spinnerView;


        public ConfigSpinner(ViewPager spinner, final Config.IntVal intVal, String[] strArr, WallpaperActivity wallpaperActivity, TabSet.TabPage tabPage) {
            super(tabPage);
            this.configValue = intVal;
            this.spinnerView = spinner;

//            ArrayAdapter arrayAdapter = new ArrayAdapter(presetActivity, R.layout.spinner_item);
//            arrayAdapter.setDropDownViewResource(R.layout.spinner_dropdown_item);
//            for (String str : strArr) {
//                arrayAdapter.add(str);
//            }
//            spinner.setAdapter(new );


            this.spinnerView.setAdapter(new CustomPagerAdapter(spinner.getContext(), strArr));

            this.spinnerView.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                @Override
                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {


                }

                @Override
                public void onPageSelected(int position) {
                    intVal.Value = position;
                    ConfigSpinner.this.parentTab.notifyConfigValueChanged();
                }

                @Override
                public void onPageScrollStateChanged(int state) {

                }
            });
//            this.spinnerView.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//                @Override // android.widget.AdapterView.OnItemSelectedListener
//                public void onNothingSelected(AdapterView<?> adapterView) {
//                }
//
//                @Override // android.widget.AdapterView.OnItemSelectedListener
//                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long j) {
//                    intVal.Value = (int) j;
//                    ConfigSpinner.this.parentTab.notifyConfigValueChanged();
//                }
//            });
        }

        @Override
        public void update() {
            if (this.configValue.Value < 0 || this.configValue.Value >= this.spinnerView.getAdapter().getCount()) {
                return;
            }
            this.spinnerView.setCurrentItem(this.configValue.Value,true);
        }
    }


    public static class ConfigSwitch extends Widget {
        Config.BoolVal configValue;
        CompoundButton switchView;


        public ConfigSwitch(CompoundButton compoundButton, final Config.BoolVal boolVal, WallpaperActivity wallpaperActivity, TabSet.TabPage tabPage) {
            super(tabPage);
            this.configValue = boolVal;
            this.switchView = compoundButton;
            compoundButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() { // from class: com.magicfluids.GUI.ConfigWidgets.ConfigSwitch.1
                @Override // android.widget.CompoundButton.OnCheckedChangeListener
                public void onCheckedChanged(CompoundButton compoundButton2, boolean z) {
                    boolVal.Value = z;
                    ConfigSwitch.this.parentTab.notifyConfigValueChanged();
                }
            });
        }

        @Override
        public void update() {
            this.switchView.setChecked(this.configValue.Value);
        }
    }
}
