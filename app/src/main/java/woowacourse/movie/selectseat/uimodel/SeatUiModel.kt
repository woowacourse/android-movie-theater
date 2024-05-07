package woowacourse.movie.selectseat.uimodel

import woowacourse.movie.model.Tier

data class SeatUiModel(
    val showPosition: String,
    val rateColor: RateColor,
    val row: Int,
    val col: Int,
    val state: SeatState = SeatState.NONE,
) {
    constructor(row: Int, col: Int, tier: Tier) : this(
        positionFormat(row, col),
        color(tier),
        row,
        col,
    )

    fun changeState(): SeatUiModel = SeatUiModel(showPosition, rateColor, row, col, state.toggle())

    companion object {
        private fun positionFormat(
            row: Int,
            col: Int,
        ): String {
            val rowLetter = 'A' + row
            return "$rowLetter${col + 1}"
        }

        private fun color(rate: Tier): RateColor =
            when (rate) {
                Tier.S -> RateColor.GREEN
                Tier.A -> RateColor.BLUE
                Tier.B -> RateColor.PURPLE
            }
    }
}
