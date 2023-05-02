package woowacourse.movie.model

import java.io.Serializable

class SeatUiModel(val row: Char, val col: Int) : UiModel, Serializable {
    companion object {
        fun toNumber(alphabet: Char): Int {
            return (alphabet - 'A') + rowStartNumber
        }

        fun toChar(rowNumber: Int): Char {
            return (rowNumber + 'A'.code - rowStartNumber).toChar()
        }

        private const val rowStartNumber = 1
    }
}
