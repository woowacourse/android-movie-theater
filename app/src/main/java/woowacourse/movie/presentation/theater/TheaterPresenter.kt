package woowacourse.movie.presentation.theater

import woowacourse.movie.data.TheaterData
import woowacourse.movie.domain.model.ScreeningInfo
import woowacourse.movie.domain.model.movie.Movie

class TheaterPresenter(
    private val view: TheaterContract.View,
    private val movie: Movie,
) : TheaterContract.Presenter {
    override fun onViewCreated() {
        val theaters = TheaterData.screeningInfos.filter { screeningInfo ->
            screeningInfo.movie == this.movie
        }

        view.showTheaters(theaters)
    }

    override fun onTheaterClicked(screeningInfo: ScreeningInfo) {
        view.navigateToBooking(screeningInfo)
    }
}