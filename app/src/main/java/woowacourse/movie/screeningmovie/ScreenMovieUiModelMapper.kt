package woowacourse.movie.screeningmovie

import woowacourse.movie.model.Advertisement
import woowacourse.movie.model.Movie
import woowacourse.movie.model.ScreenView
import java.time.format.DateTimeFormatter

fun Movie.toScreenMovieUiModel(
): ScreenMovieUiModel {
    val pattern = "yyyy.MM.dd"
    val formatter = DateTimeFormatter.ofPattern(pattern)
    return ScreenMovieUiModel(
        id = id,
        title = title,
        screenDate = "러닝타임: ${runningTime.time.inWholeMinutes}분",
        runningTime = "상영일: ${startDate.format(formatter)} ~ ${endDate.format(formatter)}",
    )
}

fun List<ScreenView>.toScreenItems(): List<ScreeningItem> =
    this.map { screenView ->
        when (screenView) {
            is Movie -> screenView.toScreenMovieUiModel()
            is Advertisement -> AdvertiseUiModel()
        }
    }
