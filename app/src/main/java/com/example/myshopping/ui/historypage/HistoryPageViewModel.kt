package com.example.myshopping.ui.historypage

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myshopping.data.model.ShoppingItem
import com.example.myshopping.data.repository.ShoppingRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import javax.inject.Inject


@HiltViewModel
class HistoryPageViewModel @Inject constructor(var repository: ShoppingRepository) : ViewModel() {
    private val _viewState = MutableStateFlow(HistoryPageViewState())
    val viewState: StateFlow<HistoryPageViewState> = _viewState.asStateFlow()

    init {

        getCompDates()
    }

    fun deleteCompDate(compDate : String){
        viewModelScope.launch {

            repository.deleteCompDate(compDate)
        }
    }




    fun getCompDates(){


        viewModelScope.launch {
            repository.getCompDate().collect { items ->

                _viewState.update {
                    it.copy(
                        complateDates = items?.distinct()
                    )
                }

            }
        }
    }
}


data class HistoryPageViewState(val complateDates : List<String>? = null)