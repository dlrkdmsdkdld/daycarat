package com.makeus.daycarat.presentation.fragment.episode

import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.navigation.navOptions
import com.makeus.daycarat.R
import com.makeus.daycarat.base.BaseFragment
import com.makeus.daycarat.databinding.FragmentEditSoaraBinding
import com.makeus.daycarat.databinding.FragmentGemBinding
import com.makeus.daycarat.databinding.FragmentSoaraBinding
import com.makeus.daycarat.presentation.MainActivity
import com.makeus.daycarat.presentation.viewmodel.AuthViewmodel
import com.makeus.daycarat.presentation.viewmodel.episode.EditSoaraViewModel
import com.makeus.daycarat.presentation.viewmodel.episode.SoaraViewmodel
import com.makeus.daycarat.util.Extensions.onThrottleClick
import com.makeus.daycarat.util.Extensions.repeatOnStarted
import com.makeus.daycarat.util.Extensions.statusBarHeight
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest

@AndroidEntryPoint
class EditSoaraFragment() : BaseFragment<FragmentEditSoaraBinding>(
    FragmentEditSoaraBinding::inflate
) {


    private val viewModel by lazy {
        ViewModelProvider(this).get(EditSoaraViewModel::class.java)
    }


    override fun initView() {
        val args: EditSoaraFragmentArgs by navArgs()

        viewModel.initEpisodeData(args.episodeId, args.soaraContent, args.contentNum)
        Log.d("GHLEESS","initView args.soaraContent ${args.soaraContent}")
        if (args.soaraContent.isNotEmpty()) {
            binding.editSoara.setText(args.soaraContent)
        }

        binding.editSoara.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun afterTextChanged(p0: Editable?) {
                viewModel.editContent(p0.toString())
            }

        })

        repeatOnStarted {
            viewModel.episodeTemp.collectLatest {
                binding.btnComplete.isEnabled = it.episodeConetent.isNotEmpty()
                binding.textCount.text = "${it.episodeConetent.length}/200"
            }
        }
        binding.btnBack.onThrottleClick {
            findNavController().popBackStack()
        }

        binding.btnComplete.onThrottleClick {
            viewModel.setSoara()
        }

        repeatOnStarted {
            viewModel.flowEvent.collect { event ->
                when (event) {
                    is AuthViewmodel.UiEvent.LoadingEvent -> {
                        (activity as MainActivity).loadingDialog.show()
                    }

                    else -> {
                        (activity as MainActivity).loadingDialog.dismiss()
                        findNavController().popBackStack()
//                        findNavController().navigate(R.id.action_editSoaraFragment_to_soaraFragment)
                    }
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