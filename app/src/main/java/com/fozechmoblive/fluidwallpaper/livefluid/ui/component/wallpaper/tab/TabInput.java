package com.fozechmoblive.fluidwallpaper.livefluid.ui.component.wallpaper.tab;

import android.graphics.drawable.Drawable;
import android.view.View;
import android.view.ViewGroup;

import com.fozechmoblive.fluidwallpaper.livefluid.R;
import com.magicfluids.Config;
;
import com.fozechmoblive.fluidwallpaper.livefluid.ui.component.wallpaper.WallpaperActivity;



public class TabInput extends TabSet.TabPage {
    TabSet inputTabWidget;

    @Override 
    public String getName() {
        return "INPUT";
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
    public  void notifyConfigValueChanged(int i) {
        super.notifyConfigValueChanged(i);
    }

    public TabInput(Config config, WallpaperActivity wallpaperActivity) {
        super(config, wallpaperActivity);
    }

    @Override 
    public View createContent() {
        super.createContent();
        View inflate = this.activity.getLayoutInflater().inflate(R.layout.tab_input_tabhost, (ViewGroup) this.rootView, false);
        TabSet tabSet = new TabSet(inflate.findViewById(R.id.tabHostInput2));
        this.inputTabWidget = tabSet;
        tabSet.addTab(new TabInputSwipe(this.config, this.activity));
        this.inputTabWidget.addTab(new TabInputHold(this.config, this.activity));
        this.inputTabWidget.finalizeSetup();
        this.rootView.addView(inflate);
        refreshState();
        return this.rootView;
    }

    @Override 
    public void refreshState() {
        super.refreshState();
        this.inputTabWidget.refreshState();
    }
}
