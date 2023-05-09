package woowacourse.movie.model.mapper

import com.example.domain.model.Movie
import woowacourse.movie.data.MovieImageMapper
import woowacourse.movie.model.MovieState

fun MovieState.asDomain(): Movie =
    Movie(id, title, startDate, endDate, runningTime, description)

fun Movie.asPresentation(): MovieState =
    MovieState(
        id,
        MovieImageMapper.mapper(id),
        title,
        startDate,
        endDate,
        runningTime,
        description
    )
