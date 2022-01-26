package com.ipcoding.einkaufsliste.feature_item.presentation.items

import com.ipcoding.einkaufsliste.feature_item.domain.model.Item
import com.ipcoding.einkaufsliste.feature_item.domain.util.ItemOrder

sealed class ItemsEvent {
    data class Order(val itemOrder: ItemOrder): ItemsEvent()
    data class DeleteItem(val item: Item): ItemsEvent()
    data class UpdateItem(val item: Item): ItemsEvent()
    object RestoreItem: ItemsEvent()
}
