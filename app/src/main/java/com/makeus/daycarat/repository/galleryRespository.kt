package com.makeus.daycarat.repository

import android.os.Build
import android.content.ContentResolver
import android.content.ContentUris
import android.content.Context
import android.net.Uri
import android.provider.MediaStore
import androidx.core.os.bundleOf
import com.makeus.daycarat.data.data.GalleryImage
import dagger.hilt.android.qualifiers.ApplicationContext
import java.io.File
import javax.inject.Inject

class GalleryRepository @Inject constructor(
    @ApplicationContext private val context: Context,
)  {

    private val uriExternal: Uri by lazy {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            MediaStore.Images.Media.getContentUri(
                MediaStore.VOLUME_EXTERNAL,
            )
        } else {
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI
        }
    }

    private val projection = arrayOf(
        MediaStore.Images.ImageColumns.DATA,
        MediaStore.Images.ImageColumns.DISPLAY_NAME, // 이름
//        MediaStore.Images.ImageColumns.SIZE, // 크기
        MediaStore.Images.ImageColumns.DATE_TAKEN,
//        MediaStore.Images.ImageColumns.DATE_ADDED, // 추가된 날짜
        MediaStore.Images.ImageColumns._ID,
    )
    private val sortedOrder = MediaStore.Images.ImageColumns.DATE_TAKEN

    private val contentResolver by lazy {
        context.contentResolver
    }

    fun getAllPhotos(
        page: Int,
        loadSize: Int,
        currentLocation: String?,
    ): MutableList<GalleryImage> {
        val galleryImageList = mutableListOf<GalleryImage>()
        var selection: String? = null
        var selectionArgs: Array<String>? = null

        if (!currentLocation.isNullOrBlank() && !currentLocation.equals("전체")) {
            selection = "${MediaStore.Images.Media.DATA} LIKE ?"
            selectionArgs = arrayOf("%$currentLocation%")
        }
        val limit = loadSize
        val offset = (page - 1) * loadSize // offset은 시작 위치를 지정해줌
        val query = getQuery(offset, limit, selection, selectionArgs)
        query?.use { cursor ->
            while (cursor.moveToNext()) {
                val id =
                    cursor.getLong(cursor.getColumnIndexOrThrow(MediaStore.Images.ImageColumns._ID))
                val name =
                    cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Images.ImageColumns.DISPLAY_NAME))
                val filepath =
                    cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Images.ImageColumns.DATA))
                val date =
                    cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Images.ImageColumns.DATE_TAKEN))
                val contentUri = ContentUris.withAppendedId(uriExternal, id)
                val image = GalleryImage(
                    id = id,
                    filepath = filepath,
                    uri = contentUri,
                    name = name,
                    date = date ?: "",
                    size = 0,
                )
                galleryImageList.add(image)
            }
            cursor.close()
        }
        return galleryImageList
    }

    private fun getQuery(
        offset: Int,
        limit: Int,
        selection: String?,
        selectionArgs: Array<String>?,
    ) = if (Build.VERSION.SDK_INT > Build.VERSION_CODES.Q) {
        val bundle = bundleOf(
            ContentResolver.QUERY_ARG_OFFSET to offset,
            ContentResolver.QUERY_ARG_LIMIT to limit,
            ContentResolver.QUERY_ARG_SORT_COLUMNS to arrayOf(MediaStore.Files.FileColumns.DATE_MODIFIED),
            ContentResolver.QUERY_ARG_SORT_DIRECTION to ContentResolver.QUERY_SORT_DIRECTION_DESCENDING,
            ContentResolver.QUERY_ARG_SQL_SELECTION to selection,
            ContentResolver.QUERY_ARG_SQL_SELECTION_ARGS to selectionArgs,
        )
        contentResolver.query(uriExternal, projection, bundle, null)
    } else {
        contentResolver.query(
            uriExternal,
            projection,
            selection,
            selectionArgs,
            "$sortedOrder DESC LIMIT $limit OFFSET $offset",
        )
    }

    fun getFolderList(): ArrayList<String> {
        val folderList = ArrayList<String>()
        folderList.add(0 , "전체")
        val uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI
        val projection = arrayOf(
            MediaStore.Images.Media.DATA,
        )
        val cursor = context.contentResolver.query(uri, projection, null, null, null)
        if (cursor != null) {
            while (cursor.moveToNext()) {
                val columnIndex = cursor.getColumnIndex(MediaStore.Images.Media.DATA)
                val filePath = cursor.getString(columnIndex)
                val folder = File(filePath).parent
                if (!folderList.contains(folder)) {
                    folderList.add(folder)
                }
            }
            cursor.close()
        }
        return folderList
    }
}