package com.makeus.daycarat.presentation

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.view.View
import android.view.WindowManager
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.navOptions
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupActionBarWithNavController
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.messaging.FirebaseMessaging
import com.gun0912.tedpermission.PermissionListener
import com.gun0912.tedpermission.normal.TedPermission
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.common.model.ClientError
import com.kakao.sdk.common.model.ClientErrorCause
import com.kakao.sdk.user.UserApiClient
import com.makeus.daycarat.R
import com.makeus.daycarat.base.BaseActivity
import com.makeus.daycarat.databinding.ActivityMainBinding
import com.makeus.daycarat.presentation.viewmodel.FcmViewModel
import com.makeus.daycarat.presentation.viewmodel.HomeViewModel
import com.makeus.daycarat.presentation.viewmodel.MainViewmodel
import com.makeus.daycarat.util.Constant.TAG
import com.makeus.daycarat.util.Extensions.navigationHeight
import com.makeus.daycarat.util.Extensions.setStatusBarTransparent
import com.makeus.daycarat.util.Extensions.statusBarHeight
import com.makeus.daycarat.util.PermissionManager.checkNotiPermission
import com.makeus.daycarat.util.PermissionManager.requestNotiPermission
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding>({ ActivityMainBinding.inflate(it) }) {
    lateinit var navController: NavController
    private val mainViewModel by lazy {
        ViewModelProvider(this).get(MainViewmodel::class.java)
    }

    private val fcmViewmodel by lazy {
        ViewModelProvider(this).get(FcmViewModel::class.java)
    }

    override fun initView() {
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN)
        WindowInsetsControllerCompat(window, window.decorView).isAppearanceLightStatusBars =  true // 스테이터스 바 아이콘 검은색
        initNavigation()


        destinationListener()

        FirebaseMessaging.getInstance().token.addOnCompleteListener(OnCompleteListener { task ->
            if (!task.isSuccessful) {
                Log.w(TAG, "Fetching FCM registration token failed", task.exception)
                return@OnCompleteListener
            }
            val token = task.result
            uploadFcmToken(token)
        })

        if (checkNotiPermission(this)){ // 알림권한없으면 권한요청
            requestNotiPermission(this)
        }


    }

    fun uploadFcmToken(token:String){
        fcmViewmodel.updateFCMToken(token)
    }

    fun initNavigation(){
        //네비게이션들을 담는 호스트
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.myNavHost) as NavHostFragment

        //네비게이션 컨트롤러 가져옴
        navController = navHostFragment.navController
        //바텀네비게이션뷰와 네비게이션을 묶어준다
        NavigationUI.setupWithNavController(binding.bottomNav, navController)
        binding.btnCenter.setOnClickListener {
//            navController.popBackStack()
            var option = navOptions {
                launchSingleTop = true
                restoreState = true
//                popUpTo(navController.graph.findStartDestination().id) {
//                    saveState = true
//                }
            }
            navController.navigate(R.id.editEpisodeFragment, args = null, option, null)
        }

    }

    fun destinationListener() {
        navController.addOnDestinationChangedListener { _: NavController?, destination: NavDestination, _: Bundle? ->
            when (destination.id) {
                R.id.editEpisodeFragment, R.id.episodeDetailTypeFragment , R.id.episodeSeeContentFragment ,
                R.id.soaraFragment ,R.id.editSoaraFragment , R.id.completeSoaraFragment ,R.id.gemDetailFragment
                , R.id.gemContentFragment  , R.id.gemKeywordFragment-> {
                    binding.bottomNav.visibility = View.GONE
                }

                else -> {
                    binding.bottomNav.visibility = View.VISIBLE
                }
            }

        }
    }




}
