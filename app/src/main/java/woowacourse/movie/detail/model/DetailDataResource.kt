package woowacourse.movie.detail.model

import woowacourse.movie.common.CommonDataSource
import java.time.LocalDate
import java.time.LocalTime

object DetailDataResource {
    var movieId: Long = 0

    var theaterId: Long = 0

    private val firstScreeningDate
        get() = CommonDataSource.movieList.first { it.id == movieId }.firstScreeningDate

    val screeningDates
        get() =
            List(firstScreeningDate.lengthOfMonth()) {
                LocalDate.of(2024, firstScreeningDate.monthValue, it + 1)
            }

    var selectedScreeningTime: LocalTime = LocalTime.of(0, 0, 0)
}
