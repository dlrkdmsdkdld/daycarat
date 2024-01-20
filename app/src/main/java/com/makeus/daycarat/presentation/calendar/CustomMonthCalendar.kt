package com.makeus.daycarat.presentation.calendar

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import android.graphics.Typeface
import android.os.Build
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.toBitmap
import kotlin.properties.Delegates
import com.haibin.calendarview.Calendar
import com.haibin.calendarview.MonthView
import com.makeus.daycarat.R
import com.makeus.daycarat.util.UiManager


class CustomMonthCalendar(context: Context) : MonthView(context) {
    //    private val mPointPaint = Paint()
    private val mycontext = context

    /**
     * 今天的背景色
     */
    private val mCurrentDayPaint = Paint()

    /**
     * 圆点半径
     */
    private var mPointRadius = 3f
    var mRadius by Delegates.notNull<Int>()

    init {
        mCurrentDayPaint.isAntiAlias = true
        mCurrentDayPaint.style = Paint.Style.STROKE
        mCurrentDayPaint.color = ContextCompat.getColor(context, R.color.main)
//        mPointPaint.isAntiAlias = true
//        mPointPaint.style = Paint.Style.FILL
//        mPointPaint.textAlign = Paint.Align.CENTER
//        mPointPaint.color = ContextCompat.getColor(context, R.color.purple_200)
    }

    /**
     * draw select calendar
     *
     * @param canvas    canvas
     * @param calendar  select calendar
     * @param x         calendar item x start point
     * @param y         calendar item y start point
     * @param hasScheme is calendar has scheme?
     * @return if return true will call onDrawScheme again
     *///캘린더뷰 셀 선택햇을때 실행되는함수
    override fun onDrawSelected(
        canvas: Canvas,
        calendar: Calendar,
        x: Int,
        y: Int,
        hasScheme: Boolean
    ): Boolean {

        mSelectedPaint.style = Paint.Style.FILL
        mSelectedPaint.color = ContextCompat.getColor(context, R.color.main)

//        val cx = x + (mItemWidth * 14 / 48) - 8
        val cx = x + mItemWidth / 2
//        val cy: Int = y + mItemHeight / 3 + 20
        val cy = mTextBaseLine / 2 + y.toFloat()  + 10
        val textcy =  mTextBaseLine / 2 + y.toFloat() + 25


        val radius: Int = Math.min(mItemWidth, mItemHeight) / 5 * 2
        canvas.drawCircle(cx.toFloat(), cy.toFloat(), radius.toFloat(), mSelectedPaint)

        canvas.drawText(
            calendar.day.toString(),
            cx.toFloat(),
            textcy,
            mCurDayTextPaint
        )
//        canvas.drawRoundRect(rectF, 16f, 16f, mSelectedPaint)
        return true
    }

    /**
     * draw scheme if calendar has scheme
     *
     * @param canvas   canvas
     * @param calendar calendar has scheme
     * @param x        calendar item x start point
     * @param y        calendar item y start point
     */ // 특정날짜에 색칠해줄때 자동실행되는함수
    val r = mycontext.resources

    //    val codeoneimage = r.getDrawable(R.drawable.ic_calendar_1,null).toBitmap()
//    val codetwoimage =r.getDrawable(R.drawable.ic_calendar_2,null).toBitmap()
//    val codethreeimage = r.getDrawable(R.drawable.ic_calendar_3,null).toBitmap()
//    val codefourimage = r.getDrawable(R.drawable.ic_calendar_4,null).toBitmap()
//    val codefiveimage = r.getDrawable(R.drawable.ic_calendar_5,null).toBitmap()
    var done: Boolean = false
    override fun onDrawScheme(canvas: Canvas, calendar: Calendar, x: Int, y: Int) {
//        val cx = x + (mItemWidth * 14 / 48) - 8
//        val cy: Int = y + mItemHeight / 3 + 20
//        val radius: Int = Math.min(mItemWidth, mItemHeight) / 5 * 2
//        canvas.drawCircle(cx.toFloat(), cy.toFloat(), radius.toFloat(), mSchemePaint)
        // println("$x $y scheme 실행됨")

    }

    override fun onPreviewHook() {
        super.onPreviewHook()
        mRadius = Math.min(mItemWidth, mItemHeight) / 5 * 2
    }

    /**
     * draw text
     *
     * @param canvas     canvas
     * @param calendar   calendar
     * @param x          calendar item x start point
     * @param y          calendar item y start point
     * @param hasScheme  is calendar has scheme?
     * @param isSelected is calendar selected?
     */

    override fun onDrawText(
        canvas: Canvas?,
        calendar: Calendar?,
        x: Int,
        y: Int,
        hasScheme: Boolean,
        isSelected: Boolean
    ) {
        val font: Typeface? = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            resources.getFont(R.font.pretendard_medium)
        } else {
            null
        }
        val cx = x + mItemWidth / 2
        val cy = mTextBaseLine / 2 + y.toFloat() + 25
        calendar?.let {
            canvas?.let { cit ->

                mSelectTextPaint.color = Color.WHITE
//                mSchemeTextPaint.color = Color.BLACK

                mCurDayTextPaint.color = context.getColor(R.color.gray_scale_900)
                mCurMonthTextPaint.color =  context.getColor(R.color.gray_scale_900)
                mOtherMonthTextPaint.color = context.getColor(R.color.gray_scale_400)

                val drawPaint = if (isSelected) mSelectTextPaint else {
                    if (it.isCurrentMonth) mCurMonthTextPaint else mOtherMonthTextPaint
                }
                drawPaint.isAntiAlias = true
                drawPaint.typeface = font
                drawPaint.textSize = UiManager.getPixel(16).toFloat()

                cit.drawText(
                    it.day.toString(),
                    cx.toFloat(),
                    cy.toFloat(),
                    drawPaint
                )

                // drawPaint.setTypeface(font)
                //val mTextBaseLine = mTextBaseLine /3*2
//                if (it.isCurrentDay) {
//                    drawPaint.color = mCurDayTextPaint.color
//                    cit.drawText(
//                        it.day.toString(),
//                        cx.toFloat(),
//                        mTextBaseLine / 2 + y.toFloat(),
//                        drawPaint
//                    )
//                } else if (it.isCurrentMonth && !hasScheme) {
//                    drawPaint.color = mCurMonthTextPaint.color
//                    cit.drawText(
//                        it.day.toString(),
//                        cx.toFloat(),
//                        mTextBaseLine / 2 + y.toFloat(),
//                        drawPaint
//                    )
//                } else if (it.isCurrentMonth && hasScheme) {
//                    cit.drawText(
//                        it.day.toString(),
//                        cx.toFloat(),
//                        mTextBaseLine / 2 + y.toFloat(),
//                        drawPaint
//                    )
//                }
                // cit.drawText(it.day.toString(), x.toFloat(), mTextBaseLine + y, drawPaint)
            }
        }
    }
}