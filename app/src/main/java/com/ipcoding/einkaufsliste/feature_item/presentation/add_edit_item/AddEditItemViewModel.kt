package com.ipcoding.einkaufsliste.feature_item.presentation.add_edit_item

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.toArgb
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ipcoding.einkaufsliste.feature_item.domain.model.InvalidItemExeption
import com.ipcoding.einkaufsliste.feature_item.domain.model.Item
import com.ipcoding.einkaufsliste.feature_item.domain.use_case.ItemUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddEditItemViewModel @Inject constructor(
    private val itemUseCases: ItemUseCases,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _itemTitle = mutableStateOf(ItemTextFieldState(
        hint = "Enter item..."
    ))
    val itemTitle: State<ItemTextFieldState> = _itemTitle

    private val _itemColor = mutableStateOf(Item.itemColors.random().toArgb())
    val itemColor: State<Int> = _itemColor

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    private var currentItemId: Int? = null

    init {
        savedStateHandle.get<Int>("itemId")?.let { itemId ->
            if(itemId != -1) {
                viewModelScope.launch {
                    itemUseCases.getItem(itemId)?.also { item ->
                        currentItemId = item.id
                        _itemTitle.value = itemTitle.value.copy(
                            text = item.title,
                            isHintVisible = false
                        )
                        _itemColor.value = item.color
                    }
                }
            }
        }
    }

    fun onEvent(event: AddEditItemEvent) {
        when(event) {
            is AddEditItemEvent.EnteredTitle -> {
                _itemTitle.value = itemTitle.value.copy(
                    text = event.value
                )
            }
            is AddEditItemEvent.ChangeTitleFocus -> {
                _itemTitle.value = itemTitle.value.copy(
                    isHintVisible = !event.focusState.isFocused &&
                            itemTitle.value.text.isBlank()
                )
            }
            is AddEditItemEvent.ChangeColor -> {
                _itemColor.value = event.color
            }
            is AddEditItemEvent.SaveItem -> {
                viewModelScope.launch {
                    try {
                        itemUseCases.addItem(
                            Item(
                                title = itemTitle.value.text,
                                color = itemColor.value,
                                id = currentItemId
                            )
                        )
                        _eventFlow.emit(UiEvent.SaveItem)
                    } catch(e: InvalidItemExeption) {
                        _eventFlow.emit(
                            UiEvent.ShowSnackbar(
                                message = e.message ?: "Couldn't save item"
                            )
                        )
                    }
                }
            }
        }
    }

    sealed class UiEvent {
        data class ShowSnackbar(val message: String): UiEvent()
        object SaveItem: UiEvent()
    }

}