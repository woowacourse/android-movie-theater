package woowacourse.domain.theater

interface TheaterRepository {
    fun getTheaters(): List<Theater>

    fun getTheater(theaterId: Long): Theater?
}
