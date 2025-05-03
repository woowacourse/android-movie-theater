package woowacourse.movie.domain.fixture

import woowacourse.movie.domain.model.theater.Theater

val gangNamTheaterFixture =
    Theater(
        "강남 극장",
        listOf(
            screeningFixtureWithMovieId1,
            screeningFixtureWithMovieId2,
        ),
    )

val seoulTheaterFixture =
    Theater(
        "서울 극장",
        listOf(
            screeningFixtureWithMovieId1,
            screeningFixtureWithMovieId2,
            screeningFixtureWithMovieId3,
        ),
    )

val suwonTheaterFixture =
    Theater(
        "수원 극장",
        listOf(
            screeningFixtureWithMovieId2,
            screeningFixtureWithMovieId3,
            screeningFixtureWithMovieId4,
            screeningFixtureWithMovieId5,
        ),
    )

val allTheaters =
    listOf(
        gangNamTheaterFixture,
        seoulTheaterFixture,
        suwonTheaterFixture,
    )
