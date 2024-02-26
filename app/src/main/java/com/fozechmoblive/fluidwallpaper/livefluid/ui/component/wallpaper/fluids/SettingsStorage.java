package com.fozechmoblive.fluidwallpaper.livefluid.ui.component.wallpaper.fluids;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.AssetManager;
import android.net.Uri;
import android.os.Build;
import android.util.Log;

import androidx.core.view.ViewCompat;

import com.google.android.gms.ads.RequestConfiguration;
import com.magicfluids.Config;
import com.magicfluids.ConfigID;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;


public class SettingsStorage {
    private static final String PRESET_EXISTS = "PRESET_EXISTS";
    private static final String PRESET_NAME = "PRESET_NAME";
    public static final String SETTINGS_LWP_NAME = "LWPSettings";
    public static final String SETTINGS_NAME = "Settings";


    public interface SettingsMap {
        boolean getBoolean(String str, boolean z);

        float getFloat(String str, float f);

        int getInt(String str, int i);
    }

    static String loadTextFileFromAssets(AssetManager assetManager, String str) {
        try {
            InputStream open = assetManager.open(str);
            byte[] bArr = new byte[open.available()];
            open.read(bArr);
            open.close();
            return new String(bArr);
        } catch (IOException e) {
            e.printStackTrace();
            return RequestConfiguration.MAX_AD_CONTENT_RATING_UNSPECIFIED;
        }
    }


    private static class SettingsFileMap implements SettingsMap {
        private Map<String, String> map;

        public SettingsFileMap(String str) {
            String[] split = str.split("\\s+");
            this.map = new HashMap();
            if (split.length % 2 != 0) {
                Log.e("SettingsFileMap", "Settings file has incorrect format: " + str);
                return;
            }
            for (int i = 0; i < split.length; i += 2) {
                this.map.put(split[i], split[i + 1]);
            }
        }

        @Override // com.magicfluids.SettingsStorage.SettingsMap
        public float getFloat(String str, float f) {
            String str2 = this.map.get(str);
            return str2 != null ? Float.valueOf(str2).floatValue() : f;
        }

        @Override // com.magicfluids.SettingsStorage.SettingsMap
        public int getInt(String str, int i) {
            String str2 = this.map.get(str);
            return str2 != null ? Integer.valueOf(str2).intValue() : i;
        }

        @Override // com.magicfluids.SettingsStorage.SettingsMap
        public boolean getBoolean(String str, boolean z) {
            String str2 = this.map.get(str);
            return str2 != null ? Boolean.valueOf(str2).booleanValue() : z;
        }
    }


    public static class SharedPrefsMap implements SettingsMap {
        private SharedPreferences prefs;

        public SharedPrefsMap(SharedPreferences sharedPreferences) {
            this.prefs = sharedPreferences;
        }

        @Override // com.magicfluids.SettingsStorage.SettingsMap
        public float getFloat(String str, float f) {
            return this.prefs.getFloat(str, f);
        }

        @Override // com.magicfluids.SettingsStorage.SettingsMap
        public int getInt(String str, int i) {
            return this.prefs.getInt(str, i);
        }

        @Override // com.magicfluids.SettingsStorage.SettingsMap
        public boolean getBoolean(String str, boolean z) {
            return this.prefs.getBoolean(str, z);
        }
    }

    private static byte[] sharedPrefsToByteArray(SharedPreferences sharedPreferences) {
        String str = RequestConfiguration.MAX_AD_CONTENT_RATING_UNSPECIFIED;
        for (Map.Entry<String, ?> entry : sharedPreferences.getAll().entrySet()) {
            if (!entry.getKey().equals(PRESET_NAME) && !entry.getKey().equals(PRESET_EXISTS)) {
                str = (((str + entry.getKey()) + " ") + entry.getValue().toString()) + " ";
            }
        }
        return str.getBytes();
    }

    private static void saveConfig(Context context, Config config, String str) {
        SharedPreferences.Editor edit = context.getSharedPreferences(str, 0).edit();
        for (Map.Entry<ConfigID, Config.ConfigVal> entry : config.getMap().entrySet()) {
            if (entry.getValue().Type == Config.ConfigVal.DataType.FLOAT) {
                edit.putFloat(entry.getKey().name(), ((Config.FloatVal) entry.getValue()).Value);
            }
            if (entry.getValue().Type == Config.ConfigVal.DataType.INT) {
                edit.putInt(entry.getKey().name(), ((Config.IntVal) entry.getValue()).Value);
            }
            if (entry.getValue().Type == Config.ConfigVal.DataType.BOOL) {
                edit.putBoolean(entry.getKey().name(), ((Config.BoolVal) entry.getValue()).Value);
            }
        }
        edit.commit();
    }

    public static boolean prefFileExist(Context context, String str) {
        return context.getSharedPreferences(str, 0).contains(ConfigID.FLUID_TYPE.name());
    }

    private static void loadConfigFromMap(SettingsMap settingsMap, Config config, boolean z) {
        for (Map.Entry<ConfigID, Config.ConfigVal> entry : config.getMap().entrySet()) {
            if (z || entry.getValue().IsPresetRelated) {
                if (entry.getValue().Type == Config.ConfigVal.DataType.FLOAT) {
                    Config.FloatVal floatVal = (Config.FloatVal) entry.getValue();
                    floatVal.Value = settingsMap.getFloat(entry.getKey().name(), floatVal.Default);
                }
                if (entry.getValue().Type == Config.ConfigVal.DataType.INT) {
                    Config.IntVal intVal = (Config.IntVal) entry.getValue();
                    intVal.Value = settingsMap.getInt(entry.getKey().name(), intVal.Default);
                    if (intVal.IsColor) {
                        intVal.Value |= ViewCompat.MEASURED_STATE_MASK;
                    }
                }
                if (entry.getValue().Type == Config.ConfigVal.DataType.BOOL) {
                    Config.BoolVal boolVal = (Config.BoolVal) entry.getValue();
                    boolVal.Value = settingsMap.getBoolean(entry.getKey().name(), boolVal.Default);
                }
            }
        }
    }

    private static void loadConfigFromSharedPrefs(Context context, Config config, String str, boolean z) {
        loadConfigFromMap(new SharedPrefsMap(context.getSharedPreferences(str, 0)), config, z);
    }

    private static String getUserPresetPrefKey(int i) {
        return "UserSettings" + i;
    }

    public static void loadSessionConfig(Context context, Config config, String str) {
        loadConfigFromSharedPrefs(context, config, str, true);
    }

    public static void saveSessionConfig(Context context, Config config, String str) {
        saveConfig(context, config, str);
    }

    public static void loadConfigFromUserPreset(Context context, Config config, int i) {
        loadConfigFromSharedPrefs(context, config, getUserPresetPrefKey(i), false);
    }

    public static void saveConfigToUserPreset(Context context, Config config, int i, String str) {
        String userPresetPrefKey = getUserPresetPrefKey(i);
        saveConfig(context, config, userPresetPrefKey);
        SharedPreferences.Editor edit = context.getSharedPreferences(userPresetPrefKey, 0).edit();
        edit.putString(PRESET_NAME, str);
        edit.putBoolean(PRESET_EXISTS, true);
        edit.commit();
    }

    public static void removeUserPreset(Context context, int i) {
        SharedPreferences.Editor edit = context.getSharedPreferences(getUserPresetPrefKey(i), 0).edit();
        edit.putString(PRESET_NAME, "Empty");
        edit.putBoolean(PRESET_EXISTS, false);
        edit.commit();
    }

    public static void loadConfigFromInternalPreset(String str, AssetManager assetManager, Config config) {
        loadConfigFromMap(new SettingsFileMap(loadTextFileFromAssets(assetManager, "presets/" + str.replaceAll("\\s+", RequestConfiguration.MAX_AD_CONTENT_RATING_UNSPECIFIED))), config, false);
    }

    public static void loadConfigPresetCustom(String path, Config config) {
        loadConfigFromMap(new SettingsFileMap(readFileContent(path)), config, false);
    }

    public static String getUserPresetName(Context context, int i) {
        return context.getSharedPreferences("UserSettings" + i, 0).getString(PRESET_NAME, "Empty");
    }

    public static boolean isSavedUserPreset(Context context, int i) {
        return context.getSharedPreferences("UserSettings" + i, 0).getBoolean(PRESET_EXISTS, false);
    }

    private static String readTextFromUri(Context context, Uri uri) throws IOException {
        StringBuilder sb = new StringBuilder();
        InputStream openInputStream = context.getContentResolver().openInputStream(uri);
        try {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader((InputStream) Objects.requireNonNull(openInputStream)));
            while (true) {
                String readLine = bufferedReader.readLine();
                if (readLine == null) {
                    break;
                }
                sb.append(readLine);
                sb.append('\n');
            }
            bufferedReader.close();
            if (openInputStream != null) {
                openInputStream.close();
            }
            return sb.toString();
        } catch (Throwable th) {
            try {
                throw th;
            } catch (Throwable th2) {
                if (openInputStream != null) {
                    try {
                        openInputStream.close();
                    } catch (Throwable th3) {
                        th.addSuppressed(th3);
                    }
                }
                throw th2;
            }
        }
    }


    public static boolean importPresets(Context context, Uri uri) {
        if (Build.VERSION.SDK_INT < 19) {
            return false;
        }
        try {
            String[] split = readTextFromUri(context, uri).split("\n");
            if (split[0].equals("mf_presetfile_v01")) {
                for (int i = 0; i < 20; i++) {
                    removeUserPreset(context, i);
                }
                for (int i2 = 1; i2 < split.length; i2++) {
                    String str = split[i2];
                    if (str.length() > 2) {
                        String[] split2 = str.split(" ", 3);
                        int parseInt = Integer.parseInt(split2[0]);
                        if (parseInt >= 0 && parseInt < 20) {
                            String str2 = split2[1];
                            SettingsFileMap settingsFileMap = new SettingsFileMap(split2[2]);
                            Config config = new Config();
                            loadConfigFromMap(settingsFileMap, config, false);
                            saveConfigToUserPreset(context, config, parseInt, str2);
                        }
                    }
                }
                return true;
            }
            return false;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }


    public static boolean exportPresets(Context context, Uri uri) {
        try {
            OutputStream openOutputStream = context.getContentResolver().openOutputStream(uri);
            try {
                openOutputStream.write("mf_presetfile_v01".getBytes());
                openOutputStream.write("\n".getBytes());
                for (int i = 0; i < 20; i++) {
                    if (isSavedUserPreset(context, i)) {
                        openOutputStream.write(Integer.toString(i).getBytes());
                        openOutputStream.write(" ".getBytes());
                        openOutputStream.write(getUserPresetName(context, i).replace(' ', '_').getBytes());
                        openOutputStream.write(" ".getBytes());
                        openOutputStream.write(sharedPrefsToByteArray(context.getSharedPreferences(getUserPresetPrefKey(i), 0)));
                        openOutputStream.write("\n".getBytes());
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                openOutputStream.flush();
                openOutputStream.close();
                return true;
            } catch (IOException e2) {
                e2.printStackTrace();
                return false;
            }
        } catch (FileNotFoundException e3) {
            e3.printStackTrace();
            return false;
        }
    }


    private static String readFileContent(String filePath) {
        File file = new File(filePath);
        StringBuilder content = new StringBuilder();

        try {
            // Kiểm tra xem tệp tin có tồn tại không
            if (file.exists()) {
                // Sử dụng BufferedReader để đọc tệp tin dòng đến dòng
                BufferedReader reader = new BufferedReader(new FileReader(file));
                String line = reader.readLine();

                while (line != null) {
                    content.append(line).append('\n');
                    line = reader.readLine();
                }

                // Đóng BufferedReader sau khi đọc xong
                reader.close();
            } else {
                System.out.println("File not found: " + filePath);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return content.toString();
    }
}
