package woowacourse.movie.moviedetail.uimodel

import woowacourse.movie.model.HeadCount
import woowacourse.movie.model.Screening

fun Screening.toMovieReservationUiModel(): ReservationPlanUiModel =
    ReservationPlanUiModel(
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
