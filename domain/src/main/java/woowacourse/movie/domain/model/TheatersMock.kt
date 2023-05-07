package woowacourse.movie.domain.model

import java.time.LocalTime

object TheatersMock {
    val movies = MovieMock.createMovies()
    fun getTheaters(): List<Theater> {
        val theaters = mutableListOf<Theater>()
        theaters.add(
            Theater.from(
                "선릉",
                listOf(
                    makeMovieRunningTime(movies[0], listOf(10, 14)),
                    makeMovieRunningTime(movies[3], listOf(12, 16))
                )
            )
        )

        theaters.add(
            Theater.from(
                "잠실",
                listOf(
                    makeMovieRunningTime(movies[0], listOf(9, 18)),
                    makeMovieRunningTime(movies[2], listOf(12, 16))
                )
            )
        )
        return theaters
    }

    private fun makeMovieRunningTime(movie: Movie, times: List<Int>): MovieRunningTime {
        return MovieRunningTime(movie, times.map { LocalTime.of(it, 0) })
    }
}
