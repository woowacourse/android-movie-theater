package woowacourse.movie.model

import woowacourse.movie.model.movie.ScreeningTimes

class Theater(
    val theaterId: Int,
    val name: String,
    val screeningTimes: ScreeningTimes,
    val movieId: Int,
)
