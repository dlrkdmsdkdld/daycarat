package com.makeus.daycarat.data.data

import android.net.Uri

data class GalleryImage(
    val id: Long,
    val filepath: String,
    val uri: Uri,
    val name: String,
    val date: String,
    val size: Int,
    var isSelected: Boolean = false,
)