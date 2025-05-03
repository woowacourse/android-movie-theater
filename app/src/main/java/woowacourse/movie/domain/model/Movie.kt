package woowacourse.movie.domain.model

import woowacourse.movie.view.ReservationUiFormatter
import woowacourse.movie.view.model.MovieUiModel
import java.time.LocalDate

data class Movie(
    val title: String,
    val poster: Int,
    val startDate: LocalDate,
    val endDate: LocalDate,
    val runningTime: Int,
)

fun Movie.toUiModel(): MovieUiModel =
    MovieUiModel(
        name = this.title,
        poster = this.poster,
        startDate = ReservationUiFormatter.localDateToUI(this.startDate),
        endDate = ReservationUiFormatter.localDateToUI(this.endDate),
        runningTime = this.runningTime,
    )
