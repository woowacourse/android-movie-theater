package woowacourse.movie.model.mapper

import com.example.domain.model.Movie
import java.time.LocalTime
import woowacourse.movie.model.MovieState

fun MovieState.asDomain(): Movie = Movie(
    imgId,
    title,
    startDate,
    endDate,
    screeningTimes.map { toString() },
    runningTime,
    description
)

fun Movie.asPresentation(): MovieState =
    MovieState(
        imgId,
        title,
        startDate,
        endDate,
        screeningTimes.map(LocalTime::parse),
        runningTime,
        description
    )
