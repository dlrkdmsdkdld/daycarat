package com.makeus.daycarat.presentation.login

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.makeus.daycarat.base.BaseFragment
import com.makeus.daycarat.databinding.FragmentIntroduceBinding
import com.makeus.daycarat.presentation.fragment.login.SubIntroduceFragment
import com.makeus.daycarat.util.Extensions.navigationHeight
import com.makeus.daycarat.util.Extensions.statusBarHeight
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class IntroduceFragment() : BaseFragment<FragmentIntroduceBinding>(
    FragmentIntroduceBinding::inflate
) {

    private lateinit var mPager: ViewPager2

    private val NUM_PAGES = 3

    override fun initView() {
        mPager = binding.pager
        val pagerAdapter = ScreenSlidePagerAdapter(requireActivity())
        mPager.adapter = pagerAdapter
        binding.indicator.setViewPager(mPager)
    }

    override fun initStatusBar() {
        binding.fieldMain.setPadding(
            0,
            requireActivity().statusBarHeight(),
            0,
            requireActivity().navigationHeight()
        )
    }


    private inner class ScreenSlidePagerAdapter(fa: FragmentActivity) : FragmentStateAdapter(fa) {
        override fun getItemCount(): Int = NUM_PAGES

        override fun createFragment(position: Int): Fragment = SubIntroduceFragment(position)
    }

}