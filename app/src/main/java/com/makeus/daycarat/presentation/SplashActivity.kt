package com.makeus.daycarat.presentation

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.makeus.daycarat.R
import com.makeus.daycarat.presentation.login.AgreementActivity
import com.makeus.daycarat.presentation.login.IntroduceActivity
import com.makeus.daycarat.presentation.login.JoinActivity
import com.makeus.daycarat.presentation.login.LoginActivity
import com.makeus.daycarat.util.Constant
import com.makeus.daycarat.util.SharedPreferenceManager

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val splashScreen = installSplashScreen()
        setContentView(R.layout.activity_splash)
        SharedPreferenceManager.getInstance().setString(Constant.USER_ACCESS_TOKEN,"eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJtdXNocm9vbTEzMjRAbmF2ZXIuY29tIiwicm9sZSI6IlVTRVIiLCJpYXQiOjE3MDU0MTE5MTAsImV4cCI6MTcxNDA1MTkxMH0._qYlVOQKnYwQtUXetaVRftc0E4BJZ99-r9iu6kztdv4")
        if (SharedPreferenceManager.getInstance().getString(Constant.USER_ACCESS_TOKEN,"").isEmpty()){
            Intent(this, LoginActivity::class.java).apply {
                startActivity(this)
                finish()
            }
        }else{
            Intent(this, MainActivity::class.java).apply {
                startActivity(this)
                finish()
            }
        }


    }
}