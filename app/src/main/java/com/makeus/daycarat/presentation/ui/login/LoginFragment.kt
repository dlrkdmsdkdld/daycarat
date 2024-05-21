package com.makeus.daycarat.presentation.login

import android.content.Intent
import android.util.Log
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.common.model.ClientError
import com.kakao.sdk.common.model.ClientErrorCause
import com.kakao.sdk.user.UserApiClient
import com.makeus.daycarat.R
import com.makeus.daycarat.presentation.util.base.BaseFragment
import com.makeus.daycarat.databinding.FragmentLoginBinding
import com.makeus.daycarat.databinding.FragmentWellcomeBinding
import com.makeus.daycarat.presentation.MainActivity
import com.makeus.daycarat.presentation.viewmodel.AuthViewmodel
import com.makeus.daycarat.presentation.util.Constant
import com.makeus.daycarat.presentation.util.Extensions.navigationHeight
import com.makeus.daycarat.presentation.util.Extensions.repeatOnStarted
import com.makeus.daycarat.presentation.util.Extensions.setStatusBarOrigin
import com.makeus.daycarat.presentation.util.Extensions.setStatusBarTransparent
import com.makeus.daycarat.presentation.util.Extensions.statusBarHeight
import com.makeus.daycarat.presentation.util.SharedPreferenceManager
import com.makeus.daycarat.presentation.util.UiEvent
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginFragment() : BaseFragment<FragmentLoginBinding>(
    FragmentLoginBinding::inflate) {

    private val viewModel by lazy {
        ViewModelProvider(this).get(AuthViewmodel::class.java)
    }


    override fun initView() {
        requireActivity().setStatusBarTransparent()

        //자동 로그인
        if (!SharedPreferenceManager.getInstance().getString(Constant.USER_ACCESS_TOKEN , "").isEmpty()){
            findNavController().navigate(R.id.action_loginFragment_to_homeFragment)
        }

        setKakaoModule()
        
        repeatOnStarted {
            viewModel.flowEvent.collect { event ->
                when(event){
                    is UiEvent.LoadingEvent ->{
                        (activity as MainActivity).loadingDialog.show()
                    } is UiEvent.AlreadyUserEvent ->{
                    (activity as MainActivity).loadingDialog.dismiss()
                    findNavController().navigate(R.id.action_loginFragment_to_homeFragment)
//                    findNavController().navigate(R.id.action_loginFragment_to_introduceFragment2)

                } is UiEvent.NewUserEvent ->{
                    findNavController().navigate(R.id.action_loginFragment_to_introduceFragment2)

                }
                    else ->{
                        (activity as MainActivity).loadingDialog.dismiss()
                    }

                }

            }
        }

    }
    fun setKakaoModule(){
        // 카카오 로그인
        // 카카오계정으로 로그인 공통 callback 구성
        // 카카오톡으로 로그인 할 수 없어 카카오계정으로 로그인할 경우 사용됨
        val callback: (OAuthToken?, Throwable?) -> Unit = { token, error ->
            if (error != null) {
                Log.e(Constant.TAG, "카카오계정으로 로그인 실패", error)
            } else if (token != null) {
                Log.i(Constant.TAG, "카카오계정으로 로그인 성공 ${token.accessToken}")
                viewModel.getTokenWithKakaoToken(token.accessToken)
            }
        }
        binding.btnKakao.setOnClickListener {
//            UserApiClient.instance.loginWithKakaoAccount(this, callback = callback)
            // 카카오톡이 설치되어 있으면 카카오톡으로 로그인, 아니면 카카오계정으로 로그인
            if (UserApiClient.instance.isKakaoTalkLoginAvailable(requireContext())) {
                UserApiClient.instance.loginWithKakaoTalk(requireContext()) { token, error ->
                    if (error != null) {
                        Log.e(Constant.TAG, "카카오톡으로 로그인 실패", error)

                        // 사용자가 카카오톡 설치 후 디바이스 권한 요청 화면에서 로그인을 취소한 경우,
                        // 의도적인 로그인 취소로 보고 카카오계정으로 로그인 시도 없이 로그인 취소로 처리 (예: 뒤로 가기)
                        if (error is ClientError && error.reason == ClientErrorCause.Cancelled) {
                            return@loginWithKakaoTalk
                        }

                        // 카카오톡에 연결된 카카오계정이 없는 경우, 카카오계정으로 로그인 시도
                        UserApiClient.instance.loginWithKakaoAccount(requireContext(), callback = callback)
                    } else if (token != null) {
                        viewModel.getTokenWithKakaoToken(token.accessToken)
                        Log.i(Constant.TAG, "카카오톡으로 로그인 성공 ${token.accessToken}")

                    }
                }
            } else {
                UserApiClient.instance.loginWithKakaoAccount(requireContext(), callback = callback)
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
    }

    override fun onDestroyView() {
        requireActivity().setStatusBarOrigin()
        super.onDestroyView()

    }

}