package com.woowacourse.domain

data class MovieBookingInfo(
    val movieInfo: Movie,
    val date: String,
    val time: String,
    val ticketCount: Int
) {
    fun formatAlarmDate(): String {
        val formattedDate: String = date.split(".").joinToString("-")
        return "$formattedDate $time"
    }
}
