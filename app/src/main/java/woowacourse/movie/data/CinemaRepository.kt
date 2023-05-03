package woowacourse.movie.data

import woowacourse.movie.model.CinemaState

object CinemaRepository {
    private val cinemas: List<CinemaState> = listOf(
        CinemaState("선릉", 3),
        CinemaState("잠실", 10),
        CinemaState("강남", 2)
    )

    fun allCinema(): List<CinemaState> = cinemas.toList()
}
