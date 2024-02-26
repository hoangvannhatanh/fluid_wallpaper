package com.fozechmoblive.fluidwallpaper.livefluid.models

import android.content.res.AssetManager
import android.os.Parcelable
import com.fozechmoblive.fluidwallpaper.livefluid.utils.TypePresetModel
import com.magicfluids.Config
import kotlinx.android.parcel.Parcelize

@Parcelize
data class PresetModel(
    val imagePreset: Int,
    val name: String,
    val isLock: Status,
    val isNew: Boolean,
    var isSelected: Boolean? = false,
    var pathFluidCustom : String = "",
    var pathImageCustom : String = "",
    var typePresetModel: TypePresetModel? = TypePresetModel.ALL
) : Parcelable {

    fun fillConfig(config: Config?, assetManager: AssetManager?) {
        com.fozechmoblive.fluidwallpaper.livefluid.ui.component.wallpaper.fluids.SettingsStorage.loadConfigFromInternalPreset(name, assetManager, config)
    }

}

