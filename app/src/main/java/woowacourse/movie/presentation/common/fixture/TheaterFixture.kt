package woowacourse.movie.presentation.common.fixture

import woowacourse.movie.domain.model.cinema.MovieSchedule
import woowacourse.movie.domain.model.cinema.Theater
import woowacourse.movie.domain.model.cinema.Theaters

private fun createDummyMovieSchedule(movieId: Int): MovieSchedule =
    MovieSchedule.createDummy(
        dummyMovie.copy(id = movieId),
    )

val dummySeolleungTheater =
    Theater(
        "선릉 극장",
        listOf(
            createDummyMovieSchedule(1),
            createDummyMovieSchedule(2),
            createDummyMovieSchedule(3),
            createDummyMovieSchedule(4),
        ),
    )

val dummyJamSilTheater =
    Theater(
        "잠실 극장",
        listOf(
            createDummyMovieSchedule(3),
            createDummyMovieSchedule(4),
            createDummyMovieSchedule(5),
            createDummyMovieSchedule(6),
        ),
    )

val dummyGangNamTheater =
    Theater(
        "강남 극장",
        listOf(
            createDummyMovieSchedule(5),
            createDummyMovieSchedule(6),
            createDummyMovieSchedule(7),
            createDummyMovieSchedule(8),
        ),
    )

val dummyTheaters = Theaters(listOf(dummySeolleungTheater, dummyJamSilTheater, dummyGangNamTheater))
