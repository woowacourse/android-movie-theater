package woowacourse.movie.data

import woowacourse.movie.model.CinemaState
import woowacourse.movie.model.MovieState

object CinemaRepository {
    private val dummyMovies = MovieRepository.allMovies()
    private val dummyMovies2 = MovieRepository.allMovies2()
    private val cinemas: List<CinemaState> = listOf(
        CinemaState("선릉", dummyMovies),
        CinemaState("잠실", dummyMovies),
        CinemaState("강남", dummyMovies2),
        CinemaState("강남", emptyList())
    )

    fun allCinema(): List<CinemaState> = cinemas.toList()

    fun findCinema(movie: MovieState): List<CinemaState> {
        return cinemas.filter { it.movies.contains(movie) }
    }
}
