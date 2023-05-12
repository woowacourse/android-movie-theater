package woowacourse.movie.presentation.mapper

import androidx.annotation.DrawableRes
import woowacourse.movie.domain.model.movie.DomainMovie
import woowacourse.movie.presentation.model.movieitem.Movie

fun Movie.toDomain(): DomainMovie =
    DomainMovie(id, title, startDate, endDate, runningTime, introduce, thumbnail)

fun DomainMovie.toPresentation(@DrawableRes thumbnailResId: Int): Movie =
    Movie(
        id = id,
        title = title,
        startDate = startDate,
        endDate = endDate,
        runningTime = runningTime,
        introduce = introduce,
        thumbnail = thumbnailResId
    )
