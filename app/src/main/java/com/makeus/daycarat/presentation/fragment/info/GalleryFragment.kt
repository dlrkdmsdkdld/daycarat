package com.makeus.daycarat.presentation.fragment.info

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.makeus.daycarat.base.BaseFragment
import com.makeus.daycarat.data.paging.GalleryImage
import com.makeus.daycarat.databinding.BottomEditCalenderBinding
import com.makeus.daycarat.databinding.FragmentGalleryBinding
import com.makeus.daycarat.databinding.FragmentUserInfoBinding
import com.makeus.daycarat.presentation.recyclerview.paging.EpisodeDetailAdatper
import com.makeus.daycarat.presentation.recyclerview.paging.GalleryAdapter
import com.makeus.daycarat.presentation.viewmodel.GalleryViewModel
import com.makeus.daycarat.presentation.viewmodel.GemViewModel
import com.makeus.daycarat.util.Extensions.repeatOnStarted
import com.makeus.daycarat.util.Extensions.statusBarHeight
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class GalleryFragment() : BottomSheetDialogFragment(){
    val binding by lazy { FragmentGalleryBinding.inflate(layoutInflater) }

    private val viewModel by lazy {
        ViewModelProvider(this).get(GalleryViewModel::class.java)
    }

    private lateinit var pagingAdapter:GalleryAdapter
    var onclick :( (GalleryImage) -> Unit)? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        pagingAdapter = GalleryAdapter{
            onclick?.invoke(it)
            dismissAllowingStateLoss()
        }
        binding.recycler.adapter = pagingAdapter
        repeatOnStarted {
            viewModel.customGalleryPhotoList.collect{ pagingData ->
                pagingAdapter.submitData(pagingData)
            }
        }
    }

}