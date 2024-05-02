package woowacourse.movie.domain.model

data class Seats(
    val seats: List<Seat>,
) {
    constructor(vararg seat: Seat) : this(seat.toList())

    fun add(seat: Seat): Seats = Seats(seats + seat)

    fun remove(seat: Seat): Seats = Seats(seats.filter { it != seat })

    fun totalPrice(): Int = seats.sumOf { it.price() }

    fun count(): Int = seats.size

    fun maxColumn(): Int = seats.maxOf { it.position.col + 1 }

    fun maxRow(): Int = seats.maxOf { it.position.row + 1 }

    fun findSeat(position: Position): Seat =
        seats.find {
            it.position == position
        } ?: throw NoSuchElementException("there is no seat in $position")
}
