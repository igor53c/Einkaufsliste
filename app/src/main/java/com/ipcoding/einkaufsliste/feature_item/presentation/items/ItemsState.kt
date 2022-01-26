package com.ipcoding.einkaufsliste.feature_item.presentation.items

import com.ipcoding.einkaufsliste.feature_item.domain.model.Item
import com.ipcoding.einkaufsliste.feature_item.domain.util.ItemOrder
import com.ipcoding.einkaufsliste.feature_item.domain.util.OrderType

data class ItemsState(
    val items: List<Item> = emptyList(),
    val itemOrder: ItemOrder = ItemOrder.Color(OrderType.Ascending)
)
