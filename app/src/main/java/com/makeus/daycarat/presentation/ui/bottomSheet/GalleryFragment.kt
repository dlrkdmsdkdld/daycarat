package com.makeus.daycarat.presentation.bottomSheet

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.makeus.daycarat.data.data.GalleryImage
import com.makeus.daycarat.databinding.FragmentGalleryBinding
import com.makeus.daycarat.presentation.recyclerview.paging.GalleryAdapter
import com.makeus.daycarat.presentation.spinner.GalleryFolderSpinnerdeSpinner
import com.makeus.daycarat.presentation.viewmodel.GalleryViewModel
import com.makeus.daycarat.presentation.util.Extensions.repeatOnStarted
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
            dismissAllowingStateLoss()
            onclick?.invoke(it)
        }
        binding.recycler.adapter = pagingAdapter
        repeatOnStarted {
            viewModel.customGalleryPhotoList.collect{ pagingData ->
                pagingAdapter.submitData(pagingData)
            }
        }
        repeatOnStarted {
            viewModel.directoryList.collect{
                binding.header.adapter = GalleryFolderSpinnerdeSpinner(requireContext(), it)
            }
        }

        binding.header.onItemSelectedListener = object: AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
//                viewModel.setCurrentDirectory()
                viewModel.setCurrentDirectory(position)
                pagingAdapter.refresh()

            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
            }

        }


    }

}