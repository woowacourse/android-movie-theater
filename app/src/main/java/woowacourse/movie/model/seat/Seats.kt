package woowacourse.movie.model.seat

data class Seats(val values: List<Seat>) {
    init {
        require(values.all { it.isSelected }) { ERROR_UNSELECTED_SEATS }
    }

    val amount: Int
        get() = values.sumOf { it.grade.price }

    fun updateSeats(
        seat: Seat,
        headCount: Int,
    ): Seats {
        return if (contains(seat)) {
            Seats(values.filterNot { it.row == seat.row && it.col == seat.col })
        } else if (values.size >= headCount) {
            this
        } else {
            Seats(values + seat.copy(isSelected = true))
        }
    }

    fun contains(seat: Seat): Boolean = values.any { it.row == seat.row && it.col == seat.col }

    fun countSelected(): Int = values.count { it.isSelected }

    companion object {
        private const val ERROR_UNSELECTED_SEATS = "선택되지 않은 Seat은 포함될 수 없습니다"
    }
}
