package woowacourse.movie.screeningmovie

import woowacourse.movie.model.Advertisement
import woowacourse.movie.model.Movie
import woowacourse.movie.model.ScreenView
import java.time.format.DateTimeFormatter

fun Movie.toScreenMovieUiModel(): ScreenMovieUiModel {
    val pattern = "yyyy.MM.dd"
    val formatter = DateTimeFormatter.ofPattern(pattern)
    return ScreenMovieUiModel(
        id = id,
        title = title,
        startDate = startDate.format(formatter),
        endDate = endDate.format(formatter),
        runningTime = runningTime.time.inWholeMinutes.toInt(),
    )
}

fun List<ScreenView>.toScreenItems(): List<ScreeningItem> =
    this.map { screenView ->
        when (screenView) {
            is Movie -> screenView.toScreenMovieUiModel()
            is Advertisement -> AdvertiseUiModel()
        }
    }
