package com.ipcoding.einkaufsliste.feature_item.domain.use_case

import com.ipcoding.einkaufsliste.feature_item.domain.model.InvalidItemExeption
import com.ipcoding.einkaufsliste.feature_item.domain.model.Item
import com.ipcoding.einkaufsliste.feature_item.domain.repository.ItemRepository

class AddItem(
    private val repository: ItemRepository
) {

    @Throws(InvalidItemExeption::class)
    suspend operator fun invoke(item: Item) {
        if(item.title.isBlank()) {
            throw InvalidItemExeption("The Item can't be empty.")
        }
        repository.insertItem(item)
    }
}