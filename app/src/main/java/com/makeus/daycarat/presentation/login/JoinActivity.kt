package com.makeus.daycarat.presentation.login

import android.content.Intent
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import androidx.viewpager2.widget.ViewPager2.OnPageChangeCallback
import com.google.android.material.tabs.TabLayoutMediator
import com.makeus.daycarat.R
import com.makeus.daycarat.base.BaseActivity
import com.makeus.daycarat.databinding.ActivityIntroduceBinding
import com.makeus.daycarat.databinding.ActivityJoinBinding
import com.makeus.daycarat.presentation.MainActivity
import com.makeus.daycarat.presentation.dialog.LoadingDialog
import com.makeus.daycarat.presentation.fragment.AdvantageFragment
import com.makeus.daycarat.presentation.fragment.IntroduceFragment
import com.makeus.daycarat.presentation.fragment.JobFragment
import com.makeus.daycarat.presentation.fragment.NicknameFragment
import com.makeus.daycarat.presentation.fragment.WellcomeFragment
import com.makeus.daycarat.presentation.viewmodel.AuthViewmodel
import com.makeus.daycarat.presentation.viewmodel.UserDataViewmodel
import com.makeus.daycarat.util.Constant
import com.makeus.daycarat.util.Extensions.repeatOnStarted
import com.makeus.daycarat.util.UiManager
import dagger.hilt.android.AndroidEntryPoint
import okhttp3.internal.notify

@AndroidEntryPoint
class JoinActivity : BaseActivity<ActivityJoinBinding>({ ActivityJoinBinding.inflate(it) }) {
    private lateinit var mPager: ViewPager2
    private val viewModel by lazy {
        ViewModelProvider(this).get(UserDataViewmodel::class.java)
    }

    private val NUM_PAGES =4
    override fun initView() {
        mPager = binding.pager
        val pagerAdapter = ScreenSlidePagerAdapter(this)
        mPager.adapter = pagerAdapter
        mPager.isUserInputEnabled = false

//        binding.indicator.setViewPager(mPager , 3)

//        TabLayoutMediator(binding.indicator, mPager) { tab, position ->
////            tab.text = "OBJECT ${(position + 1)}"
//        }.attach()

//        binding.indicator.apply {
//            setSliderHeight(UiManager.getPixel(2).toFloat())
//            setPageSize(NUM_PAGES)
//            notifyDataChanged()
//        }
        mPager.registerOnPageChangeCallback(object : OnPageChangeCallback() {
            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
                super.onPageScrolled(position, positionOffset, positionOffsetPixels)
            }

            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                setIndicator(mPager.currentItem)

            }
        })

//        binding.indicator.setViewPager(mPager)
        binding.nextBtn.setOnClickListener {
            binding.nextBtn.isEnabled = false
//            setIndicator(mPager.currentItem + 1)
            when (mPager.currentItem) {
                0 -> {

                }

                1 -> {

                }

                2 -> {
                    viewModel.updateUserInfo()
                    Log.d(
                        Constant.TAG,
                        "nickname ${viewModel.userData.value.nickname} strength ${viewModel.userData.value.strength} jobTitle ${viewModel.userData.value.jobTitle}"
                    )
                }
            }
            if (mPager.currentItem !=2) mPager.currentItem = mPager.currentItem + 1


        }
        repeatOnStarted {
            var dialog = LoadingDialog(this@JoinActivity)
            viewModel.flowEvent.collect { event ->
                when (event) {
                    is AuthViewmodel.UiEvent.LoadingEvent -> {
                        dialog.show()
                    }

                    is AuthViewmodel.UiEvent.SuccessEvent -> {
                        dialog.dismiss()
                        mPager.currentItem += 1
                    }
                    is AuthViewmodel.UiEvent.FailEvent ->{
                        Toast.makeText(this@JoinActivity , "${event.message}!",Toast.LENGTH_SHORT).show()
                        dialog.dismiss()
                    }
                    else -> {
                        Toast.makeText(this@JoinActivity , "네트워크등의 오류로 데이터 전송 싪패!",Toast.LENGTH_SHORT).show()
                        dialog.dismiss()
                    }

                }
            }

        }

    }

    fun setIndicator(position: Int) {
        when (position) {
            0 -> {
                binding.apply {
                    indicator1.setBackgroundColor(resources.getColor(R.color.main, null))
                    indicator2.setBackgroundColor(resources.getColor(R.color.gray_scale_300, null))
                    indicator3.setBackgroundColor(resources.getColor(R.color.gray_scale_300, null))
                }
            }

            1 -> {
                binding.apply {
                    indicator1.setBackgroundColor(resources.getColor(R.color.gray_scale_300, null))
                    indicator2.setBackgroundColor(resources.getColor(R.color.main, null))
                    indicator3.setBackgroundColor(resources.getColor(R.color.gray_scale_300, null))
                }
            }

            2 -> {
                binding.apply {
                    indicator1.setBackgroundColor(resources.getColor(R.color.gray_scale_300, null))
                    indicator2.setBackgroundColor(resources.getColor(R.color.gray_scale_300, null))
                    indicator3.setBackgroundColor(resources.getColor(R.color.main, null))
                }
            }
            3 -> {
                binding.apply {
                    fieldIndicator.visibility = View.GONE
                    nextBtn.visibility = View.GONE
                }
            }

        }
    }

//    override fun onBackPressed() {
//        if (mPager.currentItem == 0) {
//            // If the user is currently looking at the first step, allow the system to handle the
//            // Back button. This calls finish() on this activity and pops the back stack.
//            super.onBackPressed()
//        } else {
//            // Otherwise, select the previous step.
//            mPager.currentItem = mPager.currentItem - 1
//        }
//    }

    private inner class ScreenSlidePagerAdapter(fa: FragmentActivity) : FragmentStateAdapter(fa) {
        override fun getItemCount(): Int = NUM_PAGES

        override fun createFragment(position: Int): Fragment = when (position) {
            0 -> NicknameFragment()
            1 -> JobFragment()
            2 -> AdvantageFragment()
            3 -> WellcomeFragment()
            else -> NicknameFragment()
        }
    }

    fun enableNextBtn(b: Boolean) {
        binding.nextBtn.isEnabled = b
    }
}