package com.fozechmoblive.fluidwallpaper.livefluid.ui.component.wallpaper.fluids;

;
import com.fozechmoblive.fluidwallpaper.livefluid.R;
import com.fozechmoblive.fluidwallpaper.livefluid.ui.component.wallpaper.CustomThemeSettingActivity;
import com.fozechmoblive.fluidwallpaper.livefluid.ui.component.wallpaper.tab.TabColors;
import com.fozechmoblive.fluidwallpaper.livefluid.ui.component.wallpaper.tab.TabEffects;
import com.fozechmoblive.fluidwallpaper.livefluid.ui.component.wallpaper.tab.TabFluid;
import com.fozechmoblive.fluidwallpaper.livefluid.ui.component.wallpaper.tab.TabInput;
import com.fozechmoblive.fluidwallpaper.livefluid.ui.component.wallpaper.tab.TabSet;
import com.fozechmoblive.fluidwallpaper.livefluid.ui.component.wallpaper.tab.TabSimulation;
import com.magicfluids.Config;


public class SettingsController {
    private TabSet mainTabWidget;

    public void initControls(CustomThemeSettingActivity wallpaperActivity, Config config) {
        ColorPalette.init();
        if (mainTabWidget == null) {
            this.mainTabWidget = new TabSet(wallpaperActivity.findViewById(R.id.tabHost));
            this.mainTabWidget.addTab(new TabInput(config, wallpaperActivity));
            this.mainTabWidget.addTab(new TabSimulation(config, wallpaperActivity));
            this.mainTabWidget.addTab(new TabColors(config, wallpaperActivity));
            this.mainTabWidget.addTab(new TabFluid(config, wallpaperActivity));
            this.mainTabWidget.addTab(new TabEffects(config, wallpaperActivity));
            this.mainTabWidget.finalizeSetup();
        }
    }

    public void reloadEverything() {
        this.mainTabWidget.refreshState();
    }
}
