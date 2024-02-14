package com.makeus.daycarat.presentation.fragment.episode

import android.util.Log
import android.view.MenuItem
import android.widget.PopupMenu
import android.widget.PopupMenu.OnMenuItemClickListener
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.os.bundleOf
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.makeus.daycarat.R
import com.makeus.daycarat.base.BaseFragment
import com.makeus.daycarat.data.EpisodeContent
import com.makeus.daycarat.databinding.FragmentEpisodeSeeContentBinding
import com.makeus.daycarat.databinding.LayoutEpisodeDetailContentBinding
import com.makeus.daycarat.presentation.MainActivity
import com.makeus.daycarat.presentation.viewmodel.episode.EpisodeSeeViewModel
import com.makeus.daycarat.util.Constant
import com.makeus.daycarat.util.Extensions.onThrottleClick
import com.makeus.daycarat.util.Extensions.repeatOnStarted
import com.makeus.daycarat.util.Extensions.statusBarHeight
import com.makeus.daycarat.util.UiEvent
import com.makeus.daycarat.util.UiManager.inflateDetailContent
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest


@AndroidEntryPoint
class EpisodeSeeContentFragment() : BaseFragment<FragmentEpisodeSeeContentBinding>(
    FragmentEpisodeSeeContentBinding::inflate
) {

    private val viewModel by lazy {
        ViewModelProvider(this).get(EpisodeSeeViewModel::class.java)
    }

    override fun initView() {
        val arg: EpisodeSeeContentFragmentArgs by navArgs()
        viewModel.getEpisode(arg.episodeId)


        binding.btnBack.onThrottleClick {
            findNavController().popBackStack()
        }
        binding.btnAddEdit.onThrottleClick {
            findNavController().navigate(
                R.id.action_episodeSeeContentFragment_to_soaraFragment,
                bundleOf("episodeContent" to viewModel.getEpisodeContent())
            )
        }
        repeatOnStarted {
            viewModel.episodeConetent.collectLatest {
                binding.textTitle.text = it.title
                binding.textDate.text = it.selectedDate
                binding.textTag.text = it.activityTagName
                binding.fieldAddPoint.removeAllViews()
                it.episodeContents.forEach {
                    binding.fieldAddPoint.addView(inflateDetailContent(it, layoutInflater))
                }

            }
        }

        binding.btnMore.onThrottleClick {
            var popupMenu = PopupMenu(requireContext(), binding.btnMore)
            popupMenu.menuInflater.inflate(R.menu.episode_setting_menu, popupMenu.menu)
            popupMenu.setOnMenuItemClickListener(OnMenuItemClickListener { menuItem: MenuItem ->
                if (menuItem.itemId == R.id.option_edit) {
                    EditEpisodeFragmentDirections
                    var action = EpisodeSeeContentFragmentDirections.actionEpisodeSeeContentFragmentToEditEpisodeFragment(viewModel.episodeConetent.value)
                    findNavController().navigate(action)
                } else if (menuItem.itemId == R.id.option_remove) {
                    viewModel.deleteEpisode()
                }
                return@OnMenuItemClickListener true
            })
            popupMenu.setOnDismissListener {
                // Respond to popup being dismissed.
            }
            popupMenu.show()


        }

        repeatOnStarted {
            viewModel.flowEvent.collectLatest { event ->
                when (event) {
                    is UiEvent.LoadingEvent -> {
                        (activity as MainActivity).loadingDialog.show()
                    }
                    is UiEvent.SuccessEvent -> {
                        popupNavigate(Constant.EPISODE_REMOVE_SECCESS)
                    }
                    else -> {
                        popupNavigate(Constant.EPISODE_REMOVE_FAIL)

                    }

                }
            }
        }

    }
    fun popupNavigate(result:String){
        (activity as MainActivity).loadingDialog.dismiss()
        findNavController().apply {
            previousBackStackEntry?.savedStateHandle?.set("key", result)
            popBackStack()
        }
    }

    override fun onResume() {
        super.onResume()

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