package com.example.myshopping.ui.historydetail

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.myshopping.data.model.ShoppingItem
import com.example.myshopping.databinding.ShoppingItemCardBinding

class HistoryDetailAdapter() :
    ListAdapter<ShoppingItem, HistoryDetailAdapter.ItemsViewHolder>(ItemsUtils()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemsViewHolder {
        val binding =
            ShoppingItemCardBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return ItemsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ItemsViewHolder, position: Int) {
        getItem(position)?.let {
            holder.bind(it, position)
        }
    }

    class ItemsViewHolder(
        private val binding: ShoppingItemCardBinding,

        ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: ShoppingItem, position: Int) {

            binding.minusButton.visibility = View.INVISIBLE
            binding.plusButton.visibility = View.GONE

            binding.shoppingItemText.text = item.item_name
            binding.shoppingItemCountText.text = item.item_count.toString()

        }
    }

    class ItemsUtils : DiffUtil.ItemCallback<ShoppingItem>() {
        override fun areItemsTheSame(oldItem: ShoppingItem, newItem: ShoppingItem): Boolean {
            return oldItem.item_id == newItem.item_id
        }

        override fun areContentsTheSame(oldItem: ShoppingItem, newItem: ShoppingItem): Boolean {
            return oldItem.item_name == newItem.item_name
                    && oldItem.item_compDate == newItem.item_compDate


        }


    }
}