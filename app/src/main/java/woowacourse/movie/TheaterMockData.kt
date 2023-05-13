package woowacourse.movie

import woowacourse.movie.movie.MovieMockData

object TheaterMockData {
    val dummyTheaters = listOf(
        Theater(
            name = "이름이 어어어어어엄청나게 긴 극장",
            screeningMovies = MovieMockData.movies.toMutableList().subList(1, 5).toList(),
            screenTimes = mapOf(
                MovieMockData.movies[1] to listOf("13:00", "15:00"),
                MovieMockData.movies[2] to listOf("14:00", "16:00", "18:00"),
                MovieMockData.movies[3] to listOf("10:00", "14:00", "18:00"),
                MovieMockData.movies[4] to listOf("10:00", "14:00", "18:00"),
            )
        ),
        Theater(
            name = "선릉 극장",
            screeningMovies = MovieMockData.movies.toMutableList().subList(2, 3).toList(),
            screenTimes = mapOf(
                MovieMockData.movies[2] to listOf("14:00", "16:00", "18:00", "20:00"),
            )
        ),
        Theater(
            name = "잠실 극장",
            screeningMovies = MovieMockData.movies.toMutableList().subList(0, 2).toList(),
            screenTimes = mapOf(
                MovieMockData.movies[0] to listOf("13:00", "15:00", "17:00", "19:00", "21:00"),
                MovieMockData.movies[1] to listOf("12:00", "14:00", "16:00", "18:00", "20:00"),
            )
        ),
    )
}
