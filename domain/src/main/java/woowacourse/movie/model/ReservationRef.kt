package woowacourse.movie.model

data class ReservationRef(
    val id: Long,
    val screeningId: Long,
    val seats: String,
) {
    companion object {
        val STUB =
            ReservationRef(
                1,
                1,
                "A|1|1",
            )
    }
}
