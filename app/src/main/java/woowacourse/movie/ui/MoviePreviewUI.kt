package woowacourse.movie.ui

import woowacourse.movie.domain.model.Image
import woowacourse.movie.domain.model.Movie

data class MoviePreviewUI(
    val title: String,
    val runningTime: Int,
    val image: Image<Any>,
)

fun Movie.toPreviewUI(image: Image<Any>) =
    MoviePreviewUI(
        title = title,
        image = image,
        runningTime = runningTime,
    )