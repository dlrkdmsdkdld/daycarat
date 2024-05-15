//package com.makeus.daycarat.presentation.login
//
//import android.content.Intent
//import com.makeus.daycarat.base.BaseActivity
//import com.makeus.daycarat.databinding.ActivityAgreementBinding
//import dagger.hilt.android.AndroidEntryPoint
//
//@AndroidEntryPoint
//class AgreementActivity : BaseActivity<ActivityAgreementBinding>({ ActivityAgreementBinding.inflate(it)}) {
//
//    override fun initView(){
//        binding.checkAll.setOnCheckedChangeListener { compoundButton, b ->
//            if (b){
//                binding.check1.isChecked = true
//                binding.check2.isChecked = true
//                binding.check3.isChecked = true
//                enableBtn(true)
//            }else{
//                binding.check1.isChecked = false
//                binding.check2.isChecked = false
//                binding.check3.isChecked = false
//                enableBtn(false)
//            }
//        }
//        binding.check1.setOnCheckedChangeListener { compoundButton, b ->
//            enableBtn(checkAll())
//        }
//        binding.check2.setOnCheckedChangeListener { compoundButton, b ->
//            enableBtn(checkAll())
//        }
//        binding.check3.setOnCheckedChangeListener { compoundButton, b ->
//            enableBtn(checkAll())
//        }
//        binding.nextBtn.setOnClickListener {
//            Intent(this, IntroduceActivity::class.java).apply {
//                startActivity(this)
//            }
//        }
//
//    }
//    fun enableBtn(boolean: Boolean){
//        binding.nextBtn.isEnabled = boolean
//        binding.checkAll.isChecked = boolean
//    }
//    fun checkAll(): Boolean {
//        return binding.check1.isChecked && binding.check2.isChecked  &&binding.check3.isChecked
//    }
//}