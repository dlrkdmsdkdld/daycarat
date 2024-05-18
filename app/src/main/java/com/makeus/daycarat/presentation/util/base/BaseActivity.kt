package com.makeus.daycarat.base

import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowInsetsControllerCompat
import androidx.viewbinding.ViewBinding
import com.makeus.daycarat.presentation.dialog.LoadingDialog

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

        setEnableFalseBottomState()

    }
    fun setEnableFalseBottomState(){
        if (Build.VERSION.SDK_INT in 19..20) {
            setWindowFlag(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, true)
        }
        if (Build.VERSION.SDK_INT >= 19) {
            window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
        }
        if (Build.VERSION.SDK_INT >= 21) {
            setWindowFlag(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, false)
            window.statusBarColor = Color.TRANSPARENT
        }
    }
    private fun setWindowFlag(bits: Int, on: Boolean) {
        val win = window
        val winParams = win.attributes
        if (on) {
            winParams.flags = winParams.flags or bits
        } else {
            winParams.flags = winParams.flags and bits.inv()
        }
        win.attributes = winParams
    }
    abstract fun initView()

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}