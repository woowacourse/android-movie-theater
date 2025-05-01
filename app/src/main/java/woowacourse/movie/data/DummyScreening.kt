package woowacourse.movie.data

import woowacourse.movie.domain.model.Cinema
import woowacourse.movie.domain.model.Screening
import java.time.LocalTime

object DummyScreening {
    val dummyScreenings =
        listOf(
            Screening(
                listOf(LocalTime.of(9, 0, 0), LocalTime.of(11, 0, 0)),
                Cinema(1, "선릉"),
                DummyMovie.dummyMovie[0],
            ),
            Screening(
                listOf(LocalTime.of(10, 0, 0), LocalTime.of(12, 0, 0)),
                Cinema(2, "잠실"),
                DummyMovie.dummyMovie[0],
            ),
            Screening(
                listOf(LocalTime.of(13, 0, 0), LocalTime.of(15, 0, 0)),
                Cinema(3, "강남"),
                DummyMovie.dummyMovie[0],
            ),
            Screening(
                listOf(LocalTime.of(9, 0, 0), LocalTime.of(11, 0, 0)),
                Cinema(1, "선릉"),
                DummyMovie.dummyMovie[1],
            ),
            Screening(
                listOf(LocalTime.of(10, 0, 0), LocalTime.of(12, 0, 0)),
                Cinema(2, "잠실"),
                DummyMovie.dummyMovie[1],
            ),
            Screening(
                listOf(LocalTime.of(13, 0, 0), LocalTime.of(15, 0, 0)),
                Cinema(3, "강남"),
                DummyMovie.dummyMovie[1],
            ),
            Screening(
                listOf(LocalTime.of(9, 0, 0), LocalTime.of(11, 0, 0)),
                Cinema(1, "선릉"),
                DummyMovie.dummyMovie[2],
            ),
            Screening(
                listOf(LocalTime.of(10, 0, 0), LocalTime.of(12, 0, 0)),
                Cinema(2, "잠실"),
                DummyMovie.dummyMovie[2],
            ),
            Screening(
                listOf(LocalTime.of(13, 0, 0), LocalTime.of(15, 0, 0)),
                Cinema(3, "강남"),
                DummyMovie.dummyMovie[2],
            ),
        )
}
