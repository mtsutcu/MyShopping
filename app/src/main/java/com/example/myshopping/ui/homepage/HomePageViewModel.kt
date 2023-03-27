package com.example.myshopping.ui.homepage

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myshopping.data.model.ShoppingItem
import com.example.myshopping.data.repository.ShoppingRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

@HiltViewModel
class HomePageViewModel @Inject constructor(var repository: ShoppingRepository) : ViewModel() {
    private val _viewState = MutableStateFlow(HomePageViewState())
    val viewState: StateFlow<HomePageViewState> = _viewState.asStateFlow()

    init {
        getItems()
    }


    fun getItems() {
        viewModelScope.launch {
            repository.getItems().collect { items ->
                _viewState.update {
                    it.copy(
                        shoppingItems = items
                    )
                }
            }
        }
    }

    fun addItem(shoppingItem: ShoppingItem) {
        viewModelScope.launch {
            repository.addItem(shoppingItem)
        }
    }

    fun updateItem(shoppingItem: ShoppingItem) {
        viewModelScope.launch {
            repository.updateItem(shoppingItem)
        }
    }

    fun checkItemContain(shoppingItem: ShoppingItem) {
        var checkItem = viewState.value.shoppingItems?.singleOrNull {
            it.item_name.uppercase() == shoppingItem.item_name.uppercase()
        }


        if (checkItem != null) {
            val updateCheckItem = checkItem.copy(item_count = checkItem.item_count?.plus(1))
            updateItem(updateCheckItem)

        } else {
            addItem(shoppingItem)
        }


    }

    fun minusItemCount(shoppingItem: ShoppingItem) {
        val updateItem = shoppingItem.copy(item_count = shoppingItem.item_count?.minus(1))
        viewModelScope.launch {
            updateItem(updateItem)
        }
    }

    fun deleteItem(shoppingItem: ShoppingItem) {
        viewModelScope.launch {
            repository.deleteItem(shoppingItem)
        }
    }

    fun deleteAll() {
        viewState.value.shoppingItems?.forEach {
            deleteItem(it)
        }
    }

    fun complateAll() {
        val time = Calendar.getInstance().time
        val formatter = SimpleDateFormat("dd.MM.yyyy - HH:mm")
        val current = formatter.format(time)
        viewState.value.shoppingItems?.forEach {
            it.item_compDate = current
            updateItem(it)
        }
    }


}


data class HomePageViewState(
    val shoppingItems: List<ShoppingItem>? = null,
    val complateDates: List<String>? = null
)