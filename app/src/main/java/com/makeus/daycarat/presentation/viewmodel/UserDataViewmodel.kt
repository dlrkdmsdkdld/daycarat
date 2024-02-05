package com.makeus.daycarat.presentation.viewmodel

import android.graphics.Bitmap
import android.util.Log
import androidx.lifecycle.viewModelScope
import com.makeus.daycarat.base.BaseViewmodel
import com.makeus.daycarat.core.dto.Status
import com.makeus.daycarat.data.UserData
import com.makeus.daycarat.data.paging.GalleryImage
import com.makeus.daycarat.repository.UserInfoRepository
import com.makeus.daycarat.util.UiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File
import javax.inject.Inject

@HiltViewModel
class UserDataViewmodel @Inject constructor(private val repository: UserInfoRepository) : BaseViewmodel(){
    private val _userData = MutableStateFlow<UserData>(UserData())
    val userData: StateFlow<UserData> = _userData

    fun updateUserInfo(){
        viewModelScope.launch(Dispatchers.IO) {
            repository.updateUserData(_userData.value).collect{result ->
                when(result.status) {
                    Status.LOADING -> {
                        sendEvent(UiEvent.LoadingEvent())
                    }

                    Status.SUCCESS -> {
                        sendEvent(UiEvent.SuccessEvent())
                    }
                    else -> {

                        sendEvent(UiEvent.FailEvent(result.message))

                    }
                }

            }
        }
    }

    fun updateUserProfile(galleryImage: GalleryImage) {
        viewModelScope.launch(Dispatchers.IO) {
            Log.d("GHLEE" , " galleryImage.name, ${ galleryImage.name} ")
            val requestFile = File(galleryImage.filepath).asRequestBody("image/*".toMediaTypeOrNull())
            val body = MultipartBody.Part.createFormData("multipartFile", galleryImage.name, requestFile)
            repository.updateProfileImage(body).collectLatest { result ->
                when(result.status) {
                    Status.LOADING -> {
                        sendEvent(UiEvent.LoadingEvent())
                    }

                    Status.SUCCESS -> {
                        sendEvent(UiEvent.SuccessEvent())
                    }
                    else -> {

                        sendEvent(UiEvent.FailEvent(result.message))

                    }
                }
            }
        }

    }
//    val bitmap : Bitmap = mbinding?.profileEditImage.drawable.toBitmap()
//    val bitmapRequestBody = bitmap?.let {  BitmapRequestBody(it)}
//    val bitmapMultipartBody: MultipartBody.Part = MultipartBody.Part.createFormData("file", "file.jpeg", bitmapRequestBody)
//    Log.d(TAG,"correctionImage -> $correctionImage")
//    val editTobe=mbinding?.profileEditTobeText.text.toString()
//    val editname=mbinding?.profileEditNicknameText.text.toString()
//    if (correctionImage){
//        val result = EditProfile(isImgEdited = "T" ,nickname = editname, beMyMessage = editTobe )
//        InformationRetrofitManager.instance.editProfile(file = bitmapMultipartBody,result){responseStatus, i ->
//            startMainActivity()
//            Log.d(TAG, "statuscode - > $i ")
//        }
//    }else{
//        val result = EditProfile(isImgEdited = "F" ,nickname = editname, beMyMessage = editTobe )
//        InformationRetrofitManager.instance.editProfile(file = null,result){responseStatus, i ->
//            startMainActivity()
//        }
//
//    }






}