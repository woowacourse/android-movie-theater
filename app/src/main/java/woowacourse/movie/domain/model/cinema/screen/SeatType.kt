package woowacourse.movie.domain.model.cinema.screen

enum class SeatType {
    S_CLASS,
    A_CLASS,
    B_CLASS,
    ;

    companion object {
        fun fromRow(row: Int): SeatType =
            when (row) {
                in 0..1 -> B_CLASS
                in 2..3 -> S_CLASS
                else -> A_CLASS
            }
    }
}
