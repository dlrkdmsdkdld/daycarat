package com.makeus.daycarat.presentation.login

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.lifecycle.ViewModelProvider
import com.kakao.sdk.auth.AuthApiClient
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.common.model.ClientError
import com.kakao.sdk.common.model.ClientErrorCause
import com.kakao.sdk.common.model.KakaoSdkError
import com.kakao.sdk.user.UserApiClient
import com.makeus.daycarat.base.BaseActivity
import com.makeus.daycarat.databinding.ActivityLoginBinding
import com.makeus.daycarat.databinding.ActivityMainBinding
import com.makeus.daycarat.presentation.viewmodel.AuthViewmodel
import com.makeus.daycarat.util.Constant
import com.makeus.daycarat.util.SharedPreferenceManager
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginActivity : BaseActivity<ActivityLoginBinding>({ ActivityLoginBinding.inflate(it)}) {
    private val viewModel by lazy {
        ViewModelProvider(this).get(AuthViewmodel::class.java)
    }

    override fun initView() {
        // 카카오 로그인
        // 카카오계정으로 로그인 공통 callback 구성
        // 카카오톡으로 로그인 할 수 없어 카카오계정으로 로그인할 경우 사용됨
        val callback: (OAuthToken?, Throwable?) -> Unit = { token, error ->
            if (error != null) {
                Log.e(Constant.TAG, "카카오계정으로 로그인 실패", error)
            } else if (token != null) {
                Log.i(Constant.TAG, "카카오계정으로 로그인 성공 ${token.accessToken}")
                nextStep()
            }
        }


        binding.btnKakao.setOnClickListener{
            // 카카오톡이 설치되어 있으면 카카오톡으로 로그인, 아니면 카카오계정으로 로그인
            if (UserApiClient.instance.isKakaoTalkLoginAvailable(this)) {
                UserApiClient.instance.loginWithKakaoTalk(this) { token, error ->
                    if (error != null) {
                        Log.e(Constant.TAG, "카카오톡으로 로그인 실패", error)

                        // 사용자가 카카오톡 설치 후 디바이스 권한 요청 화면에서 로그인을 취소한 경우,
                        // 의도적인 로그인 취소로 보고 카카오계정으로 로그인 시도 없이 로그인 취소로 처리 (예: 뒤로 가기)
                        if (error is ClientError && error.reason == ClientErrorCause.Cancelled) {
                            return@loginWithKakaoTalk
                        }

                        // 카카오톡에 연결된 카카오계정이 없는 경우, 카카오계정으로 로그인 시도
                        UserApiClient.instance.loginWithKakaoAccount(this, callback = callback)
                    } else if (token != null) {
                        nextStep()
//                        checkLoginToken()
                        //todo 동의 화면으로 보내기 + token 저장
//                        SharedPreferenceManager.getInstance().setString("USER_KAKAO_TOKEN",${token.scopes})
                        viewModel.getTokenWithKakaoToken(token.accessToken)

                        Log.i(Constant.TAG, "카카오톡으로 로그인 성공 ${token.accessToken}")

                    }
                }
            } else {
                UserApiClient.instance.loginWithKakaoAccount(this, callback = callback)
            }
        }
    }
    fun nextStep(){
        Intent(this , IntroduceActivity::class.java).apply {
            startActivity(this)
        }
    }
//    fun checkLoginToken(){
//        if (AuthApiClient.instance.hasToken()){
//            UserApiClient.instance.accessTokenInfo { _, error ->
//                if (error != null) {
//                    if (error is KakaoSdkError && error.isInvalidTokenError()) {
//                        //로그인 필요
//                    }
//                    else {
//                        //기타 에러
//                    }
//                }
//                else {
//                    //토큰 유효성 체크 성공(필요 시 토큰 갱신됨)
//                    UserApiClient.instance.me { user, error ->
//                        if (error != null) {
//                            Log.e(Constant.TAG, "사용자 정보 요청 실패 $error")
//                        } else if (user != null) {
//                            Log.e(Constant.TAG, "사용자 정보 요청 성공 : ${user.kakaoAccount?.email}")
//
//                        }
//                    }
//                }
//            }
//        }
//    }
}