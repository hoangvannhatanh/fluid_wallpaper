package com.magicfluids;

import android.util.Log;

import androidx.core.internal.view.SupportMenu;
import androidx.core.view.ViewCompat;

import org.jetbrains.annotations.Nullable;

import java.util.EnumMap;
import java.util.Map;


public class Config {
    static final int COLOR_DOUBLE_PALETTE = 2;
    static final int COLOR_MULTI = 4;
    static final int COLOR_PALETTE = 1;
    static final int COLOR_RANDOM = 0;
    static final int COLOR_SCALE = 5;
    static final int COLOR_TRIPPY = 3;
    static final float GRAVITY_ENABLE_THRESHOLD = 3.0E-4f;
    static final int MENU_BUTTON_DIMMED = 1;
    static final int MENU_BUTTON_HIDDEN = 2;
    static final int MENU_BUTTON_VISIBLE = 0;
    public static Config Current = new Config();
    public static Config LWPCurrent = new Config();
    public boolean ReloadRequired = false;
    public boolean ReloadRequiredPreview = false;
    private Map<ConfigID, ConfigVal> configMap = new EnumMap(ConfigID.class);
    private float[] floatArray = new float[ConfigID.values().length];
    private int[] intArray = new int[ConfigID.values().length];
    private boolean[] boolArray = new boolean[ConfigID.values().length];

    
    public static abstract class ConfigVal {
        public boolean IsPresetRelated;
        public DataType Type;

        
        
        public enum DataType {
            FLOAT,
            INT,
            BOOL
        }

        abstract void copyValueFrom(ConfigVal configVal);

        ConfigVal(boolean z, DataType dataType) {
            this.IsPresetRelated = z;
            this.Type = dataType;
        }
    }

    
    public static class FloatVal extends ConfigVal {
        public  float Default;
        public float Max;
        public float Min;
        public float Value;

        FloatVal(float f, float f2, float f3, boolean z) {
            super(z, DataType.FLOAT);
            this.Default = f;
            this.Value = f;
            this.Min = f2;
            this.Max = f3;
        }

        public int getPercent() {
            float f = this.Value;
            float f2 = this.Min;
            return (int) (((f - f2) * 100.0f) / (this.Max - f2));
        }

        public void setPercent(int i) {
            float f = this.Min;
            this.Value = f + ((i / 100.0f) * (this.Max - f));
        }

        @Override // com.magicfluids.Config.ConfigVal
        void copyValueFrom(ConfigVal configVal) {
            this.Value = ((FloatVal) configVal).Value;
        }
    }

    
    public static class IntVal extends ConfigVal {
        public int Default;
        public boolean IsColor;
        public int Value;

        public IntVal(int i, boolean z, boolean z2) {
            super(z, DataType.INT);
            this.Default = i;
            this.Value = i;
            this.IsColor = z2;
        }

        @Override // com.magicfluids.Config.ConfigVal
        void copyValueFrom(ConfigVal configVal) {
            this.Value = ((IntVal) configVal).Value;
        }
    }

    
    public static class BoolVal extends ConfigVal {
        public boolean Default;
        public boolean Value;

        BoolVal(boolean z, boolean z2) {
            super(z2, DataType.BOOL);
            this.Default = z;
            this.Value = z;
        }

        @Override // com.magicfluids.Config.ConfigVal
        void copyValueFrom(ConfigVal configVal) {
            this.Value = ((BoolVal) configVal).Value;
        }
    }

    private void addFloat(ConfigID configID, float f, float f2, float f3) {
        this.configMap.put(configID, new FloatVal(f, f2, f3, true));
    }

    private void addInt(ConfigID configID, int i) {
        this.configMap.put(configID, new IntVal(i, true, false));
    }

    private void addIntColor(ConfigID configID, int i) {
        this.configMap.put(configID, new IntVal(i, true, true));
    }

    private void addIntNonPreset(ConfigID configID, int i) {
        this.configMap.put(configID, new IntVal(i, false, false));
    }

    private void addBool(ConfigID configID, boolean z) {
        this.configMap.put(configID, new BoolVal(z, true));
    }

    private void addBoolNonPreset(ConfigID configID, boolean z) {
        this.configMap.put(configID, new BoolVal(z, false));
    }

    public Map<ConfigID, ConfigVal> getMap() {
        return this.configMap;
    }

    public void copyValuesFrom(Config config) {
        for (Map.Entry<ConfigID, ConfigVal> entry : this.configMap.entrySet()) {
            ConfigVal findVal = config.findVal(entry.getKey());
            if (findVal != null) {
                entry.getValue().copyValueFrom(findVal);
            }
        }
    }

    private ConfigVal findVal(ConfigID configID) {
        if (this.configMap.containsKey(configID)) {
            return this.configMap.get(configID);
        }
        Log.e("Config", "Config.java:findVal(): Config value not found: " + configID);
        return null;
    }

    private ConfigVal findValOfType(ConfigID configID, ConfigVal.DataType dataType) {
        ConfigVal findVal = findVal(configID);
        if (findVal != null) {
            if (findVal.Type == dataType) {
                return findVal;
            }
            Log.e("Config", "Config.java:getFloatVal(): Config value: " + configID + " is not of requested type: " + dataType);
        }
        return null;
    }

    public FloatVal getFloatVal(ConfigID configID) {
        return (FloatVal) findValOfType(configID, ConfigVal.DataType.FLOAT);
    }

    public float getFloat(ConfigID configID) {
        FloatVal floatVal = getFloatVal(configID);
        if (floatVal == null) {
            return 0.0f;
        }
        return floatVal.Value;
    }

    public void setFloat(ConfigID configID, float f) {
        FloatVal floatVal = getFloatVal(configID);
        if (floatVal != null) {
            floatVal.Value = f;
        }
    }

    public float getFloat(int i) {
        return getFloat(ConfigID.values()[i]);
    }

    public void setFloat(int i, float f) {
        setFloat(ConfigID.values()[i], f);
    }

    public IntVal getIntVal(ConfigID configID) {
        return (IntVal) findValOfType(configID, ConfigVal.DataType.INT);
    }

    public int getInt(ConfigID configID) {
        IntVal intVal = getIntVal(configID);
        if (intVal == null) {
            return 0;
        }
        return intVal.Value;
    }

    public void setInt(ConfigID configID, int i) {
        IntVal intVal = getIntVal(configID);
        if (intVal != null) {
            intVal.Value = i;
            if (intVal.IsColor) {
                intVal.Value |= ViewCompat.MEASURED_STATE_MASK;
            }
        }
    }

    public int getInt(int i) {
        return getInt(ConfigID.values()[i]);
    }

    public void setInt(int i, int i2) {
        setInt(ConfigID.values()[i], i2);
    }

    public BoolVal getBoolVal(ConfigID configID) {
        return (BoolVal) findValOfType(configID, ConfigVal.DataType.BOOL);
    }

    public boolean getBool(ConfigID configID) {
        BoolVal boolVal = getBoolVal(configID);
        if (boolVal == null) {
            return false;
        }
        return boolVal.Value;
    }

    public void setBool(ConfigID configID, boolean z) {
        BoolVal boolVal = getBoolVal(configID);
        if (boolVal != null) {
            boolVal.Value = z;
        }
    }

    public boolean getBool(int i) {
        return getBool(ConfigID.values()[i]);
    }

    public void setBool(int i, boolean z) {
        setBool(ConfigID.values()[i], z);
    }

    public Config() {
        addIntNonPreset(ConfigID.GPU_ANIMATION, 3);
        addIntNonPreset(ConfigID.QUALITY_BASE_VALUE, 1);
        addIntNonPreset(ConfigID.EFFECTS_QUALITY, 2);
        addIntNonPreset(ConfigID.MENU_BUTTON_VISIBILITY, 0);
        addBoolNonPreset(ConfigID.MUSIC_ENABLED, true);
        addIntNonPreset(ConfigID.FPS_LIMIT, 0);
        addBoolNonPreset(ConfigID.ALLOW_MULTITHREADING, false);
        addBoolNonPreset(ConfigID.RANDOMIZE_ON_RESUME, false);
        addInt(ConfigID.FLUID_TYPE, 0);
        addFloat(ConfigID.SIM_SPEED, 1.0f, 0.1f, 1.0f);
        addFloat(ConfigID.VEL_LIFE_TIME, 1.0f, -1.0f, 1.0f);
        addFloat(ConfigID.SWIRLINESS, 0.0f, 0.0f, 3.0f);
        addFloat(ConfigID.VEL_NOISE, 0.0f, 0.0f, 0.05f);
        addInt(ConfigID.BORDER_MODE, 0);
        addFloat(ConfigID.GRAVITY, 0.0f, 0.0f, 0.08f);
        addInt(ConfigID.COLOR_OPTION, 0);
        addFloat(ConfigID.RANDOM_SATURATION, 0.75f, 0.0f, 1.0f);
        addBool(ConfigID.OVERBRIGHT_COLORS, true);
        addIntColor(ConfigID.COLOR0, SupportMenu.CATEGORY_MASK);
        addIntColor(ConfigID.COLOR1, -16711936);
        addIntColor(ConfigID.COLOR2, -16776961);
        addIntColor(ConfigID.COLOR3, -1);
        addIntColor(ConfigID.COLOR4, -1);
        addIntColor(ConfigID.COLOR5, -1);
        addBool(ConfigID.COLOR_ACTIVE0, true);
        addBool(ConfigID.COLOR_ACTIVE1, true);
        addBool(ConfigID.COLOR_ACTIVE2, true);
        addBool(ConfigID.COLOR_ACTIVE3, false);
        addBool(ConfigID.COLOR_ACTIVE4, false);
        addBool(ConfigID.COLOR_ACTIVE5, false);
        addIntColor(ConfigID.DCOLOR0, SupportMenu.CATEGORY_MASK);
        addIntColor(ConfigID.DCOLOR1, -16711936);
        addIntColor(ConfigID.DCOLOR2, -16711936);
        addBool(ConfigID.DCOLOR_ACTIVE0, true);
        addBool(ConfigID.DCOLOR_ACTIVE1, true);
        addBool(ConfigID.DCOLOR_ACTIVE2, true);
        addIntColor(ConfigID.BACKGROUND_COLOR, -1);
        addIntColor(ConfigID.MULTI_COLOR0, 0);
        addIntColor(ConfigID.MULTI_COLOR1, 54015);
        addIntColor(ConfigID.MULTI_COLOR2, 14155520);
        addIntColor(ConfigID.MULTI_COLOR3, 16711739);
        addIntColor(ConfigID.MULTI_COLOR4, 0);
        addIntColor(ConfigID.MULTI_COLOR5, 15116346);
        addIntColor(ConfigID.MULTI_COLOR6, 1503083);
        addIntColor(ConfigID.MULTI_COLOR7, 4211181);
        addIntColor(ConfigID.MULTI_COLOR8, 0);
        addBool(ConfigID.MULTI_COLOR_DOUBLE, true);
        addBool(ConfigID.CARTOON_COLORS, false);
        addFloat(ConfigID.MAGIC_PALETTE_G, 0.1f, 0.0f, 1.0f);
        addFloat(ConfigID.MAGIC_PALETTE_B, 0.2f, 0.0f, 1.0f);
        addFloat(ConfigID.MAGIC_PALETTE_BASE_SHIFT, 0.0f, 0.0f, 1.0f);
        addFloat(ConfigID.MAGIC_PALETTE_BASE_SHIFT_SPEED, 0.0f, 0.0f, 1.0f);
        addBool(ConfigID.MAGIC_PALETTE_BLACK_BACKGR, false);
        addBool(ConfigID.INVERT_COLORS, false);
        addInt(ConfigID.COLOR_CHANGE, 0);
        addFloat(ConfigID.COLOR_RANDOMNESS, 0.0f, 0.0f, 1.0f);
        addBool(ConfigID.PAINT_ENABLED, true);
        addFloat(ConfigID.FLUID_AMOUNT, 0.002f, 1.0E-4f, 0.003f);
        addFloat(ConfigID.FLUID_LIFE_TIME, 5.0f, 0.25f, 51.0f);
        addBool(ConfigID.PARTICLES_ENABLED, false);
        addInt(ConfigID.PARTICLES_MODE, 0);
        addInt(ConfigID.PARTICLES_SHAPE, 0);
        addFloat(ConfigID.PARTICLES_PER_SEC, 650.0f, 50.0f, 2000.0f);
        addFloat(ConfigID.PARTICLES_LIFE_TIME_SEC, 5.0f, 0.25f, 30.0f);
        addFloat(ConfigID.PARTICLES_SIZE, 10.0f, 0.0f, 100.0f);
        addFloat(ConfigID.PARTICLES_INTENSITY, 0.35f, 0.0f, 1.0f);
        addFloat(ConfigID.PARTICLES_SMOOTHNESS, 1.0f, 0.0f, 4.0f);
        addFloat(ConfigID.PARTICLES_TRAIL_LENGTH, 0.5f, 0.0f, 1.0f);
        addInt(ConfigID.PARTICLES_MIXING_MODE, 0);
        addInt(ConfigID.INPUT_SWIPE_MODE, 0);
        addFloat(ConfigID.INPUT_SIZE, 0.03f, 0.015f, 0.125f);
        addFloat(ConfigID.FORCE, GRAVITY_ENABLE_THRESHOLD, 0.0f, 0.001f);
        addBool(ConfigID.INPUT_SWIPE_CONSTANT, false);
        addInt(ConfigID.NUM_SOURCES, 0);
        addFloat(ConfigID.SOURCE_SPEED, 6.0E-4f, 1.5E-4f, 0.0015f);
        addBool(ConfigID.FLASH_ENABLED, false);
        addFloat(ConfigID.FLASH_FREQUENCY, 1.0f, 0.2f, 2.5f);
        addBool(ConfigID.AUTO_ON_RESUME, false);
        addInt(ConfigID.INPUT_TOUCH_MODE, 4);
        addFloat(ConfigID.TOUCH_INPUT_SIZE, 0.25f, 0.0f, 1.0f);
        addFloat(ConfigID.TOUCH_INPUT_FORCE, 0.2f, 0.0f, 1.0f);
        addInt(ConfigID.NUM_HOLD_SOURCES, 0);
        addBool(ConfigID.PE_EDGE, false);
        addFloat(ConfigID.PE_EDGE_INTENSITY, 0.9f, 0.35f, 1.0f);
        addBool(ConfigID.PE_SCATTER, false);
        addFloat(ConfigID.PE_SCATTER_INTENSITY, 0.5f, 0.0f, 1.0f);
        addInt(ConfigID.PE_SCATTER_TYPE, 0);
        addBool(ConfigID.PE_MULTIIMAGE, false);
        addFloat(ConfigID.PE_MULTIIMAGE_INTENSITY, 0.5f, 0.25f, 0.65f);
        addBool(ConfigID.PE_MULTIIMAGE_COLORING, false);
        addBool(ConfigID.PE_MIRROR, false);
        addInt(ConfigID.PE_MIRROR_TYPE, 0);
        addBool(ConfigID.PE_MIRROR_COLORING, true);
        addBool(ConfigID.SHADING_ENABLED, false);
        addFloat(ConfigID.SHADING_BUMPINESS, 0.75f, 0.0f, 1.0f);
        addFloat(ConfigID.SHADING_FLUID_BRIGHTNESS, 0.95f, 0.4f, 1.0f);
        addInt(ConfigID.SHADING_SPEC_TYPE, 1);
        addFloat(ConfigID.SHADING_SPECULAR, 1.3f, 0.0f, 2.0f);
        addFloat(ConfigID.SHADING_SPEC_POWER, 0.4f, 0.0f, 1.0f);
        addBool(ConfigID.SHADING_SPEC_ONLY_GLOW, false);
        addBool(ConfigID.GLOW, false);
        addFloat(ConfigID.GLOW_LEVEL_STRENGTH0, 0.3f, 0.0f, 4.0f);
        addFloat(ConfigID.GLOW_LEVEL_STRENGTH1, 0.3f, 0.0f, 4.0f);
        addFloat(ConfigID.GLOW_LEVEL_STRENGTH2, 0.3f, 0.0f, 4.0f);
        addFloat(ConfigID.GLOW_THRESHOLD, 0.0f, 0.0f, 1.5f);
        addBool(ConfigID.SHADOW_SOURCE, false);
        addBool(ConfigID.SHADOW_SELF, true);
        addBool(ConfigID.SHADOW_INVERSE, false);
        addFloat(ConfigID.SHADOW_INTENSITY, 3.0f, 0.5f, 8.0f);
        addFloat(ConfigID.SHADOW_FALLOFF_LENGTH, 0.25f, 0.025f, 1.0f);
        addBool(ConfigID.LIGHT_SOURCE, false);
        addFloat(ConfigID.LIGHT_RADIUS, 1.0f, 0.15f, 2.0f);
        addFloat(ConfigID.LIGHT_INTENSITY, 1.0f, 0.25f, 2.0f);
        addIntColor(ConfigID.LIGHT_COLOR, -1);
        addFloat(ConfigID.LIGHT_SOURCE_SPEED, 0.0f, 0.0f, 2.0f);
        addFloat(ConfigID.LIGHT_SOURCE_POSX, 0.5f, -0.1f, 1.1f);
        addFloat(ConfigID.LIGHT_SOURCE_POSY, 0.5f, -0.1f, 1.1f);
        addBool(ConfigID.USE_DETAIL_TEXTURE, false);
        addInt(ConfigID.DETAIL_TEXTURE, 0);
        addFloat(ConfigID.DETAIL_UV_SCALE, 2.5f, 1.5f, 3.5f);
    }

    public float[] getFloatArray() {
        return this.floatArray;
    }

    public int[] getIntArray() {
        return this.intArray;
    }

    public boolean[] getBoolArray() {
        return this.boolArray;
    }

    public void updateNativeArrays() {
        ConfigID[] configIDArr;
        for (ConfigID configID : ConfigID.values) {
            ConfigVal configVal = this.configMap.get(configID);
            if (configVal.Type == ConfigVal.DataType.FLOAT) {
                this.floatArray[configID.ordinal()] = ((FloatVal) configVal).Value;
            }
            if (configVal.Type == ConfigVal.DataType.INT) {
                this.intArray[configID.ordinal()] = ((IntVal) configVal).Value;
            }
            if (configVal.Type == ConfigVal.DataType.BOOL) {
                this.boolArray[configID.ordinal()] = ((BoolVal) configVal).Value;
            }
        }
    }

    public @Nullable String displayConfigValues() {
        StringBuilder configText = new StringBuilder();
        for (ConfigID configID : ConfigID.values()) {
            ConfigVal configVal = this.configMap.get(configID);

            // Append the configID and its value to the StringBuilder
            configText.append(configID.toString()).append(" ");

            if (configVal.Type == ConfigVal.DataType.FLOAT) {
                float floatValue = ((FloatVal) configVal).Value;
                configText.append(floatValue);
            } else if (configVal.Type == ConfigVal.DataType.INT) {
                int intValue = ((IntVal) configVal).Value;
                configText.append(intValue);
            } else if (configVal.Type == ConfigVal.DataType.BOOL) {
                boolean boolValue = ((BoolVal) configVal).Value;
                configText.append(boolValue);
            }

            configText.append(" ");
        }

        // Convert the StringBuilder to a String
        return configText.toString();

    }

}
