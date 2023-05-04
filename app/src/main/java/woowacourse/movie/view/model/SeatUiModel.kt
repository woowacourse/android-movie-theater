package woowacourse.movie.view.model

class SeatUiModel(val row: Int, val col: Int) : java.io.Serializable {
    val seatId: String = ('A'.code + row).toChar() + (col + 1).toString()
}
