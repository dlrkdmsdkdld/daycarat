package com.makeus.daycarat.presentation.util

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.util.Log
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.gun0912.tedpermission.PermissionListener
import com.gun0912.tedpermission.normal.TedPermission

object PermissionManager {

    private fun Context.isPermissionGranted(permission: String) =
        ContextCompat.checkSelfPermission(this, permission) == PackageManager.PERMISSION_GRANTED

    internal fun Fragment.requestReadStorageAndCameraPreviewPermission( onResult:( (Boolean) -> Unit )) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) return
        val readMediaPermission = if (Build.VERSION.SDK_INT < Build.VERSION_CODES.TIRAMISU) Manifest.permission.READ_EXTERNAL_STORAGE else Manifest.permission.READ_MEDIA_IMAGES
        if (requireContext().isPermissionGranted(readMediaPermission)) {
            onResult.invoke(true)
            return
        }
//        TedPermission.create().setPermissions("android.permission.CAMERA").setPermissionListener(object  : PermissionListener{
//            override fun onPermissionGranted() {
//                Log.d("GHLEEPR" , "camera 권한 get")
//            }
//
//            override fun onPermissionDenied(deniedPermissions: MutableList<String>?) {
//                onResult.invoke(false)
//            }
//
//        }).check()

        readMediaPermission(readMediaPermission , onResult)


    }
    fun readMediaPermission(readMediaPermission: String, onResult: (Boolean) -> Unit){
        TedPermission.create().setPermissions(readMediaPermission).setPermissionListener(object  : PermissionListener{
            override fun onPermissionGranted() {
                Log.d("GHLEEPR" , "READ_EXTERNAL_STORAGE 권한 get")
                onResult.invoke(true)
            }

            override fun onPermissionDenied(deniedPermissions: MutableList<String>?) {
                Log.d("GHLEEPR" , "READ_EXTERNAL_STORAGE 권한 Denied")
                onResult.invoke(false)
            }

        }).check()
    }


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