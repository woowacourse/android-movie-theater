package woowacourse.movie.feature.home.theater.ui

import woowacourse.movie.model.Theater

class TheaterUiModel(
    val theaterName: String,
    val screeningTimeCount: Int,
    val theaterItemClick: (Int) -> Unit,
    private val theaterItemPosition: Int,
) {
    fun onTheaterItemClick() {
        theaterItemClick(theaterItemPosition)
    }

    companion object {
        fun of(
            theater: Theater,
            theaterItemClick: (Int) -> Unit,
            theaterItemPosition: Int,
        ): TheaterUiModel {
            return TheaterUiModel(
                theater.name,
                theater.screeningTimes.size,
                theaterItemClick,
                theaterItemPosition,
            )
        }
    }
}
