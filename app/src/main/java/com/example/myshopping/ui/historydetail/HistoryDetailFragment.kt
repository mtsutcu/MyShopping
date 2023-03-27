package com.example.myshopping.ui.historydetail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.Navigation
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myshopping.databinding.FragmentHistoryDetailBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HistoryDetailFragment : Fragment() {

    private var _binding: FragmentHistoryDetailBinding? = null
    private val binding get() = _binding!!
    private val bundle: HistoryDetailFragmentArgs by navArgs()
    private val historyDetailViewModel: HistoryDetailViewModel by viewModels()
    private val historyDetailAdapter: HistoryDetailAdapter by lazy { HistoryDetailAdapter() }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentHistoryDetailBinding.inflate(inflater, container, false)

        binding.historyDetailTitle.text = bundle.completeDate

        binding.backToHistoryButton.setOnClickListener {
            Navigation.findNavController(it).popBackStack()
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.rvHistoryDetail.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = historyDetailAdapter
        }
        observeData()
    }


    private fun observeData() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                historyDetailViewModel.viewState.collectLatest { items ->

                    historyDetailAdapter.submitList(items.completeItems?.map { it.copy() })

                }
            }
        }
    }


}

