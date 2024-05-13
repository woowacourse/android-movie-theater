package woowacourse.movie.purchaseconfirmation.uimodel

data class SeatUiModel(
    val showPosition: String,
) {
    constructor(row: Int, col: Int) : this(positionFormat(row, col))

    companion object {
        private fun positionFormat(
            row: Int,
            col: Int,
        ): String {
            val rowLetter = 'A' + row - 1
            return "$rowLetter$col"
        }
    }
}
