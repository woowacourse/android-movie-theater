package com.woowacourse.domain.movie

import com.woowacourse.domain.TheaterMovie

data class MovieBookingInfo(
    val theaterMovie: TheaterMovie,
    val date: String,
    val time: String,
    val ticketCount: Int,
) {
    fun formatAlarmDate(): String {
        val formattedDate: String = date.split(".").joinToString("-")
        return "$formattedDate $time"
    }
}
