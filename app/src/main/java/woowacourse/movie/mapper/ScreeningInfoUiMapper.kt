package woowacourse.movie.mapper

import woowacourse.movie.model.ScreeningInfo
import woowacourse.movie.ui.model.ScreeningInfoUiModel

fun ScreeningInfo.toUiModel(): ScreeningInfoUiModel {
    return ScreeningInfoUiModel(
        movie = movie.toUiModel(),
        screeningTimes = screeningTimes,
    )
}

fun ScreeningInfoUiModel.toDomain(): ScreeningInfo {
    return ScreeningInfo(
        movie = movie.toDomain(),
        screeningTimes = screeningTimes,
    )
}
