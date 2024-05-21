package com.makeus.daycarat.presentation.fragment.login

import android.widget.LinearLayout
import androidx.appcompat.widget.AppCompatButton
import androidx.fragment.app.activityViewModels
import com.makeus.daycarat.R
import com.makeus.daycarat.presentation.util.base.BaseFragment
import com.makeus.daycarat.databinding.FragmentJoinJobBinding
import com.makeus.daycarat.databinding.ItemJoinJobBinding
import com.makeus.daycarat.presentation.login.JoinFragment
import com.makeus.daycarat.presentation.viewmodel.UserDataViewmodel
import com.makeus.daycarat.presentation.util.UiManager


class JobFragment : BaseFragment<FragmentJoinJobBinding>(
    FragmentJoinJobBinding::inflate) {

    val viewModel by lazy { (parentFragment as JoinFragment).provideSharedViewModel() }

    var buttonList = ArrayList<AppCompatButton>()
    override fun initView() {

        resources.getStringArray(R.array.des_datas).forEach {
            dynamicJobBtn(it)
        }


    }

    override fun initStatusBar() {

    }

    override fun onResume() {
        super.onResume()
        if(viewModel.userData.value.jobTitle?.isNotEmpty() == true){
            buttonList.forEach {
                if(it.text.equals(viewModel.userData.value.jobTitle)){
                    (parentFragment as JoinFragment).enableNextBtn(true)
                    it.isSelected = true
                }else{
                    it.isSelected = false
                }
            }

        }
    }
    fun dynamicJobBtn(title : String){
        var btn = ItemJoinJobBinding.inflate(layoutInflater)
        btn.rootView.apply {
            text = title
            setOnClickListener {
                (parentFragment as JoinFragment).enableNextBtn(true)
                viewModel.userData.value.jobTitle = title
                checkAllBtn(title)
            }
            var lp = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT , UiManager.getPixel(48))
            lp.setMargins(UiManager.getPixel(16), UiManager.getPixel(16), UiManager.getPixel(16),0)
            layoutParams = lp

        }


        binding.fieldBtn.addView(btn.rootView)
        buttonList.add(btn.rootView)
    }

    fun checkAllBtn(title : String){
        buttonList.forEachIndexed { index, btn ->
            btn.isSelected = btn.text.equals(title)
        }
    }

}
