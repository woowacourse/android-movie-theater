package woowacourse.movie.moviereservation

import woowacourse.movie.model.ScreeningMovie
import woowacourse.movie.moviereservation.uimodel.MovieReservationUiModel
import woowacourse.movie.moviereservation.uimodel.ScreeningDateTimeUiModel
import woowacourse.movie.moviereservation.uimodel.ScreeningDateTimesUiModel

fun ScreeningMovie.toMovieReservationUiModel(): MovieReservationUiModel =
    MovieReservationUiModel(
        id,
        movie.title,
        startDate,
        endDate,
        movie.description,
        movie.runningTime.time,
    )

fun ScreeningMovie.toScreeningDateTimeUiModel(): ScreeningDateTimesUiModel =
    ScreeningDateTimesUiModel(
        screenDateTimes.map {
            ScreeningDateTimeUiModel(
                it.date,
                it.times,
            )
        },
    )
