package com.fozechmoblive.fluidwallpaper.livefluid.ui.component.wallpaper.tab;

import android.annotation.SuppressLint;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.SeekBar;
import android.widget.TabHost;
import android.widget.TextView;

import androidx.core.content.res.ResourcesCompat;
import androidx.viewpager.widget.ViewPager;

import com.google.android.gms.ads.RequestConfiguration;
;

import com.fozechmoblive.fluidwallpaper.livefluid.R;
import com.fozechmoblive.fluidwallpaper.livefluid.ui.component.wallpaper.WallpaperActivity;
import com.fozechmoblive.fluidwallpaper.livefluid.ui.component.wallpaper.viewpager.ViewPagerArrowIndicator;
import com.fozechmoblive.fluidwallpaper.livefluid.utils.ColorConstants;
import com.magicfluids.Config;

import java.util.ArrayList;
import java.util.Iterator;

import yuku.ambilwarna.AmbilWarnaDialog;


public class TabSet implements TabHost.OnTabChangeListener {
    ScrollView scrollView;
    TabHost tabHost;
    ArrayList<TabPage> tabs = new ArrayList<>();


    public interface BoolValueProvider {
        boolean getBoolValue();
    }

    @Override // android.widget.TabHost.OnTabChangeListener
    public void onTabChanged(String str) {
        Iterator<TabPage> it = this.tabs.iterator();
        while (it.hasNext()) {
            TabPage next = it.next();
            if (str.equals(next.getName()) && next.isCreated()) {
                next.refreshState();
            }
        }
//        ScrollView scrollView = this.scrollView;
//        if (scrollView != null) {
//            scrollView.scrollTo(0, 0);
//        }
    }


    static class BoolValue implements BoolValueProvider {
        private Config.BoolVal configBool;

        public BoolValue(Config.BoolVal boolVal) {
            this.configBool = boolVal;
        }

        @Override // com.magicfluids.GUI.TabSet.BoolValueProvider
        public boolean getBoolValue() {
            return this.configBool.Value;
        }
    }


    static class IntValue implements BoolValueProvider {
        private Config.IntVal configInt;
        private int truth;

        public IntValue(Config.IntVal intVal, int i) {
            this.configInt = intVal;
            this.truth = i;
        }

        @Override
        public boolean getBoolValue() {
            return this.configInt.Value == this.truth;
        }
    }


    public static abstract class TabPage {
        WallpaperActivity activity;
        protected Config.IntVal colorPickerCurrentValue;
        Config config;
        LinearLayout rootView;
        ArrayList<ConfigWidgets.Widget> configWidgets = new ArrayList<>();
        boolean contentCreated = false;
        private ArrayList<Section> sections = new ArrayList<>();
        protected int currentMargin = 0;
        protected boolean currentFullOnly = false;
        protected AmbilWarnaDialog dialogColorPicker = null;


        public enum SWITCH_STYLE {
            SWITCH_LEFT,
            SWITCH_RIGHT,
            CHECKBOX_LEFT,
            CHECKBOX_RIGHT
        }

        public Drawable getIcon() {
            return null;
        }

        public abstract String getName();

        public TabPage(Config config, WallpaperActivity wallpaperActivity) {
            this.config = config;
            this.activity = wallpaperActivity;
        }

        public boolean isCreated() {
            return this.contentCreated;
        }

        public View createContent() {
            LinearLayout linearLayout = new LinearLayout(this.activity);
            this.rootView = linearLayout;
            linearLayout.setLayoutParams(new LinearLayout.LayoutParams(-1, -1));
            this.rootView.setOrientation(LinearLayout.VERTICAL);
            this.contentCreated = true;
            return this.rootView;
        }

        public void refreshState() {
            Iterator<ConfigWidgets.Widget> it = this.configWidgets.iterator();
            while (it.hasNext()) {
                it.next().update();
            }
            updateVisibility();
        }

        public final void notifyConfigValueChanged() {
            notifyConfigValueChanged(0);
        }

        public void notifyConfigValueChanged(int i) {
            this.activity.onSettingsChanged(null);
            if (i == 0) {
                refreshState();
            }
        }


        public void updateVisibility() {
            Iterator<Section> it = this.sections.iterator();
            while (it.hasNext()) {
                it.next().updateVisibility();
            }
        }


        public void addSeparator() {
//            this.activity.getLayoutInflater().inflate(R.layout.line_separator, (ViewGroup) this.rootView, true);
        }


        public View addText(String str, boolean z, int i, Section section) {
            Typeface typeface = ResourcesCompat.getFont(rootView.getContext(), R.font.be_vietnam_pro_regular);
            TextView textView = new TextView(this.activity);
            textView.setText(str);
            textView.setPadding(8, 16, 8, 16);
            int currentTextSizeInDP = getCurrentTextSizeInDP();
            if (z) {
                increaseMargin();
                currentTextSizeInDP = getCurrentTextSizeInDP();
                decreaseMargin();
            }
            textView.setTextSize(1, currentTextSizeInDP);
            if (isDisabled()) {
                textView.setTextColor(ColorConstants.TEXT_COLOR_DISABLED);
            } else {
                textView.setTextColor(ColorConstants.TEXT_COLOR);
            }
            textView.setAllCaps(false);
            textView.setMaxLines(1);
            textView.setSingleLine(true);
            this.rootView.addView(textView);
            LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) textView.getLayoutParams();
            layoutParams.topMargin = i + 4;
            layoutParams.bottomMargin = 4;
            layoutParams.leftMargin = getCurrentMarginInDP();
            textView.setLayoutParams(layoutParams);
            if (section != null) {
                section.add(textView);
            }
            textView.setTypeface(typeface);
            return textView;
        }

        private View inflateNamedValLayout(int i, String str, Section section) {
            View inflate = this.activity.getLayoutInflater().inflate(i, this.rootView, false);
            TextView textView = inflate.findViewById(R.id.textView);
            textView.setPadding(8, 16, 8, 16);
            textView.setText(str);
            if (isDisabled()) {
                textView.setTextColor(ColorConstants.TEXT_COLOR_DISABLED);
            } else {
                textView.setTextColor(ColorConstants.TEXT_COLOR);
            }
            textView.setAllCaps(false);
            int currentMarginInDP = getCurrentMarginInDP();
            if (currentMarginInDP > 0) {
                LinearLayout linearLayout = new LinearLayout(this.activity);
                linearLayout.setLayoutParams(new LinearLayout.LayoutParams(0, -2, 0.5f));
                linearLayout.setOrientation(LinearLayout.HORIZONTAL);
                LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) textView.getLayoutParams();
                layoutParams.leftMargin = currentMarginInDP;
                textView.setLayoutParams(layoutParams);
                textView.setTextSize(1, getCurrentTextSizeInDP());
                ViewGroup viewGroup = (ViewGroup) textView.getParent();
                int indexOfChild = viewGroup.indexOfChild(textView);
                viewGroup.removeView(textView);
                linearLayout.addView(textView);
                viewGroup.addView(linearLayout, indexOfChild);
            }
            if (section != null) {
                section.add(inflate);
            }
            return inflate;
        }


        public View addNamedSeekBar(Config.FloatVal floatVal, String str) {
            return addNamedSeekBar(floatVal, str, null);
        }


        public View addNamedSeekBar(Config.FloatVal floatVal, String str, Section section) {
            View inflateNamedValLayout = inflateNamedValLayout(R.layout.config_seekbar, str, section);
            SeekBar seekBar = inflateNamedValLayout.findViewById(R.id.seekBar);
            if (isDisabled()) {
                seekBar.setEnabled(false);
            }
            this.configWidgets.add(new ConfigWidgets.ConfigSeekBar(seekBar, floatVal, this.activity, this));
            this.rootView.addView(inflateNamedValLayout);
            return inflateNamedValLayout;
        }


        public View addNamedIntSeekBar(Config.IntVal intVal, int i, String str, Section section) {
            View inflateNamedValLayout = inflateNamedValLayout(R.layout.config_seekbar, str, section);
            SeekBar seekBar = inflateNamedValLayout.findViewById(R.id.seekBar);
            if (isDisabled()) {
                seekBar.setEnabled(false);
            }
            this.configWidgets.add(new ConfigWidgets.ConfigSeekBarInt(seekBar, intVal, i, this.activity, this));
            this.rootView.addView(inflateNamedValLayout);
            return inflateNamedValLayout;
        }


        public View addNamedSpinner(Config.IntVal intVal, String str, String[] strArr) {
            return addNamedSpinner(intVal, str, strArr, null);
        }


        public View addNamedSpinner(Config.IntVal intVal, String str, String[] strArr, Section section) {
            View inflateNamedValLayout = inflateNamedValLayout(R.layout.config_spinner, str, section);
            ViewPagerArrowIndicator viewPagerArrowIndicator = inflateNamedValLayout.findViewById(R.id.spinner_indicator);

            ViewPager spinner = inflateNamedValLayout.findViewById(R.id.viewPager);
            if (isDisabled()) {
                spinner.setEnabled(false);
                spinner.setAlpha(0.4f);
            }
            this.configWidgets.add(new ConfigWidgets.ConfigSpinner(spinner, intVal, strArr, this.activity, this));
            viewPagerArrowIndicator.bind(spinner);

            this.rootView.addView(inflateNamedValLayout);
            return inflateNamedValLayout;
        }

        protected View addNamedSwitch(Config.BoolVal boolVal, String str, SWITCH_STYLE switch_style) {
            return addNamedSwitch(boolVal, str, switch_style, null);
        }

        protected View addNamedSwitch(Config.BoolVal boolVal, String str, SWITCH_STYLE switch_style, Section section) {
            int i = AnonymousClass2.$SwitchMap$com$magicfluids$GUI$TabSet$TabPage$SWITCH_STYLE[switch_style.ordinal()];
            int i2 = R.layout.config_switch;
            if (i == 1) {
                i2 = R.layout.config_section_switch;
            } else if (i != 2) {
                if (i == 3) {
                    i2 = R.layout.config_section_checkbox;
                } else if (i == 4) {
                    i2 = R.layout.config_checkbox;
                }
            }
            View inflateNamedValLayout = inflateNamedValLayout(i2, str, section);
            CompoundButton compoundButton = inflateNamedValLayout.findViewById(R.id.switchButton);
            if (isDisabled()) {
                compoundButton.setEnabled(false);
                compoundButton.setTextColor(ColorConstants.TEXT_COLOR_DISABLED);
            } else {
                compoundButton.setEnabled(true);
                compoundButton.setTextColor(ColorConstants.TEXT_COLOR);
            }
            compoundButton.setAllCaps(false);
            this.configWidgets.add(new ConfigWidgets.ConfigSwitch(compoundButton, boolVal, this.activity, this));
            this.rootView.addView(inflateNamedValLayout);
            return inflateNamedValLayout;
        }


        public Section addNamedSectionSwitch(Config.BoolVal boolVal, String str) {
            addNamedSwitch(boolVal, str, SWITCH_STYLE.SWITCH_RIGHT);
            return addSection(new BoolValue(boolVal), null);
        }


        public Section addNamedInnerSectionSwitch(Config.BoolVal boolVal, String str, Section section) {
            addNamedSwitch(boolVal, str, SWITCH_STYLE.SWITCH_RIGHT, section);
            return addSection(new BoolValue(boolVal), section);
        }


        public View addNamedRegularSwitch(Config.BoolVal boolVal, String str) {
            return addNamedRegularSwitch(boolVal, str, null);
        }


        public View addNamedRegularSwitch(Config.BoolVal boolVal, String str, Section section) {
            return addNamedSwitch(boolVal, str, SWITCH_STYLE.CHECKBOX_RIGHT, section);
        }


        public View addNamedColorButton(Config.IntVal intVal, String str, Section section) {
            View inflateNamedValLayout = inflateNamedValLayout(R.layout.config_color_button, str, section);
            initColorButton((Button) inflateNamedValLayout.findViewById(R.id.colorButton), intVal, RequestConfiguration.MAX_AD_CONTENT_RATING_UNSPECIFIED, null);
            this.rootView.addView(inflateNamedValLayout);
            return inflateNamedValLayout;
        }


        public View addColorButton(Config.IntVal intVal, String str, Section section) {
            Button button = (Button) this.activity.getLayoutInflater().inflate(R.layout.config_base_color_button, (ViewGroup) this.rootView, false);
            initColorButton(button, intVal, str, section);
            if (this.currentMargin > 0) {
                LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) button.getLayoutParams();
                layoutParams.leftMargin = getCurrentMarginInDP();
                button.setLayoutParams(layoutParams);
            }
            this.rootView.addView(button);
            return button;
        }


        public void initColorButton(Button button, Config.IntVal intVal, String str, Section section) {
            this.configWidgets.add(new ConfigWidgets.ConfigColorButton(button, intVal, this.activity, this));
            button.setText(str);
            if (this.currentMargin > 0) {
                button.setTextSize(1, getCurrentTextSizeInDP());
            }
            if (isDisabled()) {
                button.setEnabled(false);
            }
            if (section != null) {
                section.add(button);
            }
        }


        public void initSwitch(CompoundButton compoundButton, Config.BoolVal boolVal, Section section) {
            this.configWidgets.add(new ConfigWidgets.ConfigSwitch(compoundButton, boolVal, this.activity, this));
            if (isDisabled()) {
                compoundButton.setEnabled(false);
                compoundButton.setTextColor(ColorConstants.TEXT_COLOR_DISABLED);
            }
            compoundButton.setAllCaps(false);

            if (section != null) {
                section.add(compoundButton);
            }
        }


        public static class Section {
            private BoolValueProvider visibility;
            private ArrayList<View> views = new ArrayList<>();
            private ArrayList<Section> subsections = new ArrayList<>();
            private boolean parentVisible = true;

            Section(BoolValueProvider boolValueProvider) {
                this.visibility = boolValueProvider;
            }


            public void add(View view) {
                this.views.add(view);
            }

            void addSection(Section section) {
                this.subsections.add(section);
            }

            void setVisibilityFromParent(boolean z) {
                this.parentVisible = z;
            }

            void updateVisibility() {
                boolean z = this.visibility.getBoolValue() && this.parentVisible;
                Iterator<View> it = this.views.iterator();
                while (it.hasNext()) {
                    it.next().setVisibility(z ? View.VISIBLE : View.GONE);
                }
                Iterator<Section> it2 = this.subsections.iterator();
                while (it2.hasNext()) {
                    Section next = it2.next();
                    next.setVisibilityFromParent(z);
                    next.updateVisibility();
                }
            }
        }


        public Section addSection(BoolValueProvider boolValueProvider, Section section) {
            Section section2 = new Section(boolValueProvider);
            if (section != null) {
                section.addSection(section2);
            } else {
                this.sections.add(section2);
            }
            return section2;
        }


        public void increaseMargin() {
            this.currentMargin++;
        }


        public void decreaseMargin() {
            this.currentMargin--;
        }


        public int getCurrentMarginInDP() {
            return this.currentMargin * 14;
        }

        protected int getCurrentTextSizeInDP() {
            return 15 - (this.currentMargin * 2);
        }


        public void setFullOnly(boolean z) {
            this.currentFullOnly = z;
        }

        protected boolean isDisabled() {
            return false;
        }

        protected void initColorPicker() {
            this.dialogColorPicker = new AmbilWarnaDialog(this.activity, 0,R.style.AlertDialogCustom, new AmbilWarnaDialog.OnAmbilWarnaListener() {
                public void onCancel(AmbilWarnaDialog ambilWarnaDialog) {
                }

                @Override
                public void onOk(AmbilWarnaDialog ambilWarnaDialog, int i) {
                    TabPage.this.colorPickerCurrentValue.Value = i;
                    TabPage.this.notifyConfigValueChanged();
                }
            });
        }

        public void askForColorPicker(Config.IntVal intVal) {
//            if (this.dialogColorPicker == null) {
//                initColorPicker();
//            }
            this.colorPickerCurrentValue = intVal;
            this.dialogColorPicker = new AmbilWarnaDialog(this.activity, intVal.Value, true, R.style.AlertDialogCustom, new AmbilWarnaDialog.OnAmbilWarnaListener() {
                public void onCancel(AmbilWarnaDialog ambilWarnaDialog) {
                }

                @Override
                public void onOk(AmbilWarnaDialog ambilWarnaDialog, int i) {
                    TabPage.this.colorPickerCurrentValue.Value = i;
                    TabPage.this.notifyConfigValueChanged();
                }
            });
            this.dialogColorPicker.show();
        }
    }


    /* renamed from: com.magicfluids.GUI.TabSet$2  reason: invalid class name */

    public static /* synthetic */ class AnonymousClass2 {
        static final /* synthetic */ int[] $SwitchMap$com$magicfluids$GUI$TabSet$TabPage$SWITCH_STYLE;

        static {
            int[] iArr = new int[TabPage.SWITCH_STYLE.values().length];
            $SwitchMap$com$magicfluids$GUI$TabSet$TabPage$SWITCH_STYLE = iArr;
            try {
                iArr[TabPage.SWITCH_STYLE.SWITCH_LEFT.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$magicfluids$GUI$TabSet$TabPage$SWITCH_STYLE[TabPage.SWITCH_STYLE.SWITCH_RIGHT.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$com$magicfluids$GUI$TabSet$TabPage$SWITCH_STYLE[TabPage.SWITCH_STYLE.CHECKBOX_LEFT.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                $SwitchMap$com$magicfluids$GUI$TabSet$TabPage$SWITCH_STYLE[TabPage.SWITCH_STYLE.CHECKBOX_RIGHT.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
        }
    }

    public TabSet(TabHost tabHost) {
        this.scrollView = null;
        this.tabHost = tabHost;
        tabHost.setup();
        this.tabHost.setOnTabChangedListener(this);
        this.scrollView = (ScrollView) this.tabHost.findViewById(R.id.tabHostScroll);
    }

    public void addTab(final TabPage tabPage) {
        this.tabs.add(tabPage);
        Drawable icon = tabPage.getIcon();

        TabHost.TabSpec newTabSpec = this.tabHost.newTabSpec(tabPage.getName());
        if (icon == null) {
            newTabSpec.setIndicator(tabPage.getName());
        } else {
            newTabSpec.setIndicator(tabPage.getName(), icon);
        }
        newTabSpec.setContent(str -> tabPage.createContent());
        this.tabHost.addTab(newTabSpec);
    }

    public void finalizeSetup() {
        this.tabHost.getTabWidget().setLayoutParams(new LinearLayout.LayoutParams(-1, -2));
        Typeface typeface = ResourcesCompat.getFont(tabHost.getContext(), R.font.be_vietnam_pro_medium);
        for (int i = 0; i < this.tabs.size(); i++) {
            View childTabViewAt = this.tabHost.getTabWidget().getChildTabViewAt(i);
            childTabViewAt.setPadding(5, 5, 5, 5);
            @SuppressLint("ResourceType") TextView textView = childTabViewAt.findViewById(16908310);
            textView.setTextColor(ColorConstants.TEXT_COLOR);
            textView.setSingleLine();
            textView.setTypeface(typeface);
            textView.setTextSize(13.0f);
        }
    }

    public void refreshState() {
        Iterator<TabPage> it = this.tabs.iterator();
        while (it.hasNext()) {
            TabPage next = it.next();
            if (next.isCreated()) {
                next.refreshState();
            }
        }
    }
}
