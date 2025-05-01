package woowacourse.movie.mapper

import woowacourse.movie.model.Schedule
import woowacourse.movie.ui.model.ScheduleUiModel

fun Schedule.toUiModel(): ScheduleUiModel {
    return ScheduleUiModel(
        movie = movie.toUiModel(),
        screeningTimes = screeningTimes,
    )
}

fun ScheduleUiModel.toDomain(): Schedule {
    return Schedule(
        movie = movie.toDomain(),
        screeningTimes = screeningTimes,
    )
}
