package com.survivalcoding.noteapp.domain.util

sealed class NoteOrder(val orderType: OrderType) {
    class Title(orderType: OrderType) : NoteOrder(orderType) {
        override fun copy(orderType: OrderType): NoteOrder = Title(orderType)
    }

    class Date(orderType: OrderType) : NoteOrder(orderType) {
        override fun copy(orderType: OrderType): NoteOrder = Date(orderType)
    }

    class Color(orderType: OrderType) : NoteOrder(orderType) {
        override fun copy(orderType: OrderType): NoteOrder = Color(orderType)
    }

    abstract fun copy(orderType: OrderType): NoteOrder
}
