package com.makeus.daycarat.util

import android.content.res.Resources
import android.text.Layout
import android.util.TypedValue
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import com.makeus.daycarat.DayCaratApplication
import com.makeus.daycarat.data.EpisodeContent
import com.makeus.daycarat.databinding.LayoutEpisodeDetailContentBinding

object UiManager {
    fun getPixel(dp: Int): Int {
//        Resources r = ((BitApplication)BitApplication.getInstance()).getLocalizedResources();
        val r: Resources = DayCaratApplication.mAppContext!!.getResources()
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp.toFloat(), r.displayMetrics)
            .toInt()
    }


    fun inflateDetailContent(data: EpisodeContent , layoutInflater:LayoutInflater): ConstraintLayout {
        var inflating = LayoutEpisodeDetailContentBinding.inflate(layoutInflater)
        inflating.textTitle.text = data.episodeContentType
        inflating.textDes.text = data.content
        return inflating.root

    }
}

