package woowacourse.movie.data

import woowacourse.movie.domain.model.Screening
import java.time.LocalTime

object ScreeningData {
    val values: List<Screening> =
        listOf(
            Screening(
                TheaterData.SEOLLEUNG,
                MovieData.HARRY_POTTER_01,
                listOf(9, 11, 15).map { LocalTime.of(it, 0) },
            ),
            Screening(
                TheaterData.JAMSIL,
                MovieData.HARRY_POTTER_01,
                listOf(10, 12, 14, 17, 20, 22).map { LocalTime.of(it, 0) },
            ),
            Screening(
                TheaterData.GANGNAM,
                MovieData.HARRY_POTTER_01,
                listOf(15, 22).map { LocalTime.of(it, 0) },
            ),
            Screening(
                TheaterData.SEOLLEUNG,
                MovieData.HARRY_POTTER_02,
                listOf(12, 16, 20).map { LocalTime.of(it, 0) },
            ),
        )
}
