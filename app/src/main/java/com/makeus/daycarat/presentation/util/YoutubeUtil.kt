package com.makeus.daycarat.presentation.util

import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.GranularRoundedCorners
import java.util.regex.Pattern


object YoutubeUtil {

    // 유튜브 섬네일 가져옴
    fun getThumbnail(url: String): String {
        val vId = getVideoId(url)
        return "https://img.youtube.com/vi/$vId/hqdefault.jpg"
    }

    fun getVideoId(url: String?): String {
        var vId = ""
        val pattern = Pattern.compile(
            "^.*(?:(?:youtu\\.be\\/|v\\/|vi\\/|u\\/\\w\\/|embed\\/)|(?:(?:watch)?\\?v(?:i)?=|\\&v(?:i)?=))([^#\\&\\?]*).*",
            Pattern.CASE_INSENSITIVE)
        val matcher = pattern.matcher(url ?: "")
        if (matcher.matches()) {
            vId = matcher.group(1) ?: ""
        }
        return vId
    }
    // 유튜브 주소인지 판별
    fun isYoutube(url: String?): Boolean {
        return getVideoId(url).isNotEmpty()
    }

    fun setImagThubnail(view:ImageView , url:String){
        Glide.with(view.context).load(getThumbnail(url)).transform(
            CenterCrop(), GranularRoundedCorners(
                UiManager.getPixel(16).toFloat(), UiManager.getPixel(16).toFloat(), 0f, 0f)
        ).into(view)
    }

    fun setImagThubnail(view:ImageView , drawableId:Int){
        Glide.with(view.context).load(drawableId).transform(
            CenterCrop(), GranularRoundedCorners(
                UiManager.getPixel(16).toFloat(), UiManager.getPixel(16).toFloat(), 0f, 0f)
        ).into(view)
    }



}