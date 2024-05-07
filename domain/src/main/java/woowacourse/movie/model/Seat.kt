package woowacourse.movie.model

data class Seat(
    val rate: SeatRate,
    val row: Int,
    val col: Int,
    val state: SeatState = SeatState.NONE,
) {
    val price = rate.price.price

    fun changeState(): Seat = Seat(rate, row, col, state.reserveState())
}
