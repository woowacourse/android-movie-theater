package woowacourse.movie.ui

import woowacourse.movie.data.model.MovieData
import woowacourse.movie.domain.model.Image

data class MoviePreviewUI(
    val title: String,
    val runningTime: Int,
    val image: Image<Any>,
)

fun MovieData.toPreviewUI(image: Image<Any>) =
    MoviePreviewUI(
        title = title,
        image = image,
        runningTime = runningTime,
    )
