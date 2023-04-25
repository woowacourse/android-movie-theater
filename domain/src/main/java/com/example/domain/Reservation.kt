package com.example.domain

import kotlin.properties.Delegates

data class Reservation(
    val totalPrice: Int,
    val ticketCount: Int,
    val seatNames: String,
    val movieTitle: String,
    val date: String,
    val time: String,
) {
    var id: Long? by Delegates.vetoable(null) { _, old, new ->
        old == null && new != null
    }
}