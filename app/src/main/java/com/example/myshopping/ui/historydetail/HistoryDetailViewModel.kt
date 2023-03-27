package com.example.myshopping.ui.historydetail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.myshopping.data.model.ShoppingItem
import com.example.myshopping.data.repository.ShoppingRepository
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HistoryDetailViewModel @Inject constructor(private val repository: ShoppingRepository,  state: SavedStateHandle) : ViewModel() {

    private val _viewState = MutableStateFlow(HistoryDetailViewState())
    val viewState : StateFlow<HistoryDetailViewState> = _viewState.asStateFlow()

    init {
        state.get<String>("completeDate")?.let { getCompleteItems(it) }
    }

    fun getCompleteItems(compDate : String){
        viewModelScope.launch {
            repository.getCompleteItems(compDate).collect{items ->
                _viewState.update {
                    it.copy(
                        completeItems = items
                    )
                }
            }
        }

    }



}

data class HistoryDetailViewState(val completeItems : List<ShoppingItem>? = null)

