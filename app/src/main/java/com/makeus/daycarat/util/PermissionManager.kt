package com.makeus.daycarat.util

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.provider.Settings
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.gun0912.tedpermission.PermissionListener
import com.gun0912.tedpermission.normal.TedPermission

object PermissionManager {

    fun checkNotiPermission(context:Context): Boolean {
        if (Build.VERSION.SDK_INT >= 33) { //os 13부터 알림권한 필요
            val permission = ContextCompat.checkSelfPermission(context, "android.permission.POST_NOTIFICATIONS")
            return permission != PackageManager.PERMISSION_GRANTED
        }else{ // 유저의 기기 버전이 안드로이드 13이하이면 해당 퍼미션 필요없으므로 onGranted
            return false
        }
    }

    fun requestNotiPermission(activity:Activity){
        TedPermission.create().setPermissions("android.permission.POST_NOTIFICATIONS")
            .setPermissionListener(object : PermissionListener {
                override fun onPermissionGranted() {

                }

                override fun onPermissionDenied(deniedPermissions: MutableList<String>?) {
                }

            })
            .check()
//        if (ActivityCompat.shouldShowRequestPermissionRationale(activity, "android.permission.POST_NOTIFICATIONS")) { // 유저가 이미 권한을 거부한경우 , 사용자가 권한 요청을 처음 보거나, 다시 묻지 않음 선택한 경우, 권한을 허용한 경우 false를 반환한다
//            try {
//                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//                    val notiIntent = Intent(Settings.ACTION_APP_NOTIFICATION_SETTINGS)
//                        .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
//                        .putExtra(
//                            Settings.EXTRA_APP_PACKAGE,
//                            activity.packageName
//                        )
//
//                    activity.startActivity(notiIntent)
//                } else {
//                    val intent =
//                        Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).setData(Uri.parse("package:" + activity.packageName))
//                    activity.startActivity(intent)
//                }
//            } catch (e: Exception) {
//                val intent =
//                    Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).setData(Uri.parse("package:" + activity.packageName))
//                activity.startActivity(intent)
//            }
//        } else {
//            TedPermission.create().setPermissions("android.permission.POST_NOTIFICATIONS")
//                .setPermissionListener(object : PermissionListener {
//                    override fun onPermissionGranted() {
//
//                    }
//
//                    override fun onPermissionDenied(deniedPermissions: MutableList<String>?) {
//                    }
//
//                })
//                .check()
//        }
    }


}