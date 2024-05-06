package woowacourse.movie.feature.home.theater.ui

import woowacourse.movie.model.Theater

class TheaterUiModel(
    val theaterName: String,
    val screeningTimeCount: Int,
) {
    companion object {
        fun of(theater: Theater): TheaterUiModel {
            return TheaterUiModel(
                theater.name,
                theater.screeningTimes.size,
            )
        }
    }
}
