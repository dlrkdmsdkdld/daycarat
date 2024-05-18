package com.makeus.daycarat.domain.repository

import android.database.Cursor
import com.makeus.daycarat.data.data.GalleryImage

interface GalleryRepository {


    fun getAllPhotos(
        page: Int,
        loadSize: Int,
        currentLocation: String?
    ): MutableList<GalleryImage>


    fun getQuery(
        offset: Int,
        limit: Int,
        selection: String?,
        selectionArgs: Array<String>?
    ): Cursor?

    fun getFolderList(): ArrayList<String>

}