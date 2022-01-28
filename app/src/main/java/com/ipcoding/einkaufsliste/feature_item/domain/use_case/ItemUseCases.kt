package com.ipcoding.einkaufsliste.feature_item.domain.use_case

data class ItemUseCases(
    val getItems: GetItems,
    val deleteItem: DeleteItem,
    val changeColorItem: ChangeColorItem,
    val addItem: AddItem,
    val getItem: GetItem
)
