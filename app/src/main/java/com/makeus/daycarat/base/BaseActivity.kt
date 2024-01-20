package com.makeus.daycarat.base

import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import androidx.viewbinding.ViewBinding
import com.makeus.daycarat.presentation.dialog.LoadingDialog
import com.makeus.daycarat.util.Extensions.setStatusBarOrigin
import com.makeus.daycarat.util.Extensions.setStatusBarTransparent

abstract class BaseActivity<B: ViewBinding>(
    val bindingFactory: (LayoutInflater) -> B
): AppCompatActivity() {

    private var _binding: B? = null
    val binding get() = _binding!!
    lateinit var loadingDialog :LoadingDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = bindingFactory(layoutInflater)
        setContentView(binding.root)
        this.loadingDialog = LoadingDialog(binding.root.context)
        initView()
    }
    abstract fun initView()

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}