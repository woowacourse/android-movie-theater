package woowacourse.movie.detail.model

import woowacourse.movie.common.MovieDataSource
import java.time.LocalDate
import java.time.LocalTime

object DetailDataResource {
    var movieId: Long = 0

    var theaterId: Long = 0

    private val firstScreeningDate
        get() = MovieDataSource.movieList.first { it.id == movieId }.firstScreeningDate

    val screeningDates
        get() =
            List(firstScreeningDate.lengthOfMonth()) {
                LocalDate.of(2024, firstScreeningDate.monthValue, it + 1)
            }

    val screeningTimesWeekends =
        listOf(
            LocalTime.of(9, 0, 0),
            LocalTime.of(11, 0, 0),
            LocalTime.of(13, 0, 0),
            LocalTime.of(15, 0, 0),
            LocalTime.of(17, 0, 0),
            LocalTime.of(19, 0, 0),
            LocalTime.of(21, 0, 0),
            LocalTime.of(23, 0, 0),
        )

    val screeningTimesWeekdays =
        listOf(
            LocalTime.of(10, 0, 0),
            LocalTime.of(12, 0, 0),
            LocalTime.of(14, 0, 0),
            LocalTime.of(16, 0, 0),
            LocalTime.of(18, 0, 0),
            LocalTime.of(20, 0, 0),
            LocalTime.of(22, 0, 0),
            LocalTime.of(0, 0, 0),
        )
//    val screeningTimesWeekends =
//        List(8) { index ->
//            LocalTime.of(9 + index * 2, 0, 0)
//        }
//
//    val screeningTimesWeekdays =
//        List(8) { index ->
//            if (index != 7) {
//                LocalTime.of(10 + index * 2, 0, 0)
//            } else {
//                LocalTime.of(0, 0, 0)
//            }
//        }

    var selectedScreeningTime: LocalTime = LocalTime.of(0, 0, 0)
}
