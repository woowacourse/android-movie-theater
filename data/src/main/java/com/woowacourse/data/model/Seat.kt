package com.woowacourse.data.model

typealias DataSeat = Seat

data class Seat(
    val row: DataSeatRow,
    val col: DataSeatCol,
)
