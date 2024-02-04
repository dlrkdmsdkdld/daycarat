package com.makeus.daycarat.presentation.fragment

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.provider.MediaStore
import android.util.Log
import androidx.core.content.ContextCompat.checkSelfPermission
import androidx.fragment.app.activityViewModels
import com.bumptech.glide.Glide
import com.makeus.daycarat.R
import com.makeus.daycarat.base.BaseFragment
import com.makeus.daycarat.databinding.FragmentGemBinding
import com.makeus.daycarat.databinding.FragmentUserInfoBinding
import com.makeus.daycarat.presentation.viewmodel.MainViewmodel
import com.makeus.daycarat.util.Extensions.repeatOnStarted
import com.makeus.daycarat.util.Extensions.statusBarHeight
import com.makeus.daycarat.util.PermissionManager.requestReadStorageAndCameraPreviewPermission
import kotlinx.coroutines.flow.collectLatest

class UserInfoFragment() : BaseFragment<FragmentUserInfoBinding>(
    FragmentUserInfoBinding::inflate
) {
    private val mainViewModel: MainViewmodel by activityViewModels()
    val REQUEST_PERMISSION_READ_STORAGE_AND_CAMERA = 3

    override fun initView() {

        repeatOnStarted {
            mainViewModel.userData.collect {
                binding.textEmail.text = it.email
                binding.textNickname.text = it.nickname
                Log.d("profileImage", "profileImage ${it.profileImage}")
                Glide.with(this@UserInfoFragment)
                    .load(it.profileImage)
                    .error(R.drawable.bg_home)
                    .into(binding.imgProfile)


            }
        }

        binding.btnEdit.setOnClickListener {
            requestReadStorageAndCameraPreviewPermission{b: Boolean ->
                Log.d("GHLEEPR" , "camera 권한 $b")
            }

//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//                if (checkSelfPermission(requireContext() , Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
//                    requireActivity().contentResolver.refresh(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, null, null)
//                }
//            }
        }


    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    override fun initStatusBar() {
        binding.fieldMain.setPadding(
            0,
            requireActivity().statusBarHeight(),
            0,
            0
        )
    }

}