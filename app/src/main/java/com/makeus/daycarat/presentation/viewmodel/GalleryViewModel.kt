package com.makeus.daycarat.presentation.viewmodel

import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.makeus.daycarat.presentation.util.base.BaseViewmodel
import com.makeus.daycarat.data.data.GalleryImage
import com.makeus.daycarat.data.source.GalleryPagingSource
import com.makeus.daycarat.data.source.GalleryPagingSource.Companion.PAGING_SIZE
import com.makeus.daycarat.domain.repository.GalleryRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GalleryViewModel @Inject constructor(private val repository: GalleryRepository) :
    BaseViewmodel() {

    private val _customGalleryPhotoList = MutableStateFlow<PagingData<GalleryImage>>(PagingData.empty())
    val customGalleryPhotoList: StateFlow<PagingData<GalleryImage>> =
        _customGalleryPhotoList.asStateFlow()

    private val _currentDirectory = MutableStateFlow<String>("" )
    val currentDirectory: StateFlow<String> = _currentDirectory

    private val _directoryList = MutableStateFlow<List<String>>(listOf<String>() )
    val directoryList: StateFlow<List<String>> = _directoryList

    init {
        getGalleryPagingImages()
        getDirectory()

    }

    fun getGalleryPagingImages(){
        viewModelScope.launch(Dispatchers.IO) {
            Pager(
                config = PagingConfig(
                    pageSize = PAGING_SIZE,
                    enablePlaceholders = true,
                ),
                pagingSourceFactory = {
                    GalleryPagingSource(
                        imageRepository = repository,
                        currnetLocation = currentDirectory.value,
                    )
                },
            )
            .flow.cachedIn(viewModelScope).collectLatest {
                _customGalleryPhotoList.value = it
            }
        }
    }

    fun setCurrentDirectory(index: Int) {
         _directoryList.value.getOrNull(index)?.let {
             _currentDirectory.value = it
             _customGalleryPhotoList.value = PagingData.empty()
             getGalleryPagingImages()
         }
    }


    fun setCurrentDirectory(location: String) {
        _currentDirectory.value = location
    }

    fun getDirectory() {
        _directoryList.value  =  repository.getFolderList()
    }


}
