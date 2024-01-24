package com.makeus.daycarat.presentation

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.view.WindowInsetsControllerCompat
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.navOptions
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupActionBarWithNavController
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.common.model.ClientError
import com.kakao.sdk.common.model.ClientErrorCause
import com.kakao.sdk.user.UserApiClient
import com.makeus.daycarat.R
import com.makeus.daycarat.base.BaseActivity
import com.makeus.daycarat.databinding.ActivityMainBinding
import com.makeus.daycarat.presentation.viewmodel.HomeViewModel
import com.makeus.daycarat.presentation.viewmodel.MainViewmodel
import com.makeus.daycarat.util.Constant.TAG
import com.makeus.daycarat.util.Extensions.navigationHeight
import com.makeus.daycarat.util.Extensions.setStatusBarTransparent
import com.makeus.daycarat.util.Extensions.statusBarHeight
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding>({ ActivityMainBinding.inflate(it) }) {
    lateinit var navController: NavController
    private val mainViewModel by lazy {
        ViewModelProvider(this).get(MainViewmodel::class.java)
    }

    override fun initView() {
        WindowInsetsControllerCompat(window, window.decorView).isAppearanceLightStatusBars =
            true // 스테이터스 바 아이콘 검은색
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
                popUpTo(navController.graph.findStartDestination().id) {
                    saveState = true
                }
            }
            navController.navigate(R.id.editEpisodeFragment, args = null, option, null)
        }
        destinationListener()
    }

    fun destinationListener() {
        navController.addOnDestinationChangedListener { _: NavController?, destination: NavDestination, _: Bundle? ->
            when (destination.id) {
                R.id.editEpisodeFragment, R.id.episodeDetailTypeFragment , R.id.episodeSeeContentFragment -> {
                    binding.bottomNav.visibility = View.GONE
                }

                else -> {
                    binding.bottomNav.visibility = View.VISIBLE
                }
            }

        }
    }
}
