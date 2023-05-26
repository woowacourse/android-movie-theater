package woowacourse.movie.data.model.uimodel

import java.io.Serializable

class SeatUIModel(val row: Char, val col: Int) : UIModel, Serializable {
    companion object {
        fun toNumber(alphabet: Char): Int {
            return alphabet - 'A' + 1
        }

        fun toChar(rowNumber: Int): Char {
            return (rowNumber + 'A'.code).toChar()
        }
    }
}
