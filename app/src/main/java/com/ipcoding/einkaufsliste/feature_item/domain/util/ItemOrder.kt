package com.ipcoding.einkaufsliste.feature_item.domain.util

sealed class ItemOrder(val orderType: OrderType) {

    class Title(orderType: OrderType): ItemOrder(orderType)
    class Color(orderType: OrderType): ItemOrder(orderType)
}

