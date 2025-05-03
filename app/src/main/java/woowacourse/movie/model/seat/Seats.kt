package woowacourse.movie.model.seat

data class Seats(val values: List<Seat>) {
    init {
        require(values.all { it.isSelected }) { ERROR_UNSELECTED_SEATS }
    }

    val amount: Int
        get() = values.sumOf { it.grade.price }

    fun toggle(
        seat: Seat,
        headCount: Int,
    ): Seats {
        val row = seat.row
        val col = seat.col
        val existingSeat = values.find { it.col == col && it.row == row }

        return if (existingSeat != null) {
            Seats(
                values.map {
                    if (it.col == col && it.row == row) it.copy(isSelected = false) else it
                }.filter { it.isSelected },
            )
        } else {
            if (values.count { it.isSelected } >= headCount) {
                this
            } else {
                Seats(values + seat.copy(isSelected = true))
            }
        }
    }

    companion object {
        private const val ERROR_UNSELECTED_SEATS = "선택되지 않은 Seat은 포함될 수 없습니다"
    }
}
