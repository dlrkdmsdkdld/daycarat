package com.makeus.daycarat.presentation.fragment

import android.widget.LinearLayout
import androidx.appcompat.widget.AppCompatButton
import com.makeus.daycarat.R
import com.makeus.daycarat.base.BaseFragment
import com.makeus.daycarat.databinding.FragmentJoinJobBinding
import com.makeus.daycarat.databinding.FragmentJoinNicknameBinding
import com.makeus.daycarat.databinding.ItemJoinJobBinding
import com.makeus.daycarat.util.UiManager


class JobFragment : BaseFragment<FragmentJoinJobBinding>(
    FragmentJoinJobBinding::inflate) {

    var buttonList = ArrayList<AppCompatButton>()
    override fun initView() {

        resources.getStringArray(R.array.des_datas).forEach {
            dynamicJobBtn(it)
        }

    }
    fun dynamicJobBtn(title : String){
        var btn = ItemJoinJobBinding.inflate(layoutInflater)
        btn.rootView.apply {
            text = title
            setOnClickListener {
                checkAllBtn(title)
            }
            var lp = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT , UiManager.getPixel(48))
            lp.setMargins(UiManager.getPixel(15),UiManager.getPixel(15),UiManager.getPixel(15),0)
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
