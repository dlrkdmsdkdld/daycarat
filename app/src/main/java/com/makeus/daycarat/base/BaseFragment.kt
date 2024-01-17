package com.makeus.daycarat.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.viewbinding.ViewBinding
import com.makeus.daycarat.databinding.ActivityMainBinding
import com.makeus.daycarat.presentation.dialog.LoadingDialog
import com.makeus.daycarat.util.Extensions.repeatOnStarted
import kotlinx.coroutines.CoroutineScope

typealias Inflate<T> = (LayoutInflater, ViewGroup?, Boolean) -> T

abstract class BaseFragment<VB: ViewBinding>(
    private val inflate: Inflate<VB>
) : Fragment() {

    private var _binding: VB? = null
    val binding get() = _binding!!
    lateinit var loadingDialog :LoadingDialog

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = inflate.invoke(inflater, container, false)
        loadingDialog = LoadingDialog(binding.root.context)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        // 주의해서 사용 :: 안드로이드에서 탭 변경해서 fragment 변경시 기존
    // fragment들은 ondestory를 타지 않고 오직 onDestroyView만 타기때문에 메모리 누수 주위


    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
    }
    abstract fun initView()
}

//example
//class HomeFragment() : BaseFragment<ActivityMainBinding>(ActivityMainBinding::inflate) {
//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
//
//
//        binding.homeText.text = "Hello view binding"
//    }
//}