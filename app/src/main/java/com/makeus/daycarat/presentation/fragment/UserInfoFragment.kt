package com.makeus.daycarat.presentation.fragment

import android.content.Intent
import android.util.Log
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.makeus.daycarat.R
import com.makeus.daycarat.base.BaseFragment
import com.makeus.daycarat.databinding.FragmentUserInfoBinding
import com.makeus.daycarat.presentation.MainActivity
import com.makeus.daycarat.presentation.bottomSheet.GalleryFragment
import com.makeus.daycarat.presentation.dialog.ResignDialog
import com.makeus.daycarat.presentation.login.LoginActivity
import com.makeus.daycarat.presentation.viewmodel.MainViewmodel
import com.makeus.daycarat.presentation.viewmodel.UserDataViewmodel
import com.makeus.daycarat.util.Constant
import com.makeus.daycarat.util.Extensions.onThrottleClick
import com.makeus.daycarat.util.Extensions.repeatOnStarted
import com.makeus.daycarat.util.Extensions.statusBarHeight
import com.makeus.daycarat.util.PermissionManager.requestReadStorageAndCameraPreviewPermission
import com.makeus.daycarat.util.SharedPreferenceManager
import com.makeus.daycarat.util.UiEvent
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest

@AndroidEntryPoint
class UserInfoFragment() : BaseFragment<FragmentUserInfoBinding>(
    FragmentUserInfoBinding::inflate
) {
    private val mainViewModel: MainViewmodel by activityViewModels()
    private val userInfoViewModel by lazy {
        ViewModelProvider(this).get(UserDataViewmodel::class.java)
    }
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

        binding.btnEdit.onThrottleClick {
            requestReadStorageAndCameraPreviewPermission{b: Boolean ->
                Log.d("GHLEEPR" , "camera 권한 $b")
                if (b){
                    var bottomDialog = GalleryFragment()
                    bottomDialog.onclick = {
                        Glide.with(this@UserInfoFragment).load(it.uri).into(binding.imgProfile)
                        userInfoViewModel.updateUserProfile(it)
                    }
                    activity?.supportFragmentManager?.let { it1 ->
                        bottomDialog.show(it1, "GalleryFragment")
                    }
                }

            }


        }

        repeatOnStarted {
            userInfoViewModel.flowEvent.collect{event ->
                when (event) {
                    is UiEvent.LoadingEvent -> {
                        (activity as MainActivity).loadingDialog.show()
                    }
                    else -> {
                        (activity as MainActivity).loadingDialog.dismiss()
                    }
                }

            }
        }

        binding.btnLogout.onThrottleClick {
            ResignDialog(requireContext(),true).also { resignDialog: ResignDialog ->
                resignDialog.onclick = {
                    SharedPreferenceManager.getInstance().setString(Constant.USER_ACCESS_TOKEN,"")
                    (activity)?.finishAffinity()
                    Intent(activity , LoginActivity::class.java).apply {
                        startActivity(this)
                    }
                }
                resignDialog.show()
            }

        }

        binding.btnResign.onThrottleClick {  //회원 탈퇴
            ResignDialog(requireContext(),false).also { resignDialog: ResignDialog ->
                resignDialog.onclick = {
                    userInfoViewModel.deleteUserInfo()
                }
                resignDialog.show()
            }
        }

        repeatOnStarted {
            userInfoViewModel.resignFlowEvent.collectLatest { result ->
                when(result){
                    is UiEvent.SuccessEvent ->{
                        (activity as MainActivity).loadingDialog.dismiss()
                        SharedPreferenceManager.getInstance().setString(Constant.USER_ACCESS_TOKEN,"")
                        (activity)?.finishAffinity()
                        Intent(activity , LoginActivity::class.java).apply {
                            startActivity(this)
                        }
                    }else -> {}
                }

            }
        }


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