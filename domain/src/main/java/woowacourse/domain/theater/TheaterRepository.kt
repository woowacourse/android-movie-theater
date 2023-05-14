package woowacourse.domain.theater

import woowacourse.domain.util.CgvResult

interface TheaterRepository {
    fun getTheaters(): List<Theater>

    fun getTheater(theaterId: Long): CgvResult<Theater>

    fun addTheater(
        theaterName: String,
        screeningMovies: List<ScreeningMovie>,
        seatStructure: SeatStructure,
    )
}
