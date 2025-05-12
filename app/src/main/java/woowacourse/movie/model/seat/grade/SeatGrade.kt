package woowacourse.movie.model.seat.grade

enum class SeatGrade(
    val ticketPrice: Int,
) {
    B(10_000),
    S(15_000),
    A(12_000),
}
