package woowacourse.movie.data.movie

import woowacourse.movie.model.ImageUrl
import woowacourse.movie.model.Movie
import woowacourse.movie.model.RunningTime
import java.time.LocalDate
import kotlin.time.Duration

fun Movie.toDto(): MovieDto =
    MovieDto(
        id,
        title,
        description,
        imageUrl.url,
        startDate.toString(),
        endDate.toString(),
        runningTime.time.toString(),
    )

fun MovieDto.toMovie(): Movie =
    Movie(
        id,
        title,
        description,
        ImageUrl(imageUrl),
        LocalDate.parse(startDate),
        LocalDate.parse(endDate),
        RunningTime(Duration.parse(runningTime)),
    )
