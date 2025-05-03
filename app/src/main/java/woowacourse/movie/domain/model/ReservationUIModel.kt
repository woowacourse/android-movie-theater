package woowacourse.movie.domain.model

import woowacourse.movie.view.model.MovieUiModel

data class ReservationUIModel(
    val movie: MovieUiModel,
    val movieDate: MovieDate,
    val movieTime: MovieTime,
    val ticketCount: Int,
    val theaterName: String,
)
