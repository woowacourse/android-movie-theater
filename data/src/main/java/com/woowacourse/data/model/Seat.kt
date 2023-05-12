package com.woowacourse.data.model

typealias DataSeat = Seat

data class Seat(
    val row: DataSeatRow,
    val col: DataSeatCol,
) {
    constructor(row: Int, col: Int) : this(DataSeatRow(row), DataSeatCol(col))
}
