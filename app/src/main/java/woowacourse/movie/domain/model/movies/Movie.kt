package woowacourse.movie.domain.model.movies

import woowacourse.movie.domain.model.booking.ScreeningDates

class Movie(
    val id: Int,
    val title: String,
    val posterResource: String,
    val screeningDates: ScreeningDates,
    val runningTime: Int,
)
