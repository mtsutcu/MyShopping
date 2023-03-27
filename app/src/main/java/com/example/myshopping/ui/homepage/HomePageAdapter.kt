package com.example.myshopping.ui.homepage

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.myshopping.data.model.ShoppingItem
import com.example.myshopping.databinding.ShoppingItemCardBinding

class HomePageAdapter(var vievModel: HomePageViewModel) :
    ListAdapter<ShoppingItem, HomePageAdapter.ItemsViewHolder>(ItemsUtils()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemsViewHolder {
        val binding =
            ShoppingItemCardBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return ItemsViewHolder(binding, vievModel)
    }

    override fun onBindViewHolder(holder: ItemsViewHolder, position: Int) {
        getItem(position)?.let {
            holder.bind(it, position)
        }
    }

    class ItemsViewHolder(
        private val binding: ShoppingItemCardBinding,
        var vievModel: HomePageViewModel
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: ShoppingItem, position: Int) {
            if (item.item_count!! > 1){
                binding.minusButton.visibility = View.VISIBLE
            }else{
                binding.minusButton.visibility = View.INVISIBLE
            }

            binding.shoppingItemText.text = item.item_name
            binding.shoppingItemCountText.text = item.item_count.toString()

            binding.plusButton.setOnClickListener {

                vievModel.checkItemContain(item)
            }

            binding.minusButton.setOnClickListener {
                vievModel.minusItemCount(item)
            }

        }
    }

    class ItemsUtils : DiffUtil.ItemCallback<ShoppingItem>() {
        override fun areItemsTheSame(oldItem: ShoppingItem, newItem: ShoppingItem): Boolean {
            return oldItem.item_id == newItem.item_id
        }

        override fun areContentsTheSame(oldItem: ShoppingItem, newItem: ShoppingItem): Boolean {
            return oldItem.item_id == newItem.item_id
                    && oldItem.item_name == newItem.item_name
                    && oldItem.item_count == newItem.item_count
                    && oldItem.item_compDate == newItem.item_compDate

        }

    }
}