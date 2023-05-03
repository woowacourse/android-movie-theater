package woowacourse.movie.view.model

class SeatUiModel(row: Int, col: Int) : java.io.Serializable {
    val seatId: String = ('A'.code + row).toChar() + (col + 1).toString()
}
