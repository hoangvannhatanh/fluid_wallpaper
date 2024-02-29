package com.fozechmoblive.fluidwallpaper.livefluid.utils


import android.content.Context
import com.fozechmoblive.fluidwallpaper.livefluid.R
import com.fozechmoblive.fluidwallpaper.livefluid.models.PresetModel
import com.fozechmoblive.fluidwallpaper.livefluid.models.Status
import java.io.File

object CommonData {
    // Don't format this file
    fun getListPreset(): ArrayList<PresetModel> {
        val listPresetModel = ArrayList<PresetModel>()

        listPresetModel.add(
            PresetModel(
                R.drawable.bg_preset_68,
                "Dancing Dna 2",
                Status.FREE,
                false,
                typePresetModel = TypePresetModel.NEW
            )
        )
        listPresetModel.add(
            PresetModel(
                R.drawable.bg_preset_69,
                "Exotic Energy 2",
                Status.FREE,
                false,
                typePresetModel = TypePresetModel.NEW
            )
        )
        listPresetModel.add(
            PresetModel(
                R.drawable.bg_preset_70,
                "Exotic Energy 3",
                Status.FREE,
                false,
                typePresetModel = TypePresetModel.NEW
            )
        )
        listPresetModel.add(
            PresetModel(
                R.drawable.bg_preset_73,
                "Exotic Energy 6",
                Status.FREE,
                false,
                typePresetModel = TypePresetModel.NEW
            )
        )
        listPresetModel.add(
            PresetModel(
                R.drawable.bg_preset_82,
                "Fire Flies 8",
                Status.FREE,
                false,
                typePresetModel = TypePresetModel.NEW
            )
        )
        listPresetModel.add(
            PresetModel(
                R.drawable.bg_preset_79,
                "Fire Flies 5",
                Status.FREE,
                false,
                typePresetModel = TypePresetModel.NEW
            )
        )
        listPresetModel.add(
            PresetModel(
                R.drawable.bg_preset_76,
                "Fire Flies 2",
                Status.FREE,
                false,
                typePresetModel = TypePresetModel.NEW
            )
        )
        listPresetModel.add(
            PresetModel(
                R.drawable.bg_preset_92,
                "Floating Flames 9",
                Status.FREE,
                false,
                typePresetModel = TypePresetModel.NEW
            )
        )
        listPresetModel.add(
            PresetModel(
                R.drawable.bg_preset_89,
                "Floating Flames 6",
                Status.FREE,
                false,
                typePresetModel = TypePresetModel.NEW
            )
        )
        listPresetModel.add(
            PresetModel(
                R.drawable.bg_preset_86,
                "Floating Flames 3",
                Status.FREE,
                false,
                typePresetModel = TypePresetModel.NEW
            )
        )
        listPresetModel.add(
            PresetModel(
                R.drawable.bg_preset_102,
                "Lake of Lava 8",
                Status.FREE,
                false,
                typePresetModel = TypePresetModel.NEW
            )
        )
        listPresetModel.add(
            PresetModel(
                R.drawable.bg_preset_99,
                "Lake of Lava 5",
                Status.FREE,
                false,
                typePresetModel = TypePresetModel.NEW
            )
        )
        listPresetModel.add(
            PresetModel(
                R.drawable.bg_preset_97,
                "Lake of Lava 3",
                Status.FREE,
                false,
                typePresetModel = TypePresetModel.NEW
            )
        )
        listPresetModel.add(
            PresetModel(
                R.drawable.bg_preset_vip2,
                "Liquid Spectra",
                Status.FREE,
                false,
                typePresetModel = TypePresetModel.NEW
            )
        )
        listPresetModel.add(
            PresetModel(
                R.drawable.bg_preset_105,
                "Magic Trail by Toni 4",
                Status.FREE,
                false,
                typePresetModel = TypePresetModel.NEW
            )
        )
        listPresetModel.add(
            PresetModel(
                R.drawable.bg_preset_56,
                "Particle Party",
                Status.FREE,
                false,
                typePresetModel = TypePresetModel.NEW
            )
        )
        listPresetModel.add(
            PresetModel(
                R.drawable.bg_preset_16,
                "Wobbly World",
                Status.FREE,
                false,
                typePresetModel = TypePresetModel.NEW
            )
        )
        listPresetModel.add(
            PresetModel(
                R.drawable.bg_preset_61,
                "Floating Flames",
                Status.FREE,
                false,
                typePresetModel = TypePresetModel.NEW
            )
        )
        listPresetModel.add(
            PresetModel(
                R.drawable.bg_preset_vip8,
                "Transcend Ripples",
                Status.FREE,
                false,
                typePresetModel = TypePresetModel.NEW
            )
        )
        listPresetModel.add(
            PresetModel(
                R.drawable.bg_preset_59,
                "Magic Trail",
                Status.FREE,
                false,
                typePresetModel = TypePresetModel.NEW
            )
        )
        listPresetModel.add(
            PresetModel(
                R.drawable.bg_preset_63,
                "Subtle Setting",
                Status.FREE,
                false,
                typePresetModel = TypePresetModel.NEW
            )
        )
        listPresetModel.add(
            PresetModel(
                R.drawable.bg_preset_67,
                "Swirly Sparkles",
                Status.FREE,
                false,
                typePresetModel = TypePresetModel.NEW
            )
        )
        listPresetModel.add(
            PresetModel(
                R.drawable.bg_preset_6,
                "Gleeful Glimmers",
                Status.FREE,
                false,
                typePresetModel = TypePresetModel.NEW
            )
        )
        listPresetModel.add(
            PresetModel(
                R.drawable.bg_preset_62,
                "Glimming Glow",
                Status.FREE,
                false,
                typePresetModel = TypePresetModel.NEW
            )
        )
        listPresetModel.add(
            PresetModel(
                R.drawable.bg_preset_54,
                "Trippy Tint",
                Status.FREE,
                false,
                typePresetModel = TypePresetModel.NEW
            )
        )
        listPresetModel.add(
            PresetModel(
                R.drawable.bg_preset_55,
                "Girls Game",
                Status.FREE,
                false,
                typePresetModel = TypePresetModel.NEW
            )
        )
        listPresetModel.add(
            PresetModel(
                R.drawable.bg_preset_57,
                "Busy Brilliance",
                Status.FREE,
                false,
                typePresetModel = TypePresetModel.NEW
            )
        )
        listPresetModel.add(
            PresetModel(
                R.drawable.bg_preset_58,
                "Grainy Greatness",
                Status.FREE,
                false,
                typePresetModel = TypePresetModel.NEW
            )
        )
        listPresetModel.add(
            PresetModel(
                R.drawable.bg_preset_25,
                "Disturbing Details",
                Status.FREE,
                false,
                typePresetModel = TypePresetModel.NEW
            )
        )
        listPresetModel.add(
            PresetModel(
                R.drawable.bg_preset_38,
                "Random Remarkability",
                Status.FREE,
                false,
                typePresetModel = TypePresetModel.NEW
            )
        )
        listPresetModel.add(
            PresetModel(
                R.drawable.bg_preset_39,
                "Gravity Game",
                Status.FREE,
                false,
                typePresetModel = TypePresetModel.NEW
            )
        )
        listPresetModel.add(
            PresetModel(
                R.drawable.bg_preset_40,
                "Wonderful Whirls",
                Status.FREE,
                false,
                typePresetModel = TypePresetModel.NEW
            )
        )
        listPresetModel.add(
            PresetModel(
                R.drawable.bg_preset_41,
                "Fading Forms",
                Status.FREE,
                false,
                typePresetModel = TypePresetModel.NEW
            )
        )
        listPresetModel.add(
            PresetModel(
                R.drawable.bg_preset_42,
                "Wavy Winter",
                Status.FREE,
                false,
                typePresetModel = TypePresetModel.NEW
            )
        )
        listPresetModel.add(
            PresetModel(
                R.drawable.bg_preset_43,
                "Bloody Blue",
                Status.FREE,
                false,
                typePresetModel = TypePresetModel.NEW
            )
        )
        listPresetModel.add(
            PresetModel(
                R.drawable.bg_preset_44,
                "Lake of Lava",
                Status.FREE,
                false,
                typePresetModel = TypePresetModel.NEW
            )
        )
        listPresetModel.add(
            PresetModel(
                R.drawable.bg_preset_45,
                "Steady Sea",
                Status.FREE,
                false,
                typePresetModel = TypePresetModel.NEW
            )
        )
        listPresetModel.add(
            PresetModel(
                R.drawable.bg_preset_46,
                "Freaky Fun",
                Status.FREE,
                false,
                typePresetModel = TypePresetModel.NEW
            )
        )

        listPresetModel.add(
            PresetModel(
                R.drawable.bg_preset_72,
                "Exotic Energy 5",
                Status.FREE,
                false,
                typePresetModel = TypePresetModel.FEATURE
            )
        )
        listPresetModel.add(
            PresetModel(
                R.drawable.bg_preset_75,
                "Fantastic Filaments 3",
                Status.FREE,
                false,
                typePresetModel = TypePresetModel.FEATURE
            )
        )
        listPresetModel.add(
            PresetModel(
                R.drawable.bg_preset_78,
                "Fire Flies 4",
                Status.FREE,
                false,
                typePresetModel = TypePresetModel.FEATURE
            )
        )
        listPresetModel.add(
            PresetModel(
                R.drawable.bg_preset_81,
                "Fire Flies 7",
                Status.FREE,
                false,
                typePresetModel = TypePresetModel.FEATURE
            )
        )
        listPresetModel.add(
            PresetModel(
                R.drawable.bg_preset_84,
                "Fire Flies 10",
                Status.FREE,
                false,
                typePresetModel = TypePresetModel.FEATURE
            )
        )
        listPresetModel.add(
            PresetModel(
                R.drawable.bg_preset_88,
                "Floating Flames 5",
                Status.FREE,
                false,
                typePresetModel = TypePresetModel.FEATURE
            )
        )
        listPresetModel.add(
            PresetModel(
                R.drawable.bg_preset_91,
                "Floating Flames 8",
                Status.FREE,
                false,
                typePresetModel = TypePresetModel.FEATURE
            )
        )
        listPresetModel.add(
            PresetModel(
                R.drawable.bg_preset_96,
                "Lake of Lava 2",
                Status.FREE,
                false,
                typePresetModel = TypePresetModel.FEATURE
            )
        )
        listPresetModel.add(
            PresetModel(
                R.drawable.bg_preset_101,
                "Lake of Lava 7",
                Status.FREE,
                false,
                typePresetModel = TypePresetModel.FEATURE
            )
        )
        listPresetModel.add(
            PresetModel(
                R.drawable.bg_preset_104,
                "Magic Trail by Toni 3",
                Status.FREE,
                false,
                typePresetModel = TypePresetModel.FEATURE
            )
        )
        listPresetModel.add(
            PresetModel(
                R.drawable.bg_preset_34,
                "Dancing in the Dark",
                Status.FREE,
                false,
                typePresetModel = TypePresetModel.FEATURE
            )
        )
        listPresetModel.add(
            PresetModel(
                R.drawable.bg_preset_35,
                "Blinking Beauty",
                Status.FREE,
                false,
                typePresetModel = TypePresetModel.FEATURE
            )
        )
        listPresetModel.add(
            PresetModel(
                R.drawable.bg_preset_36,
                "Earthly Elements",
                Status.FREE,
                false,
                typePresetModel = TypePresetModel.FEATURE
            )
        )
        listPresetModel.add(
            PresetModel(
                R.drawable.bg_preset_37,
                "Crazy Colors",
                Status.FREE,
                false,
                typePresetModel = TypePresetModel.FEATURE
            )
        )
        listPresetModel.add(
            PresetModel(
                R.drawable.bg_preset_48,
                "Gentle Glow",
                Status.FREE,
                false,
                typePresetModel = TypePresetModel.FEATURE
            )
        )
        listPresetModel.add(
            PresetModel(
                R.drawable.bg_preset_49,
                "Transient Thoughts",
                Status.FREE,
                false,
                typePresetModel = TypePresetModel.FEATURE
            )
        )
        listPresetModel.add(
            PresetModel(
                R.drawable.bg_preset_50,
                "Glorious Galaxies",
                Status.FREE,
                false,
                typePresetModel = TypePresetModel.FEATURE
            )
        )
        listPresetModel.add(
            PresetModel(
                R.drawable.bg_preset_51,
                "Something Strange",
                Status.FREE,
                false,
                typePresetModel = TypePresetModel.FEATURE
            )
        )
        listPresetModel.add(
            PresetModel(
                R.drawable.bg_preset_52,
                "Cloudy Composition",
                Status.FREE,
                false,
                typePresetModel = TypePresetModel.FEATURE
            )
        )
        listPresetModel.add(
            PresetModel(
                R.drawable.bg_preset_53,
                "Glowing Glare",
                Status.FREE,
                false,
                typePresetModel = TypePresetModel.FEATURE
            )
        )

        listPresetModel.add(
            PresetModel(
                R.drawable.bg_preset_71,
                "Exotic Energy 4",
                Status.FREE,
                false,
                typePresetModel = TypePresetModel.TRENDING
            )
        )
        listPresetModel.add(
            PresetModel(
                R.drawable.bg_preset_74,
                "Fantastic Filaments 2",
                Status.FREE,
                false,
                typePresetModel = TypePresetModel.TRENDING
            )
        )
        listPresetModel.add(
            PresetModel(
                R.drawable.bg_preset_77,
                "Fire Flies 3",
                Status.FREE,
                false,
                typePresetModel = TypePresetModel.TRENDING
            )
        )
        listPresetModel.add(
            PresetModel(
                R.drawable.bg_preset_80,
                "Fire Flies 6",
                Status.FREE,
                false,
                typePresetModel = TypePresetModel.TRENDING
            )
        )
        listPresetModel.add(
            PresetModel(
                R.drawable.bg_preset_83,
                "Fire Flies 9",
                Status.FREE,
                false,
                typePresetModel = TypePresetModel.TRENDING
            )
        )

        listPresetModel.add(
            PresetModel(
                R.drawable.bg_preset_87,
                "Floating Flames 4",
                Status.FREE,
                false,
                typePresetModel = TypePresetModel.TRENDING
            )
        )
        listPresetModel.add(
            PresetModel(
                R.drawable.bg_preset_90,
                "Floating Flames 7",
                Status.FREE,
                false,
                typePresetModel = TypePresetModel.TRENDING
            )
        )
        listPresetModel.add(
            PresetModel(
                R.drawable.bg_preset_93,
                "Floating Flames 10",
                Status.FREE,
                false,
                typePresetModel = TypePresetModel.TRENDING
            )
        )
        listPresetModel.add(
            PresetModel(
                R.drawable.bg_preset_98,
                "Lake of Lava 4",
                Status.FREE,
                false,
                typePresetModel = TypePresetModel.TRENDING
            )
        )
        listPresetModel.add(
            PresetModel(
                R.drawable.bg_preset_100,
                "Lake of Lava 6",
                Status.FREE,
                false,
                typePresetModel = TypePresetModel.TRENDING
            )
        )
        listPresetModel.add(
            PresetModel(
                R.drawable.bg_preset_103,
                "Magic Trail by Toni 2",
                Status.FREE,
                false,
                typePresetModel = TypePresetModel.TRENDING
            )
        )
        listPresetModel.add(
            PresetModel(
                R.drawable.bg_preset_vip1,
                "Flow Canvas",
                Status.FREE,
                false,
                typePresetModel = TypePresetModel.TRENDING
            )
        )
        listPresetModel.add(
            PresetModel(
                R.drawable.bg_preset_vip3,
                "Aqua Ripple",
                Status.FREE,
                false,
                typePresetModel = TypePresetModel.TRENDING
            )
        )
        listPresetModel.add(
            PresetModel(
                R.drawable.bg_preset_vip4,
                "Dynamic Ink",
                Status.FREE,
                false,
                typePresetModel = TypePresetModel.TRENDING
            )
        )
        listPresetModel.add(
            PresetModel(
                R.drawable.bg_preset_vip5,
                "Ephemeral Wave",
                Status.FREE,
                false,
                typePresetModel = TypePresetModel.TRENDING
            )
        )
        listPresetModel.add(
            PresetModel(
                R.drawable.bg_preset_vip6,
                "Lumina Flow",
                Status.FREE,
                false,
                typePresetModel = TypePresetModel.TRENDING
            )
        )
        listPresetModel.add(
            PresetModel(
                R.drawable.bg_preset_vip7,
                "Mirage Flow",
                Status.FREE,
                false,
                typePresetModel = TypePresetModel.TRENDING
            )
        )
        listPresetModel.add(
            PresetModel(
                R.drawable.bg_preset_vip9,
                "Liquid Dreamscape",
                Status.FREE,
                false,
                typePresetModel = TypePresetModel.TRENDING
            )
        )
        listPresetModel.add(
            PresetModel(
                R.drawable.bg_preset_vip10,
                "Lustrous Liquidity",
                Status.FREE,
                false,
                typePresetModel = TypePresetModel.TRENDING
            )
        )
        listPresetModel.add(
            PresetModel(
                R.drawable.bg_preset_13,
                "Fire Flies",
                Status.FREE,
                true,
                typePresetModel = TypePresetModel.TRENDING
            )
        )
        listPresetModel.add(
            PresetModel(
                R.drawable.bg_preset_3,
                "Fantastic Filaments",
                Status.FREE,
                true,
                typePresetModel = TypePresetModel.TRENDING
            )
        )
        listPresetModel.add(
            PresetModel(
                R.drawable.bg_preset_65,
                "Bouncing Beach",
                Status.FREE,
                false,
                typePresetModel = TypePresetModel.TRENDING
            )
        )
        listPresetModel.add(
            PresetModel(
                R.drawable.bg_preset_17,
                "Fantasy Fireworks",
                Status.FREE,
                false,
                typePresetModel = TypePresetModel.TRENDING
            )
        )
        listPresetModel.add(
            PresetModel(
                R.drawable.bg_preset_2,
                "Wonderful Waves",
                Status.FREE,
                true,
                typePresetModel = TypePresetModel.TRENDING
            )
        )
        listPresetModel.add(
            PresetModel(
                R.drawable.bg_preset_1,
                "Exotic Energy",
                Status.FREE,
                false,
                typePresetModel = TypePresetModel.TRENDING
            )
        )
        listPresetModel.add(
            PresetModel(
                R.drawable.bg_preset_60,
                "Lovely Liquid",
                Status.FREE,
                false,
                typePresetModel = TypePresetModel.TRENDING
            )
        )
        listPresetModel.add(
            PresetModel(
                R.drawable.bg_preset_64,
                "Calm Christmas",
                Status.FREE,
                false,
                typePresetModel = TypePresetModel.TRENDING
            )
        )
        listPresetModel.add(
            PresetModel(
                R.drawable.bg_preset_66,
                "Classy Combination",
                Status.FREE,
                false,
                typePresetModel = TypePresetModel.TRENDING
            )
        )
        listPresetModel.add(
            PresetModel(
                R.drawable.bg_preset_4,
                "Slick Slime",
                Status.FREE,
                false,
                typePresetModel = TypePresetModel.TRENDING
            )
        )
        listPresetModel.add(
            PresetModel(
                R.drawable.bg_preset_5,
                "Dancing Dna",
                Status.FREE,
                false,
                typePresetModel = TypePresetModel.TRENDING
            )
        )
        listPresetModel.add(
            PresetModel(
                R.drawable.bg_preset_7,
                "Dimension of Depth",
                Status.FREE,
                false,
                typePresetModel = TypePresetModel.TRENDING
            )
        )
        listPresetModel.add(
            PresetModel(
                R.drawable.bg_preset_8,
                "Super Smoke",
                Status.FREE,
                false,
                typePresetModel = TypePresetModel.TRENDING
            )
        )
        listPresetModel.add(
            PresetModel(
                R.drawable.bg_preset_9,
                "Flashy Fluids",
                Status.FREE,
                false,
                typePresetModel = TypePresetModel.TRENDING
            )
        )
        listPresetModel.add(
            PresetModel(
                R.drawable.bg_preset_10,
                "Tangling Trails",
                Status.FREE,
                true,
                typePresetModel = TypePresetModel.TRENDING
            )
        )
        listPresetModel.add(
            PresetModel(
                R.drawable.bg_preset_11,
                "Magic Marker",
                Status.FREE,
                true,
                typePresetModel = TypePresetModel.TRENDING
            )
        )
        listPresetModel.add(
            PresetModel(
                R.drawable.bg_preset_12,
                "Complex Cells",
                Status.FREE,
                true,
                typePresetModel = TypePresetModel.TRENDING
            )
        )
        listPresetModel.add(
            PresetModel(
                R.drawable.bg_preset_14,
                "Electrifying Experience",
                Status.FREE,
                false,
                typePresetModel = TypePresetModel.TRENDING
            )
        )
        listPresetModel.add(
            PresetModel(
                R.drawable.bg_preset_15,
                "Magic Mirror",
                Status.FREE,
                false,
                typePresetModel = TypePresetModel.TRENDING
            )
        )
        listPresetModel.add(
            PresetModel(
                R.drawable.bg_preset_18,
                "Impressionist Image",
                Status.FREE,
                false,
                typePresetModel = TypePresetModel.TRENDING
            )
        )
        listPresetModel.add(
            PresetModel(
                R.drawable.bg_preset_19,
                "Peaceful Pond",
                Status.FREE,
                false,
                typePresetModel = TypePresetModel.TRENDING
            )
        )
        listPresetModel.add(
            PresetModel(
                R.drawable.bg_preset_20,
                "Creative Canvas",
                Status.FREE,
                false,
                typePresetModel = TypePresetModel.TRENDING
            )
        )
        listPresetModel.add(
            PresetModel(
                R.drawable.bg_preset_21,
                "Fire and Flame",
                Status.FREE,
                false,
                typePresetModel = TypePresetModel.TRENDING
            )
        )
        listPresetModel.add(
            PresetModel(
                R.drawable.bg_preset_22,
                "Molten Metal",
                Status.FREE,
                false,
                typePresetModel = TypePresetModel.TRENDING
            )
        )
        listPresetModel.add(
            PresetModel(
                R.drawable.bg_preset_23,
                "Weird Water",
                Status.FREE,
                false,
                typePresetModel = TypePresetModel.TRENDING
            )
        )
        listPresetModel.add(
            PresetModel(
                R.drawable.bg_preset_24,
                "Silky Smooth",
                Status.FREE,
                false,
                typePresetModel = TypePresetModel.TRENDING
            )
        )
        listPresetModel.add(
            PresetModel(
                R.drawable.bg_preset_26,
                "Uniquely Unreal",
                Status.FREE,
                false,
                typePresetModel = TypePresetModel.TRENDING
            )
        )
        listPresetModel.add(
            PresetModel(
                R.drawable.bg_preset_27,
                "Blinding Bliss",
                Status.FREE,
                false,
                typePresetModel = TypePresetModel.TRENDING
            )
        )
        listPresetModel.add(
            PresetModel(
                R.drawable.bg_preset_28,
                "Life of Lights",
                Status.FREE,
                false,
                typePresetModel = TypePresetModel.TRENDING
            )
        )
        listPresetModel.add(
            PresetModel(
                R.drawable.bg_preset_29,
                "Cosmic Charm",
                Status.FREE,
                false,
                typePresetModel = TypePresetModel.TRENDING
            )
        )
        listPresetModel.add(
            PresetModel(
                R.drawable.bg_preset_30,
                "Strange Substance",
                Status.FREE,
                false,
                typePresetModel = TypePresetModel.TRENDING
            )
        )
        listPresetModel.add(
            PresetModel(
                R.drawable.bg_preset_31,
                "Jittery Jello",
                Status.FREE,
                false,
                typePresetModel = TypePresetModel.TRENDING
            )
        )
        listPresetModel.add(
            PresetModel(
                R.drawable.bg_preset_32,
                "Radioactive Rumble",
                Status.FREE,
                false,
                typePresetModel = TypePresetModel.TRENDING
            )
        )
        listPresetModel.add(
            PresetModel(
                R.drawable.bg_preset_33,
                "Wizard Wand",
                Status.FREE,
                false,
                typePresetModel = TypePresetModel.TRENDING
            )
        )
        listPresetModel.add(
            PresetModel(
                R.drawable.bg_preset_47,
                "Incredible Ink",
                Status.FREE,
                false,
                typePresetModel = TypePresetModel.TRENDING
            )
        )

        return listPresetModel
    }


    fun getListPresetCustom(context: Context): ArrayList<PresetModel> {
        val directory = File(
            context.externalCacheDir,
            "Fluids_Custom"
        )

        val listPresetModel = ArrayList<PresetModel>()

        if (directory.isDirectory && directory.canRead()) {
            val files = directory.listFiles()

            for (file in files) {
                if (file.isDirectory) {
                    listPresetModel.add(
                        PresetModel(
                            0,
                            file.name,
                            Status.FREE,
                            false,
                            typePresetModel = TypePresetModel.CUSTOM,
                            pathFluidCustom = directory.path + "/" + file.name + "/" + file.name + ".txt",
                            pathImageCustom = directory.path + "/" + file.name + "/" + file.name + ".png"
                        )
                    )

                }
            }
        }

        return listPresetModel
    }
}

enum class TypePresetModel {
    TRENDING, NEW, FEATURE, ALL, CUSTOM, ADS
}

