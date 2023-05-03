package woowacourse.movie.presentation.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
class PickedSeats(val seats: List<Seat> = emptyList()) : Parcelable {
    fun sorted(): PickedSeats = PickedSeats(seats.sorted())

    fun indices(colSize: Int): List<Int> = seats.map { it.toIndex(colSize) }

    override fun toString(): String =
        seats.joinToString(", ") { it.toString() }
}
