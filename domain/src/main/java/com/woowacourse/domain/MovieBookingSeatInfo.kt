package com.woowacourse.domain

data class MovieBookingSeatInfo(
    val movieBookingInfo: MovieBookingInfo,
    val seats: List<String>,
    val totalPrice: String,
)
