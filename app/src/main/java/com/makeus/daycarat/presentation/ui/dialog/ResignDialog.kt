package com.makeus.daycarat.presentation.dialog

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import com.makeus.daycarat.R
import com.makeus.daycarat.databinding.DialogResignBinding
import com.makeus.daycarat.presentation.util.Extensions.onThrottleClick

class ResignDialog(context: Context ,var isLogout:Boolean) : Dialog(context) {

    lateinit var binding:DialogResignBinding

    var onclick:(() -> Unit )? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DialogResignBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setCancelable(false)
        window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
//        val param = WindowManager.LayoutParams()
//        param.flags = WindowManager.LayoutParams.FLAG_DIM_BEHIND
//        param.dimAmount = 0.8f
        window!!.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)



        if (isLogout){
            binding.btnResign.visibility = View.GONE
            binding.textTitle.text = "로그아웃 하시겠습니까?"
        }else{
            binding.btnLogout.visibility = View.GONE
            binding.textTitle.text = "정말 탈퇴하시겠습니까?"
        }

        binding.btnResign.onThrottleClick {
            onclick?.invoke()
            dismiss()
        }
        binding.btnLogout.onThrottleClick {
            onclick?.invoke()
            dismiss()
        }
        binding.btnCancel.onThrottleClick {
            dismiss()
        }
    }
}