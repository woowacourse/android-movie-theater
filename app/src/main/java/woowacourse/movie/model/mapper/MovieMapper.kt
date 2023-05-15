package woowacourse.movie.model.mapper

import com.example.domain.model.Movie
import woowacourse.movie.data.MovieImageMapper
import woowacourse.movie.model.MovieState

fun MovieState.asDomain(): Movie =
    Movie(movieId, title, startDate, endDate, runningTime, description)

fun Movie.asPresentation(): MovieState =
    MovieState(
        MovieImageMapper.mapper(movieId),
        movieId,
        title,
        startDate,
        endDate,
        runningTime,
        description
    )
