package com.example.myshopping.ui.historypage

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.Navigation
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myshopping.R
import com.example.myshopping.Utils.SwipeToDeleteCallback
import com.example.myshopping.databinding.FragmentHistoryBinding
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HistoryPageFragment : Fragment() {

    private var _binding: FragmentHistoryBinding? = null
    private val binding get() = _binding!!

    private val historyPageViewModel: HistoryPageViewModel by viewModels()
    private val historyPageAdapter: HistoryPageAdapter by lazy {
        HistoryPageAdapter(
            historyPageViewModel
        )
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentHistoryBinding.inflate(inflater, container, false)

        binding.backToHomeButton.setOnClickListener {
            Navigation.findNavController(it).popBackStack()
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.rvHistoryPage.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = historyPageAdapter
        }
        observeData()

        val swipeToDeleteCallback = object : SwipeToDeleteCallback() {
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition
                historyPageViewModel.viewState.value.complateDates?.let {
                    historyPageViewModel.deleteCompDate(it.get(position))

                    Snackbar.make(
                        binding.rvHistoryPage,
                        getString(R.string.deleted) + ": " + it.get(position),
                        Snackbar.LENGTH_LONG
                    )
                        .show()
                }

            }
        }
        val itemTouchHelper = ItemTouchHelper(swipeToDeleteCallback)
        itemTouchHelper.attachToRecyclerView(binding.rvHistoryPage)
    }


    private fun observeData() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                historyPageViewModel.viewState.collectLatest { items ->
                    historyPageAdapter.submitList(items.complateDates?.map {
                        it
                    })
                    if (items.complateDates.isNullOrEmpty()) {
                        binding.emptyTextHistory.visibility = View.VISIBLE

                    } else {
                        binding.emptyTextHistory.visibility = View.INVISIBLE

                    }

                }
            }
        }
    }
}