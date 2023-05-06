package woowacourse.movie.model.mapper

import android.net.Uri
import com.example.domain.model.Movie
import woowacourse.movie.model.MovieState

fun MovieState.asDomain(): Movie = Movie(
    imgUri.toString(),
    title,
    startDate,
    endDate,
    screeningTimes,
    runningTime,
    description
)

fun Movie.asPresentation(): MovieState =
    MovieState(
        Uri.parse(imgUri),
        title,
        startDate,
        endDate,
        screeningTimes,
        runningTime,
        description
    )
