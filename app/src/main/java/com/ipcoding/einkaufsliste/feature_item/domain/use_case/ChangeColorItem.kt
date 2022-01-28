package com.ipcoding.einkaufsliste.feature_item.domain.use_case

import androidx.compose.ui.graphics.toArgb
import com.ipcoding.einkaufsliste.feature_item.domain.model.Item
import com.ipcoding.einkaufsliste.feature_item.domain.repository.ItemRepository
import com.ipcoding.einkaufsliste.ui.theme.Green
import com.ipcoding.einkaufsliste.ui.theme.Red

class ChangeColorItem (
    private val repository: ItemRepository
) {

    suspend operator fun invoke(item: Item) {
        val red = Red.toArgb()
        val green = Green.toArgb()
        item.color = if(item.color == red) green else red
        repository.insertItem(item)
    }
}