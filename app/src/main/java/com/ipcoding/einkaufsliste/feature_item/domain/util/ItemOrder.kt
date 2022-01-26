package com.ipcoding.einkaufsliste.feature_item.domain.util

sealed class ItemOrder(var orderType: OrderType) {

    class Title(orderType: OrderType): ItemOrder(orderType)
    class Color(orderType: OrderType): ItemOrder(orderType)

    fun copy(orderType: OrderType): ItemOrder {
        return  when(this) {
            is Title -> Title(orderType)
            is Color -> Color(orderType)
        }
    }
}

