package woowacourse.movie.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
class Seats private constructor(
    private val _seats: MutableSet<Seat>,
) : Parcelable {
    val value: List<Seat>
        get() = _seats.toList()

    val size: Int
        get() = _seats.size

    val totalPrice: Int
        get() = _seats.sumOf { it.price }

    fun labels(): List<String> = _seats.map { it.label }

    fun add(label: String): Boolean = _seats.add(Seat(label))

    fun click(label: String): Boolean =
        if (contains(label)) {
            remove(label)
            false
        } else {
            add(label)
            true
        }

    fun contains(label: String): Boolean = _seats.any { it.label == label }

    private fun remove(label: String): Boolean = _seats.removeIf { it.label == label }

    companion object {
        fun create(): Seats = Seats(mutableSetOf())
    }
}
