package com.ipcoding.einkaufsliste.feature_item.domain.use_case

import com.ipcoding.einkaufsliste.feature_item.domain.model.Item
import com.ipcoding.einkaufsliste.feature_item.domain.repository.ItemRepository

class GetItem (
    private val repository: ItemRepository
) {

    suspend operator fun invoke(id: Int): Item? {
        return repository.getItemById(id)
    }
}