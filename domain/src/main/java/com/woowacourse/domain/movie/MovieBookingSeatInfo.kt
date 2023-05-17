package com.woowacourse.domain.movie

data class MovieBookingSeatInfo(
    val movieBookingInfo: MovieBookingInfo,
    val seats: List<String>,
    val totalPrice: String,
)
