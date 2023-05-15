package woowacourse.movie.view.activities.seatselection

import woowacourse.movie.R
import woowacourse.movie.domain.theater.Point
import woowacourse.movie.domain.theater.SeatClass
import woowacourse.movie.domain.theater.Theater

data class SeatsUIState(val seats: List<List<SeatUIState>>) {

    companion object {
        fun from(theater: Theater): SeatsUIState {
            return SeatsUIState(createSeatUIStates(theater))
        }

        private fun createSeatUIStates(theater: Theater): List<List<SeatUIState>> {
            fun createSeatName(row: Int, column: Int): String = ('A' - 1 + row).toString() + column

            fun getColorResourceId(seatClass: SeatClass): Int = when (seatClass) {
                SeatClass.S -> R.color.s_class_color
                SeatClass.A -> R.color.a_class_color
                SeatClass.B -> R.color.b_class_color
            }

            val seatUIStates =
                List(theater.seats.keys.maxOf(Point::row)) { mutableListOf<SeatUIState>() }
            theater.seats.entries.sortedWith { entry1, entry2 ->
                val point1 = entry1.key
                val point2 = entry2.key
                if (point1.row == point2.row) point1.column.compareTo(point2.column)
                else point1.row.compareTo(point2.row)
            }.forEach {
                seatUIStates[it.key.row - 1].add(
                    SeatUIState(createSeatName(it.key.row, it.key.column), getColorResourceId(it.value))
                )
            }
            return seatUIStates
        }
    }
}