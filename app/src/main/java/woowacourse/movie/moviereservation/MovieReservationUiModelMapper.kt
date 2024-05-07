package woowacourse.movie.moviereservation

import woowacourse.movie.model.ScreeningMovie
import woowacourse.movie.moviereservation.uimodel.MovieReservationUiModel
import woowacourse.movie.moviereservation.uimodel.ScreeningDateTimeUiModel
import woowacourse.movie.moviereservation.uimodel.ScreeningDateTimesUiModel
import java.time.format.DateTimeFormatter

fun ScreeningMovie.toMovieReservationUiModel(): MovieReservationUiModel =
    MovieReservationUiModel(
        id = id,
        title = movie.title,
        startDate = startDate.format(dateFormatter),
        endDate = endDate.format(dateFormatter),
        description = movie.description,
        runningTime = movie.runningTime.time.inWholeMinutes.toInt(),
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

private val dateFormatter = DateTimeFormatter.ofPattern("yyyy.MM.dd")
