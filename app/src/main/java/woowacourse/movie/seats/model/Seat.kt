package woowacourse.movie.seats.model

import android.graphics.Color
import woowacourse.movie.seats.model.SeatsDataSource.seatTotalPrice
import java.io.Serializable

data class Seat private constructor(val rowIndex: Int, val colIndex: Int) : Serializable {
    var selected = false
        private set

    var cellBackgroundColor = Color.WHITE
        private set

    val rank: SeatRank
        get() = SeatRank.of(rowIndex)

    val coordinate: String
        get() = (rowIndex + 65).toChar() + (colIndex + 1).toString()

    fun select() {
        if (!selected) {
            selected = true
            cellBackgroundColor = Color.YELLOW
            seatTotalPrice += rank.price
            return
        }
        if (selected) {
            selected = false
            cellBackgroundColor = Color.WHITE
            seatTotalPrice -= rank.price
        }
    }

    companion object {
        val seats = mutableListOf<Seat>()

        fun of(
            rowIndex: Int,
            colIndex: Int,
        ): Seat {
            val seat = Seat(rowIndex, colIndex)
            if (seat !in seats) seats.add(seat)
            return seats.first { it == seat }
        }
    }
}
