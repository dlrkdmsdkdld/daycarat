package com.makeus.daycarat.presentation.util

import android.content.res.Resources
import android.text.SpannableString
import android.text.Spanned
import android.text.style.ForegroundColorSpan
import android.util.TypedValue
import android.view.LayoutInflater
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.constraintlayout.widget.ConstraintLayout
import com.makeus.daycarat.presentation.DayCaratApplication
import com.makeus.daycarat.R
import com.makeus.daycarat.data.data.EpisodeContent
import com.makeus.daycarat.databinding.LayoutEpisodeDetailContentBinding

object UiManager {
    fun getPixel(dp: Int): Int {
//        Resources r = ((BitApplication)BitApplication.getInstance()).getLocalizedResources();
        val r: Resources = DayCaratApplication.mAppContext!!.getResources()
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp.toFloat(), r.displayMetrics)
            .toInt()
    }

    fun setSpan(color:Int ,startPoint:Int , endPoint:Int , ment:String ): SpannableString {
        var span = SpannableString(ment)
        span.setSpan( ForegroundColorSpan(color) , startPoint , endPoint , Spanned.SPAN_EXCLUSIVE_EXCLUSIVE )
        return span
    }


    fun inflateDetailContent(data: EpisodeContent, layoutInflater:LayoutInflater): ConstraintLayout {
        var inflating = LayoutEpisodeDetailContentBinding.inflate(layoutInflater)
        inflating.textTitle.text = data.episodeContentType
        inflating.textDes.text = data.content
        return inflating.root

    }

    fun setGemImage(keyword:String, tagetImageView: ImageView){
        when(keyword){
            "커뮤니케이션" -> tagetImageView.setImageResource(R.drawable.bg_communication)
            "문제 해결" ->tagetImageView.setImageResource(R.drawable.bg_resolve)
            "창의성" ->tagetImageView.setImageResource(R.drawable.bg_creativity)
            "도전 정신" ->tagetImageView.setImageResource(R.drawable.bg_challenge)
            "전문성" ->tagetImageView.setImageResource(R.drawable.bg_profession)
            "실행력" ->tagetImageView.setImageResource(R.drawable.bg_excutive)
        }
    }

    fun setGemDes(keyword:String,targetTextView:TextView){
        when(keyword){
            "커뮤니케이션" -> targetTextView.text = "다른 사람들과 소통방식에 있어 어떤 강점을 발휘했는지 적어보세요"
            "문제 해결" ->targetTextView.text = "다른 사람들과 소통방식에 있어 어떤 강점을 발휘했는지 적어보세요"
            "창의성" ->targetTextView.text = "다른 사람들과 소통방식에 있어 어떤 강점을 발휘했는지 적어보세요"
            "도전 정신" -> targetTextView.text = "다른 사람들과 소통방식에 있어 어떤 강점을 발휘했는지 적어보세요"
            "전문성" -> targetTextView.text = "다른 사람들과 소통방식에 있어 어떤 강점을 발휘했는지 적어보세요"
            "실행력" -> targetTextView.text = "다른 사람들과 소통방식에 있어 어떤 강점을 발휘했는지 적어보세요"
        }
    }

    fun setGemCardBgColor(keyword:String, cardview: CardView){
        when(keyword){
            "커뮤니케이션" -> cardview.setCardBackgroundColor(cardview.context.getColor(R.color.sub_blue_50 ))
            "문제 해결" ->cardview.setCardBackgroundColor(cardview.context.getColor(R.color.sub_resolve_50 ))
            "창의성" ->cardview.setCardBackgroundColor(cardview.context.getColor(R.color.sub_creative_50 ))
            "도전 정신" ->cardview.setCardBackgroundColor(cardview.context.getColor(R.color.sub_chanllenge_50 ))
            "전문성" ->cardview.setCardBackgroundColor(cardview.context.getColor(R.color.sub_profession_50 ))
            "실행력" ->cardview.setCardBackgroundColor(cardview.context.getColor(R.color.sub_red_50 ))
        }
    }


}

