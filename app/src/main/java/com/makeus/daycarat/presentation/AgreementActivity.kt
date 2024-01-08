package com.makeus.daycarat.presentation

import android.os.Bundle
import com.makeus.daycarat.base.BaseActivity
import com.makeus.daycarat.databinding.ActivityAgreementBinding
import com.makeus.daycarat.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AgreementActivity : BaseActivity<ActivityAgreementBinding>({ ActivityAgreementBinding.inflate(it)}) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        binding.checkAll.set

    }
}