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

        if (SharedPreferenceManager.getInstance().getString(Constant.USER_ACCESS_TOKEN,"").isEmpty()){
            Intent(this, LoginActivity::class.java).apply {
                startActivity(this)
                finish()
            }
        }else{
            Intent(this, JoinActivity::class.java).apply {
                startActivity(this)
                finish()
            }
        }


    }
}