package com.example.myshopping.ui.homepage

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.fragment.app.Fragment
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
import com.example.myshopping.data.model.ShoppingItem
import com.example.myshopping.databinding.FragmentHomePageBinding
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch


@AndroidEntryPoint
class HomePageFragment : Fragment() {

    private var _binding: FragmentHomePageBinding? = null
    private val binding get() = _binding!!

    private val homePageViewModel: HomePageViewModel by viewModels()
    private val homePageAdapter: HomePageAdapter by lazy { HomePageAdapter(homePageViewModel) }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomePageBinding.inflate(inflater, container, false)

        binding.addFAB.setOnClickListener {
            enterItem()
        }
        binding.deleteAllButton.setOnClickListener {
            homePageViewModel.deleteAll()
        }

        binding.historyButton.setOnClickListener {
            Navigation.findNavController(it).navigate(R.id.homeToHistory)
        }

        binding.completeFAB.setOnClickListener {
            homePageViewModel.complateAll()
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeData()
        binding.rvShoppingList.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = homePageAdapter
        }


        val swipeToDeleteCallback = object : SwipeToDeleteCallback() {
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition
                homePageViewModel.viewState.value.shoppingItems?.let {
                    homePageViewModel.deleteItem(
                        it.get(position)
                    )

                    Snackbar.make(
                        binding.rvShoppingList,
                        it.get(position).item_name + " " + getString(R.string.deleted),
                        Snackbar.LENGTH_LONG
                    )
                        .show()
                }

            }
        }
        val itemTouchHelper = ItemTouchHelper(swipeToDeleteCallback)
        itemTouchHelper.attachToRecyclerView(binding.rvShoppingList)

    }


    private fun observeData() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                homePageViewModel.viewState.collectLatest {
                    homePageAdapter.submitList(it.shoppingItems?.map { it.copy() })

                    if (it.shoppingItems.isNullOrEmpty()) {
                        binding.completeFAB.visibility = View.INVISIBLE
                        binding.deleteAllButton.visibility = View.GONE
                        binding.rvShoppingList.visibility = View.INVISIBLE
                        binding.emptyText.visibility = View.VISIBLE

                    } else {
                        binding.completeFAB.visibility = View.VISIBLE
                        binding.deleteAllButton.visibility = View.VISIBLE
                        binding.rvShoppingList.visibility = View.VISIBLE
                        binding.emptyText.visibility = View.INVISIBLE

                    }

                }
            }
        }
    }

    private fun enterItem() {
        val alert = AlertDialog.Builder(requireContext())

        val myCustomLayout: View = layoutInflater.inflate(R.layout.custom_dialog, null)
        alert.setView(myCustomLayout)
        alert.setTitle(R.string.addItem)
        alert.setPositiveButton(R.string.add) { s, d ->

            val itemText = myCustomLayout.findViewById<EditText>(R.id.enter_item_edit_text)
            if (!itemText.text.isNullOrEmpty()) {
                homePageViewModel.checkItemContain(
                    ShoppingItem(
                        0,
                        itemText.text.toString(),
                        1,
                        null
                    )
                )
            }
        }
        alert.show()
    }


}