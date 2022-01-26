package com.ipcoding.einkaufsliste.feature_item.presentation.add_edit_item

import androidx.compose.ui.focus.FocusState

sealed class AddEditItemEvent {
    data class EnteredTitle(val value: String): AddEditItemEvent()
    data class ChangeTitleFocus(val focusState: FocusState): AddEditItemEvent()
    data class ChangeColor(val color: Int): AddEditItemEvent()
    object SaveItem: AddEditItemEvent()
}
