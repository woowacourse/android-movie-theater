package woowacourse.movie.domain.model

import woowacourse.movie.data.model.MovieData
import woowacourse.movie.ui.MovieDetailUI
import woowacourse.movie.ui.MoviePreviewUI

data class Movie(
    val id: Int,
    val title: String,
    val runningTime: Int,
    val description: String,
    val poster: Image<Any>,
) {
    companion object {
        val NULL =
            Movie(
                id = -1,
                title = "",
                runningTime = -1,
                description = "",
                poster = Image.StringImage(""),
            )
    }
}

fun Movie.toPreviewUI() =
    MoviePreviewUI(
        title = title,
        runningTime = runningTime,
        image = poster,
    )

fun Movie.toDetailUi() =
    MovieDetailUI(
        title = title,
        runningTime = runningTime,
        description = description,
        image = poster,
    )

fun Movie.toData() =
    MovieData(
        id = id,
        title = title,
        runningTime = runningTime,
        description = description,
    )
