package woowacourse.movie.repository

import com.example.domain.repository.MovieRepository
import woowacourse.movie.model.CinemaState
import woowacourse.movie.model.MovieState
import woowacourse.movie.model.mapper.asPresentation

object CinemaRepository {
    private val dummyMovies = MovieRepository.allMovies()
    private val dummyMovies2 = MovieRepository.allMovies2()
    private val cinemas: List<CinemaState> = listOf(
        CinemaState("선릉aaaaaaaaaaaaaaa", dummyMovies.map { it.asPresentation() }),
        CinemaState("잠실", dummyMovies.map { it.asPresentation() }),
        CinemaState("강남", dummyMovies2.map { it.asPresentation() }),
        CinemaState("강남", emptyList())
    )

    fun allCinema(): List<CinemaState> = cinemas.toList()

    fun findCinema(movie: MovieState): List<CinemaState> {
        return cinemas.filter { it.movies.contains(movie) }
    }
}
