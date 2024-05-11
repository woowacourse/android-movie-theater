package woowacourse.movie.domain.mapper

import woowacourse.movie.data.database.movie.MovieContentEntity
import woowacourse.movie.domain.MovieContent
import java.time.LocalDate

fun MovieContentEntity.toMovieContent(): MovieContent =
    MovieContent(
        id = id,
        imageId = imageId,
        title = title,
        openingMovieDate = LocalDate.parse(openingMovieDate),
        endingMoviesDate = LocalDate.parse(endingMoviesDate),
        runningTime = runningTime,
        synopsis = synopsis,
        theaterIds = theaterIds,
    )
