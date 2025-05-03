package woowacourse.movie.model

class Seats(values: Set<Seat> = setOf()) {
    private val _seats: MutableSet<Seat> = values.toMutableSet()
    val seats: Set<Seat> get() = _seats.toSet()

    val amount: Int
        get() = _seats.sumOf { it.grade.price }

    fun has(seat: Seat): Boolean {
        return _seats.contains(seat)
    }

    operator fun minus(seat: Seat) {
        _seats.remove(seat)
    }

    operator fun plus(seat: Seat) {
        _seats.add(seat)
    }
}
