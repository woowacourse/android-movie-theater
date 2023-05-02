package com.example.domain.model.seat

enum class SeatColumn {
    ONE,
    TWO,
    THREE,
    FOUR;

    companion object {
        private const val ERROR_COLUMN_RANGE = "[ERROR] COLUMN의 범위는 1에서 4사이입니다."
        private val COLUMNS: Map<Int, SeatColumn> = values().associateBy { it.ordinal }
        fun valueOf(column: Int): SeatColumn =
            COLUMNS[column - 1] ?: throw IllegalArgumentException(ERROR_COLUMN_RANGE)
    }
}
