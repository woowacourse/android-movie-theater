package woowacourse.domain.theater

interface TheaterRepository {
    fun getTheaters(): List<Theater>

    fun getTheater(theaterId: Long): Theater?

    fun addTheater(theaterName: String, screeningMovies: List<ScreeningMovie>, seatStructure: SeatStructure)
}
