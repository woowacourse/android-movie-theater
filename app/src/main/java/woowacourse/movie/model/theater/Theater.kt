package woowacourse.movie.model.theater

import woowacourse.movie.model.movie.ScreeningTimes

class Theater(
    val theaterId: Int,
    val name: String,
    val screeningTimes: ScreeningTimes,
    val movieId: Int,
)
