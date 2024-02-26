package com.fozechmoblive.fluidwallpaper.livefluid.ui.component.wallpaper.fluids;

import android.graphics.Color;

import androidx.core.view.ViewCompat;

import java.util.ArrayList;


public class ColorPalette {
    static ArrayList<ColorPalette> Palettes;
    int[] Colors;
    String Name;
    int NumColors;

    ColorPalette(int i, int i2, int i3, int i4, int i5, String str) {
        this.Colors = new int[]{Color.TRANSPARENT};
        this.NumColors = i;
        int[] iArr = {i2 | ViewCompat.MEASURED_STATE_MASK, i3 | ViewCompat.MEASURED_STATE_MASK, i4 | ViewCompat.MEASURED_STATE_MASK, (-16777216) | i5};
        this.Name = str;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static void init() {
        ArrayList<ColorPalette> arrayList = new ArrayList<>();
        Palettes = arrayList;
        arrayList.add(new ColorPalette(2, 16724736, 22015, ViewCompat.MEASURED_SIZE_MASK, ViewCompat.MEASURED_SIZE_MASK, "Hot and cold"));
        Palettes.add(new ColorPalette(3, 14496546, 2276351, 12320614, ViewCompat.MEASURED_SIZE_MASK, "RGB"));
        Palettes.add(new ColorPalette(3, 5575031, 14527231, 16716151, ViewCompat.MEASURED_SIZE_MASK, "Purple sunset"));
        Palettes.add(new ColorPalette(3, 14048017, 1149081, 16776836, ViewCompat.MEASURED_SIZE_MASK, "Salon"));
        Palettes.add(new ColorPalette(2, 16610627, 1930868, ViewCompat.MEASURED_SIZE_MASK, ViewCompat.MEASURED_SIZE_MASK, "Orange and deep sea"));
        Palettes.add(new ColorPalette(2, 12346998, 16244659, ViewCompat.MEASURED_SIZE_MASK, ViewCompat.MEASURED_SIZE_MASK, "Pink and grey"));
    }
}
