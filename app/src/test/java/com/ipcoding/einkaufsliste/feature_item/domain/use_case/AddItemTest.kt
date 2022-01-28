package com.ipcoding.einkaufsliste.feature_item.domain.use_case

import androidx.compose.ui.graphics.toArgb
import com.google.common.truth.Truth.assertThat
import com.ipcoding.einkaufsliste.feature_item.data.repository.FakeItemRepository
import com.ipcoding.einkaufsliste.feature_item.domain.model.InvalidItemExeption
import com.ipcoding.einkaufsliste.feature_item.domain.model.Item
import com.ipcoding.einkaufsliste.ui.theme.Red
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class AddItemTest {

    private lateinit var addItem: AddItem
    private lateinit var fakeRepository: FakeItemRepository

    @Before
    fun setUp() {
        fakeRepository = FakeItemRepository()
        addItem = AddItem(fakeRepository)

    }

    @Test
    fun `The title is blank, catch the error message `()  = runBlocking {

        var error : String? = null

        try {
            addItem(Item(title = "", color = Red.toArgb()))
        } catch (exception: InvalidItemExeption){
            error = exception.message
        }

        assertThat(error).isEqualTo("The Item can't be empty.")
    }
}