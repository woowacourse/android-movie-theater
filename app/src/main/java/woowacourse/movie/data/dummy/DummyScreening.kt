package woowacourse.movie.data.dummy

import woowacourse.movie.domain.model.Screening
import java.time.LocalTime

object DummyScreening {
    val dummyScreenings =
        listOf(
            Screening(
                listOf(LocalTime.of(9, 0, 0), LocalTime.of(11, 0, 0)),
                DummyCinema.dummyCinemas[0],
                DummyMovie.dummyMovie[0],
            ),
            Screening(
                listOf(LocalTime.of(10, 0, 0), LocalTime.of(12, 0, 0)),
                DummyCinema.dummyCinemas[1],
                DummyMovie.dummyMovie[0],
            ),
            Screening(
                listOf(LocalTime.of(13, 0, 0), LocalTime.of(15, 0, 0)),
                DummyCinema.dummyCinemas[2],
                DummyMovie.dummyMovie[0],
            ),
            Screening(
                listOf(LocalTime.of(9, 0, 0), LocalTime.of(11, 0, 0)),
                DummyCinema.dummyCinemas[1],
                DummyMovie.dummyMovie[1],
            ),
            Screening(
                listOf(LocalTime.of(10, 0, 0), LocalTime.of(12, 0, 0)),
                DummyCinema.dummyCinemas[2],
                DummyMovie.dummyMovie[1],
            ),
            Screening(
                listOf(LocalTime.of(13, 0, 0), LocalTime.of(15, 0, 0)),
                DummyCinema.dummyCinemas[2],
                DummyMovie.dummyMovie[1],
            ),
            Screening(
                listOf(LocalTime.of(9, 0, 0), LocalTime.of(11, 0, 0)),
                DummyCinema.dummyCinemas[0],
                DummyMovie.dummyMovie[2],
            ),
            Screening(
                listOf(LocalTime.of(10, 0, 0), LocalTime.of(12, 0, 0)),
                DummyCinema.dummyCinemas[1],
                DummyMovie.dummyMovie[2],
            ),
            Screening(
                listOf(LocalTime.of(13, 0, 0), LocalTime.of(15, 0, 0)),
                DummyCinema.dummyCinemas[2],
                DummyMovie.dummyMovie[3],
            ),
            Screening(
                listOf(LocalTime.of(13, 0, 0), LocalTime.of(15, 0, 0)),
                DummyCinema.dummyCinemas[2],
                DummyMovie.dummyMovie[4],
            ),
            Screening(
                listOf(LocalTime.of(13, 0, 0), LocalTime.of(15, 0, 0)),
                DummyCinema.dummyCinemas[2],
                DummyMovie.dummyMovie[5],
            ),
            Screening(
                listOf(LocalTime.of(13, 0, 0), LocalTime.of(15, 0, 0)),
                DummyCinema.dummyCinemas[2],
                DummyMovie.dummyMovie[6],
            ),
            Screening(
                listOf(LocalTime.of(13, 0, 0)),
                DummyCinema.dummyCinemas[2],
                DummyMovie.dummyMovie[6],
            ),
        )
}
