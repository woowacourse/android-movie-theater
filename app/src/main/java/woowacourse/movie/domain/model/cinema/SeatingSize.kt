package woowacourse.movie.domain.model.cinema

data class SeatingSize(
    val rowSize: Int,
    val colSize: Int,
) {
    init {
        require(rowSize > MINIMUM_SIZE && colSize > MINIMUM_SIZE) {
            INVALID_SEATING_SIZE.format(
                rowSize,
                colSize,
            )
        }
    }

    companion object {
        private const val MINIMUM_SIZE = 0
        private const val INVALID_SEATING_SIZE = "좌석의 규모는 ${MINIMUM_SIZE}보다 커야 합니다. rowSize: %d, colSize: %d"
    }
}
