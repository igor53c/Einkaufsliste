package com.ipcoding.einkaufsliste.feature_item.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.ipcoding.einkaufsliste.ui.theme.Green
import com.ipcoding.einkaufsliste.ui.theme.Red

@Entity
data class Item(
    val title: String,
    val quantity: String?,
    val color: Int,
    @PrimaryKey val id: Int? = null
) {
    companion object {
        val itemColors = listOf(Red, Green)
    }
}
