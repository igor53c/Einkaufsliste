package com.ipcoding.einkaufsliste.feature_item.domain.use_case

import com.ipcoding.einkaufsliste.feature_item.domain.model.Item
import com.ipcoding.einkaufsliste.feature_item.domain.repository.ItemRepository
import com.ipcoding.einkaufsliste.feature_item.domain.util.ItemOrder
import com.ipcoding.einkaufsliste.feature_item.domain.util.OrderType
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GetItems(
    private val repository: ItemRepository
) {

    operator fun invoke(
        itemOrder: ItemOrder = ItemOrder.Color(OrderType.Ascending)
    ): Flow<List<Item>> {
        return repository.getItems().map { items ->
            when (itemOrder.orderType) {
                is OrderType.Ascending -> {
                    when (itemOrder) {
                        is ItemOrder.Title -> items.sortedBy { it.title.lowercase() }
                        is ItemOrder.Color -> items.sortedBy { it.color }
                    }
                }
                is OrderType.Descending -> {
                    when (itemOrder) {
                        is ItemOrder.Title -> items.sortedByDescending { it.title.lowercase() }
                        is ItemOrder.Color -> items.sortedByDescending { it.color }
                    }
                }
            }
        }
    }
}