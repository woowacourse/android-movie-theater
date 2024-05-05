package woowacourse.movie.ui

import woowacourse.movie.domain.model.DateRange
import woowacourse.movie.domain.model.Image
import woowacourse.movie.domain.model.Screen

data class ScreenDetailUi(
    val id: Int,
    val movieDetailUI: MovieDetailUI,
    val dateRange: DateRange,
)


fun Screen.toDetailUI(image: Image<Any>) =
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
