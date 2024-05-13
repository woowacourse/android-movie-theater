package woowacourse.movie.model

data class Seats(val seats: List<Seat> = emptyList()) {
    val totalPrice = Price(seats.sumOf { it.price })
    val count = seats.size

    override fun toString(): String = seats.joinToString(",")

    init {
        require(seats.distinct().size == seats.size) {
            "중복된 좌석이 들어올 수 없습니다."
        }
    }

    companion object {
        val STUB = Seats(listOf(Seat.STUB))

        fun from(string: String): Seats {
            val seats = string.split(",").map { Seat.from(it) }
            return Seats(seats)
        }
    }
}
