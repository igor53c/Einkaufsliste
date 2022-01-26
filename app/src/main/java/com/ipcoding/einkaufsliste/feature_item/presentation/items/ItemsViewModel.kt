package com.ipcoding.einkaufsliste.feature_item.presentation.items

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ipcoding.einkaufsliste.feature_item.domain.model.Item
import com.ipcoding.einkaufsliste.feature_item.domain.use_case.ItemUseCases
import com.ipcoding.einkaufsliste.feature_item.domain.util.ItemOrder
import com.ipcoding.einkaufsliste.feature_item.domain.util.OrderType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ItemsViewModel @Inject constructor(
    private val itemUseCases: ItemUseCases
) : ViewModel() {

    private val _state = mutableStateOf(ItemsState())
    val state: State<ItemsState> = _state

    private var recentlyDeletedItem: Item? = null

    private var getItemsJob: Job? = null

    init {
        getItems(ItemOrder.Color(OrderType.Ascending))
    }

    fun onEvent(event: ItemsEvent) {
        when(event) {
            is ItemsEvent.Order -> {
                if(state.value.itemOrder::class == event.itemOrder::class) {
                    when(state.value.itemOrder.orderType) {
                        is OrderType.Ascending -> {
                            event.itemOrder.orderType = OrderType.Descending
                        }
                        is OrderType.Descending -> {
                            event.itemOrder.orderType = OrderType.Ascending
                        }
                    }
                }
                getItems(event.itemOrder)
            }
            is ItemsEvent.DeleteItem -> {
                viewModelScope.launch {
                    itemUseCases.deleteItem(event.item)
                    recentlyDeletedItem = event.item
                }
            }
            is ItemsEvent.UpdateItem -> {
                viewModelScope.launch {
                    itemUseCases.updateItem(event.item)
                }
            }
            is ItemsEvent.RestoreItem -> {
                viewModelScope.launch {
                    itemUseCases.addItem(recentlyDeletedItem ?: return@launch)
                    recentlyDeletedItem = null
                }
            }
        }
    }

    private fun getItems(itemOrder: ItemOrder) {
        getItemsJob?.cancel()
        getItemsJob = itemUseCases.getItems(itemOrder)
            .onEach { items ->
                _state.value = state.value.copy(
                    items = items,
                    itemOrder = itemOrder
                )
            }
            .launchIn(viewModelScope)
    }
}