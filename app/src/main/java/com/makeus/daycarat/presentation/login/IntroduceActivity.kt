package com.makeus.daycarat.presentation.login

import android.os.Bundle
import androidx.core.view.WindowInsetsControllerCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import androidx.viewpager.widget.ViewPager
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.makeus.daycarat.base.BaseActivity
import com.makeus.daycarat.databinding.ActivityIntroduceBinding
import com.makeus.daycarat.databinding.ActivityMainBinding
import com.makeus.daycarat.presentation.fragment.IntroduceFragment
import com.makeus.daycarat.util.Extensions.navigationHeight
import com.makeus.daycarat.util.Extensions.statusBarHeight
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class IntroduceActivity : BaseActivity<ActivityIntroduceBinding>({ ActivityIntroduceBinding.inflate(it)}) {
    private lateinit var mPager: ViewPager2

    private val NUM_PAGES = 3
    override fun initView() {
        binding.fieldMain.setPadding(
            0,
            this.statusBarHeight(),
            0,
            this.navigationHeight()
        )
        WindowInsetsControllerCompat(window, window.decorView).isAppearanceLightStatusBars = true // 스테이터스 바 아이콘 검은색
        mPager = binding.pager
        val pagerAdapter = ScreenSlidePagerAdapter(this)
        mPager.adapter = pagerAdapter
        binding.indicator.setViewPager(mPager)
    }

    override fun onBackPressed() {
        if (mPager.currentItem == 0) {
            // If the user is currently looking at the first step, allow the system to handle the
            // Back button. This calls finish() on this activity and pops the back stack.
            super.onBackPressed()
        } else {
            // Otherwise, select the previous step.
            mPager.currentItem = mPager.currentItem - 1
        }
    }
    private inner class ScreenSlidePagerAdapter(fa: FragmentActivity) : FragmentStateAdapter(fa) {
        override fun getItemCount(): Int = NUM_PAGES

        override fun createFragment(position: Int): Fragment = IntroduceFragment(position)
    }
}