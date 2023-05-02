package com.example.domain.model.seat

data class SeatPosition(
    val row: SeatRow,
    val column: SeatColumn
) {
    constructor(row: Int, column: Int) : this(SeatRow.valueOf(row), SeatColumn.valueOf(column))
}
