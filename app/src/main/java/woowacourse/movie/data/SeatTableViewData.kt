package woowacourse.movie.data

data class SeatTableViewData(val seats: SeatsViewData, val size: TableSize) {
    fun getSeat(row: Int, column: Int): SeatViewData {
        return seats.value.find {
            it.row == row && it.column == column
        } ?: throw IllegalArgumentException(ERROR_WRONG_SEAT.format(row, column))
    }

    companion object {
        private const val ERROR_WRONG_SEAT = "입력한 행과 열에 좌석이 없습니다. 행 : %d, 열 : %d"
    }
}
