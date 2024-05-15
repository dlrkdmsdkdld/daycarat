package com.makeus.daycarat.presentation.login

import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.core.view.WindowInsetsControllerCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.makeus.daycarat.R
import com.makeus.daycarat.base.BaseFragment
import com.makeus.daycarat.databinding.FragmentJoinBinding
import com.makeus.daycarat.presentation.MainActivity
import com.makeus.daycarat.presentation.dialog.LoadingDialog
import com.makeus.daycarat.presentation.fragment.WellcomeFragment
import com.makeus.daycarat.presentation.fragment.login.AdvantageFragment
import com.makeus.daycarat.presentation.fragment.login.JobFragment
import com.makeus.daycarat.presentation.fragment.login.NicknameFragment
import com.makeus.daycarat.presentation.viewmodel.UserDataViewmodel
import com.makeus.daycarat.util.Constant
import com.makeus.daycarat.util.Extensions.navigationHeight
import com.makeus.daycarat.util.Extensions.repeatOnStarted
import com.makeus.daycarat.util.Extensions.statusBarHeight
import com.makeus.daycarat.util.UiEvent
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class JoinFragment() : BaseFragment<FragmentJoinBinding>(
    FragmentJoinBinding::inflate
) {
    private lateinit var mPager: ViewPager2
    private val viewModel by lazy {
        ViewModelProvider(this).get(UserDataViewmodel::class.java)
    }

    fun provideSharedViewModel(): UserDataViewmodel {
        return viewModel
    }

    private val NUM_PAGES = 4

    override fun initView() {
        mPager = binding.pager
        val pagerAdapter = ScreenSlidePagerAdapter(childFragmentManager , lifecycle)
        mPager.adapter = pagerAdapter
        mPager.isUserInputEnabled = false

        mPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
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
        binding.btnBack.setOnClickListener {
            mPager.currentItem = mPager.currentItem - 1
        }

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
            viewModel.flowEvent.collect { event ->
                when (event) {
                    is UiEvent.LoadingEvent -> {
                        (activity as MainActivity).loadingDialog.show()
                    }

                    is UiEvent.SuccessEvent -> {
                        (activity as MainActivity).loadingDialog.dismiss()
                        mPager.currentItem += 1
                    }
                    is UiEvent.FailEvent ->{
                        Toast.makeText(requireContext(), "${event.message}!", Toast.LENGTH_SHORT).show()
                        (activity as MainActivity).loadingDialog.dismiss()
                    }
                    else -> {
                        Toast.makeText(requireContext() , "네트워크등의 오류로 데이터 전송 싪패!", Toast.LENGTH_SHORT).show()
                        (activity as MainActivity).loadingDialog.dismiss()
                    }

                }
            }

        }

    }

    override fun initStatusBar() {
        binding.fieldMain.setPadding(
            0,
            requireActivity().statusBarHeight(),
            0,
            requireActivity().navigationHeight()
        )
        WindowInsetsControllerCompat(requireActivity().window, requireActivity().window.decorView).isAppearanceLightStatusBars = true // 스테이터스 바 아이콘 검은색

    }

    private inner class ScreenSlidePagerAdapter(fa: FragmentManager, lifecycle: Lifecycle): FragmentStateAdapter(fa,  lifecycle) {
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
    fun setIndicator(position: Int) {
        when (position) {
            0 -> {
                binding.apply {
                    indicator1.setBackgroundColor(resources.getColor(R.color.main, null))
                    indicator2.setBackgroundColor(resources.getColor(R.color.gray_scale_300, null))
                    indicator3.setBackgroundColor(resources.getColor(R.color.gray_scale_300, null))
                    binding.btnBack.visibility = View.GONE
                }
            }

            1 -> {
                binding.apply {
                    indicator1.setBackgroundColor(resources.getColor(R.color.gray_scale_300, null))
                    indicator2.setBackgroundColor(resources.getColor(R.color.main, null))
                    indicator3.setBackgroundColor(resources.getColor(R.color.gray_scale_300, null))
                    binding.btnBack.visibility = View.VISIBLE
                }
            }

            2 -> {
                binding.apply {
                    indicator1.setBackgroundColor(resources.getColor(R.color.gray_scale_300, null))
                    indicator2.setBackgroundColor(resources.getColor(R.color.gray_scale_300, null))
                    indicator3.setBackgroundColor(resources.getColor(R.color.main, null))
                    binding.btnBack.visibility = View.VISIBLE
                }
            }
            3 -> {
                binding.apply {
                    fieldIndicator.visibility = View.GONE
                    binding.btnBack.visibility = View.GONE
                    nextBtn.visibility = View.GONE
                }
            }

        }
    }

}