package woowacourse.movie.domain.model.seat

data class Seat(val row: String, val number: Int, val seatGrade: SeatGrade) {
    override fun toString(): String = "$row$number"
}
