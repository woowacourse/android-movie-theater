package woowacourse.movie.moviereservation

import woowacourse.movie.model.HeadCount
import woowacourse.movie.model.Screening
import woowacourse.movie.moviereservation.uimodel.HeadCountUiModel
import woowacourse.movie.moviereservation.uimodel.MovieReservationUiModel
import woowacourse.movie.moviereservation.uimodel.ScreeningDateTimeUiModel
import woowacourse.movie.moviereservation.uimodel.ScreeningDateTimesUiModel

fun Screening.toMovieReservationUiModel(): MovieReservationUiModel =
    MovieReservationUiModel(
        id,
        movie.title,
        startDate,
        endDate,
        movie.description,
        movie.runningTime.time,
    )

fun HeadCount.toHeadCountUiModel(): HeadCountUiModel = HeadCountUiModel(count)

fun HeadCountUiModel.toHeadCount(): HeadCount = HeadCount(count.toInt())

fun Screening.toScreeningDateTimeUiModel(): ScreeningDateTimesUiModel =
    ScreeningDateTimesUiModel(
        screeningDateTimes.map {
            ScreeningDateTimeUiModel(
                it.date,
                it.times,
            )
        },
    )
