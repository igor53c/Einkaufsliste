package com.ipcoding.einkaufsliste.feature_item.domain.use_case

import com.ipcoding.einkaufsliste.feature_item.domain.model.Item
import com.ipcoding.einkaufsliste.feature_item.domain.repository.ItemRepository

class DeleteItem(
    private val repository: ItemRepository
) {

    suspend operator fun invoke(item: Item) {
        repository.deleteItem(item)
    }
}