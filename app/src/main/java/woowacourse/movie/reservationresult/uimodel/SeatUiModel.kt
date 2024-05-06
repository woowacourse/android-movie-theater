package woowacourse.movie.reservationresult.uimodel

data class SeatUiModel(
    val showPosition: String,
) {
    constructor(row: Int, col: Int) : this(positionFormat(row, col))

    companion object {
        private const val START_ROW_ALPHABET = 'A'
        private const val SHOW_COL_FORMAT = 1

        private fun positionFormat(
            row: Int,
            col: Int,
        ): String {
            val rowLetter = START_ROW_ALPHABET + row
            return "$rowLetter${col + SHOW_COL_FORMAT}"
        }
    }
}
