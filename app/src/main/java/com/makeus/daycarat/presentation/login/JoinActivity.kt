package com.makeus.daycarat.presentation.login

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import androidx.viewpager2.widget.ViewPager2.OnPageChangeCallback
import com.google.android.material.tabs.TabLayoutMediator
import com.makeus.daycarat.base.BaseActivity
import com.makeus.daycarat.databinding.ActivityIntroduceBinding
import com.makeus.daycarat.databinding.ActivityJoinBinding
import com.makeus.daycarat.presentation.fragment.AdvantageFragment
import com.makeus.daycarat.presentation.fragment.IntroduceFragment
import com.makeus.daycarat.presentation.fragment.JobFragment
import com.makeus.daycarat.presentation.fragment.NicknameFragment
import com.makeus.daycarat.util.UiManager
import dagger.hilt.android.AndroidEntryPoint
import okhttp3.internal.notify

@AndroidEntryPoint
class JoinActivity : BaseActivity<ActivityJoinBinding>({ ActivityJoinBinding.inflate(it)}) {
    private lateinit var mPager: ViewPager2

    private val NUM_PAGES = 3
    override fun initView() {
        mPager = binding.pager
        val pagerAdapter = ScreenSlidePagerAdapter(this)
        mPager.adapter = pagerAdapter
        mPager.isUserInputEnabled = false

//        binding.indicator.setViewPager(mPager , 3)

        TabLayoutMediator(binding.indicator, mPager) { tab, position ->
//            tab.text = "OBJECT ${(position + 1)}"
        }.attach()

//        binding.indicator.apply {
//            setSliderHeight(UiManager.getPixel(2).toFloat())
//            setPageSize(NUM_PAGES)
//            notifyDataChanged()
//        }
//        mPager.registerOnPageChangeCallback(object : OnPageChangeCallback() {
//            override fun onPageScrolled(
//                position: Int,
//                positionOffset: Float,
//                positionOffsetPixels: Int
//            ) {
//                super.onPageScrolled(position, positionOffset, positionOffsetPixels)
//                binding.indicator.onPageScrolled(position,positionOffset,positionOffsetPixels)
//            }
//
//            override fun onPageSelected(position: Int) {
//                super.onPageSelected(position)
//                binding.indicator.onPageSelected(position)
//
//            }
//        })

//        binding.indicator.setViewPager(mPager)
        binding.nextBtn.setOnClickListener {
            mPager.currentItem = mPager.currentItem + 1
            binding.nextBtn.isEnabled = false
        }
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

        override fun createFragment(position: Int): Fragment = when(position){
             0 -> NicknameFragment()
            1 -> JobFragment()
            2 -> AdvantageFragment()
            else -> NicknameFragment()
        }
    }
    fun enableNextBtn(b:Boolean){
        binding.nextBtn.isEnabled = b
    }
}