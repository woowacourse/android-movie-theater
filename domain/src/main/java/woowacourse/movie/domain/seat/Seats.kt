package woowacourse.movie.domain.seat

data class Seats(val value: List<Seat>) {
    companion object {
        fun from(row: Int, column: Int): Seats {
            return Seats(
                (0 until row * column).map {
                    val y = it / column
                    val x = it % column
                    Seat(MovieSeatRow(y), x)
                }
            )
        }
    }
}
