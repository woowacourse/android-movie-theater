package woowacourse.movie.screeningmovie

import woowacourse.movie.model.Advertisement
import woowacourse.movie.model.Movie
import woowacourse.movie.screeningmovie.uimodel.AdvertisementUiModel
import woowacourse.movie.screeningmovie.uimodel.ScreenMovieUiModel
import java.time.format.DateTimeFormatter

fun Movie.toScreenMovieUiModel(): ScreenMovieUiModel {
    val pattern = "yyyy.MM.dd"
    val formatter = DateTimeFormatter.ofPattern(pattern)
    return ScreenMovieUiModel(
        id = id,
        title = title,
        screenDate = "러닝타임: ${runningTime.time.inWholeMinutes}분",
        runningTime = "상영일: ${startDate.format(formatter)} ~ ${endDate.format(formatter)}",
    )
}

fun Advertisement.toAdvertisementUiModel(): AdvertisementUiModel {
    return AdvertisementUiModel()
}
