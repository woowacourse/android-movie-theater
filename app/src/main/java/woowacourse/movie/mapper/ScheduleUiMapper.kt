package woowacourse.movie.mapper

import woowacourse.movie.model.ScreeningInfo
import woowacourse.movie.ui.model.ScheduleUiModel

fun ScreeningInfo.toUiModel(): ScheduleUiModel {
    return ScheduleUiModel(
        movie = movie.toUiModel(),
        screeningTimes = screeningTimes,
    )
}
