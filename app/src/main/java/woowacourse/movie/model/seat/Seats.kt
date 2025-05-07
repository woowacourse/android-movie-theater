package woowacourse.movie.model.seat

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import woowacourse.movie.model.seat.grade.SeatGradePolicy
import woowacourse.movie.model.seat.index.Col
import woowacourse.movie.model.seat.index.Row

@JvmInline
@Parcelize
value class Seats private constructor(
    private val _seats: MutableSet<Seat>,
) : Parcelable {
    val value: List<Seat>
        get() = _seats.toList()

    val size: Int
        get() = _seats.size

    fun getTotalPrice(seatGradePolicy: SeatGradePolicy): Int = _seats.sumOf { it.getGrade(seatGradePolicy).ticketPrice }

    fun add(
        row: Row,
        col: Col,
    ): Boolean = _seats.add(Seat(row, col))

    fun click(
        row: Row,
        col: Col,
    ): Boolean =
        if (contains(row, col)) {
            remove(row, col)
            false
        } else {
            add(row, col)
            true
        }

    fun contains(
        row: Row,
        col: Col,
    ): Boolean = _seats.any { it.row == row && it.col == col }

    private fun remove(
        row: Row,
        col: Col,
    ): Boolean = _seats.removeIf { it.row == row && it.col == col }

    companion object {
        fun create(): Seats = Seats(mutableSetOf())
    }
}
