package com.ipcoding.einkaufsliste.feature_item.domain.use_case

import com.ipcoding.einkaufsliste.feature_item.domain.model.Item
import com.ipcoding.einkaufsliste.feature_item.domain.util.ItemOrder
import com.ipcoding.einkaufsliste.feature_item.domain.util.OrderType
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import com.google.common.truth.Truth.assertThat
import com.ipcoding.einkaufsliste.feature_item.data.repository.FakeItemRepository
import org.junit.Before
import org.junit.Test

class GetItemsTest {

    private lateinit var getItems: GetItems
    private lateinit var fakeRepository: FakeItemRepository

    @Before
    fun setUp() {
        fakeRepository = FakeItemRepository()
        getItems = GetItems(fakeRepository)

        val itemsToInsert = mutableListOf<Item>()
        ('a'..'z').forEachIndexed { index, c ->
            itemsToInsert.add(
                Item(
                    title = c.toString(),
                    color = index
                )
            )
        }
        itemsToInsert.shuffle()
        runBlocking {
            itemsToInsert.forEach { fakeRepository.insertItem(it) }
        }
    }

    @Test
    fun `Order items by title ascending, correct order`() = runBlocking {
        val items = getItems(ItemOrder.Title(OrderType.Ascending)).first()

        for(i in 0..items.size - 2) {
            assertThat(items[i].title).isLessThan(items[i+1].title)
        }
    }

    @Test
    fun `Order items by title descending, correct order`() = runBlocking {
        val items = getItems(ItemOrder.Title(OrderType.Descending)).first()

        for(i in 0..items.size - 2) {
            assertThat(items[i].title).isGreaterThan(items[i+1].title)
        }
    }

    @Test
    fun `Order items by color ascending, correct order`() = runBlocking {
        val items = getItems(ItemOrder.Color(OrderType.Ascending)).first()

        for(i in 0..items.size - 2) {
            assertThat(items[i].color).isLessThan(items[i+1].color)
        }
    }

    @Test
    fun `Order items by color descending, correct order`() = runBlocking {
        val items = getItems(ItemOrder.Color(OrderType.Descending)).first()

        for(i in 0..items.size - 2) {
            assertThat(items[i].color).isGreaterThan(items[i+1].color)
        }
    }
}