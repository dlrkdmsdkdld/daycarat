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

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val splashScreen = installSplashScreen()
        setContentView(R.layout.activity_splash)

        Intent(this, JoinActivity::class.java).apply {
            startActivity(this)
            finish()
        }
    }
}