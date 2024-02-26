package com.fozechmoblive.fluidwallpaper.livefluid.ui.bases.ext

fun String.isVideoFast() = videoExtensions.any { endsWith(it, true) }

fun String.isImageFast() = photoExtensions.any { endsWith(it, true) }
fun String.isAudioFast() = audioExtensions.any { endsWith(it, true) }
fun String.isRawFast() = rawExtensions.any { endsWith(it, true) }