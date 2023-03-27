package com.example.myshopping.ui.historypage

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.myshopping.databinding.HistoryPageItemBinding


class HistoryPageAdapter(var vievModel: HistoryPageViewModel) :
    ListAdapter<String, HistoryPageAdapter.ItemsViewHolder>(ItemsUtils()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemsViewHolder {
        val binding =
            HistoryPageItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return ItemsViewHolder(binding, vievModel)
    }

    override fun onBindViewHolder(holder: ItemsViewHolder, position: Int) {
        getItem(position)?.let {
            holder.bind(it, position)
        }
    }

    class ItemsViewHolder(
        private val binding: HistoryPageItemBinding,
        var vievModel: HistoryPageViewModel
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: String, position: Int) {

            binding.historyPageItemText.text = item

            binding.historyPageItemText.setOnClickListener {
                val trans = HistoryPageFragmentDirections.Companion.toHistoryDetail(item)
                Navigation.findNavController(it).navigate(trans)
            }

            binding.historyPageItemText.setOnLongClickListener {
                vievModel.deleteCompDate(item)

                return@setOnLongClickListener true
            }
        }
    }

    class ItemsUtils : DiffUtil.ItemCallback<String>() {
        override fun areItemsTheSame(oldItem: String, newItem: String): Boolean {
            return oldItem.length == newItem.length
        }

        override fun areContentsTheSame(oldItem: String, newItem: String): Boolean {
            return oldItem == newItem

        }

    }
}


