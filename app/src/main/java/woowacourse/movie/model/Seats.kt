package woowacourse.movie.model

class Seats(values: Set<Seat> = setOf()) {
    private val _seats: Set<Seat> = values.toSet()
    val seats: Set<Seat> get() = _seats.toSet()

    val amount: Int
        get() = _seats.sumOf { it.grade.price }

    fun has(seat: Seat): Boolean {
        return _seats.contains(seat)
    }

    operator fun plus(seat: Seat): Seats = Seats(_seats + seat)

    operator fun minus(seat: Seat): Seats = Seats(_seats - seat)
}
