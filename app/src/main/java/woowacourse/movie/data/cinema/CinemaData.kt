package woowacourse.movie.data.cinema

import woowacourse.movie.data.reservation.LocalScreeningData
import woowacourse.movie.data.reservation.ScreeningData
import woowacourse.movie.domain.cinema.Cinema
import woowacourse.movie.domain.reservation.DefaultShowtimePolicy

interface CinemaData {
    val value: List<Cinema>
}

class LocalCinemaData(
    screeningData: ScreeningData = LocalScreeningData(),
) : CinemaData {
    override val value: List<Cinema> =
        listOf(
            Cinema(
                "선릉 극장",
                screeningData.value,
                DefaultShowtimePolicy(),
            ),
            Cinema(
                "잠실 극장",
                screeningData.value,
                DefaultShowtimePolicy(),
            ),
            Cinema(
                "강남 극장",
                screeningData.value,
                DefaultShowtimePolicy(),
            ),
        )
}
