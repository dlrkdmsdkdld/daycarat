package com.makeus.daycarat.presentation

import android.content.Intent
import android.os.Bundle
import com.makeus.daycarat.base.BaseActivity
import com.makeus.daycarat.databinding.ActivityAgreementBinding
import com.makeus.daycarat.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AgreementActivity : BaseActivity<ActivityAgreementBinding>({ ActivityAgreementBinding.inflate(it)}) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initView()

    }
    fun initView(){
        binding.checkAll.setOnCheckedChangeListener { compoundButton, b ->
            if (b){
                binding.check1.isChecked = true
                binding.check2.isChecked = true
                binding.check3.isChecked = true
                enableBtn(true)
            }else{
                binding.check1.isChecked = false
                binding.check2.isChecked = false
                binding.check3.isChecked = false
                enableBtn(false)
            }
        }
        binding.check1.setOnCheckedChangeListener { compoundButton, b ->
            enableBtn(checkAll())
        }
        binding.check2.setOnCheckedChangeListener { compoundButton, b ->
            enableBtn(checkAll())
        }
        binding.check3.setOnCheckedChangeListener { compoundButton, b ->
            enableBtn(checkAll())
        }
        binding.nextBtn.setOnClickListener {
            Intent(this,)
        }

    }
    fun enableBtn(boolean: Boolean){
        binding.nextBtn.isEnabled = boolean
        binding.checkAll.isChecked = boolean
    }
    fun checkAll(): Boolean {
        return binding.check1.isChecked && binding.check2.isChecked  &&binding.check3.isChecked
    }
}