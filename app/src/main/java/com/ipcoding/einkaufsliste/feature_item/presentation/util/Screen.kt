package com.ipcoding.einkaufsliste.feature_item.presentation.util

sealed class Screen(val route: String) {
    object ItemsScreen: Screen("items_screen")
    object AddEditItemScreen: Screen("add_edit_item_screen")
}
