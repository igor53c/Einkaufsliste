package com.ipcoding.einkaufsliste.feature_item.data.repository

import com.ipcoding.einkaufsliste.feature_item.data.data_source.ItemDao
import com.ipcoding.einkaufsliste.feature_item.domain.model.Item
import com.ipcoding.einkaufsliste.feature_item.domain.repository.ItemRepository
import kotlinx.coroutines.flow.Flow

class ItemRepositoryImpl(
    private val dao: ItemDao
): ItemRepository {

    override fun getItems(): Flow<List<Item>> {
        return dao.getItems()
    }

    override suspend fun getItemById(id: Int?): Item? {
        return dao.getItemById(id)
    }

    override suspend fun insertItem(item: Item) {
        dao.insertItem(item)
    }

    override suspend fun deleteItem(item: Item) {
        dao.deleteItem(item)
    }
}