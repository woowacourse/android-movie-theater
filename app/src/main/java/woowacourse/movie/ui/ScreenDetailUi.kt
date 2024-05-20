package woowacourse.movie.ui

import woowacourse.movie.data.model.ScreenData
import woowacourse.movie.domain.model.DateRange
import woowacourse.movie.domain.model.Image

data class ScreenDetailUi(
    val id: Int,
    val movieDetailUI: MovieDetailUI,
    val dateRange: DateRange,
)

fun ScreenData.toDetailUI(image: Image<Any>) =
    ScreenDetailUi(
        id = id,
        movieDetailUI =
            MovieDetailUI(
                title = movie.title,
                runningTime = movie.runningTime,
                description = movie.description,
                image = image,
            ),
        dateRange = dateRange,
    )
