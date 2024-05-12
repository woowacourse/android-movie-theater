package woowacourse.movie.moviedetail.uimodel

import woowacourse.movie.model.HeadCount
import woowacourse.movie.model.ScreeningSchedule

fun ScreeningSchedule.toMovieDetailUiModel(): MovieDetailUiModel =
    MovieDetailUiModel(
        movie.title,
        startDate,
        endDate,
        movie.description,
        movie.runningTime.time,
    )

fun HeadCount.toHeadCountUiModel(): HeadCountUiModel = HeadCountUiModel(count)

fun HeadCountUiModel.toHeadCount(): HeadCount = HeadCount(count.toInt())

fun ScreeningSchedule.toScheduleUiModels(): ScheduleUiModels =
    ScheduleUiModels(
        schedules.map {
            ScheduleUiModel(
                it.date,
                it.times,
            )
        },
    )
