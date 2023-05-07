package woowacourse.movie.model.mapper

import com.example.domain.model.ScreeningMovieTimes
import woowacourse.movie.model.ScreeningMovieTimesState

fun ScreeningMovieTimesState.asDomain(): ScreeningMovieTimes = ScreeningMovieTimes(
    movie.asDomain(),
    screeningDateTimes.toList()
)

fun ScreeningMovieTimes.asPresentation(): ScreeningMovieTimesState = ScreeningMovieTimesState(
    movie.asPresentation(),
    screeningDateTimes.toList()
)
