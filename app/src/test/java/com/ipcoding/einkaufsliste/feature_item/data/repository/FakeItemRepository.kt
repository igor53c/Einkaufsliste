package com.ipcoding.einkaufsliste.feature_item.data.repository

import com.ipcoding.einkaufsliste.feature_item.domain.model.Item
import com.ipcoding.einkaufsliste.feature_item.domain.repository.ItemRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class FakeItemRepository : ItemRepository {

    private val items = mutableListOf<Item>()

    override fun getItems(): Flow<List<Item>> {
        return flow { emit(items) }
    }

    override suspend fun getItemById(id: Int?): Item? {
        return items.find { it.id == id }
    }

    override suspend fun insertItem(item: Item) {
        items.add(item)
    }

    override suspend fun deleteItem(item: Item) {
        items.remove(item)
    }
}