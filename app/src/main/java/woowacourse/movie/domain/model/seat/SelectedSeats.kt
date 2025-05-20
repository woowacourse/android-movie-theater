package woowacourse.movie.domain.model.seat

import java.io.Serializable

class SelectedSeats(
    private val headCount: Int,
    private val _seats: MutableSet<Seat> = mutableSetOf(),
) : Serializable {
    val value: List<Seat>
        get() = _seats.toList()

    fun updateSelection(seat: Seat) {
        if (isSelected(seat)) {
            unselect(seat)
            return
        }
        select(seat)
    }

    fun getTotalPrice(): Int = _seats.sumOf { it.grade.price }

    fun isFull(): Boolean = _seats.size == headCount

    fun isSelected(seat: Seat): Boolean = _seats.contains(seat)

    private fun select(seat: Seat) {
        require(_seats.size < headCount) {
            SELECT_MESSAGE.format(headCount)
        }
        _seats.add(seat)
    }

    private fun unselect(seat: Seat) {
        _seats.remove(seat)
    }

    companion object {
        private const val SELECT_MESSAGE = "좌석은 %d개만 선택할 수 있습니다."
    }
}
