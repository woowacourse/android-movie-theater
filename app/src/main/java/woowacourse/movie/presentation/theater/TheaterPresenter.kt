package woowacourse.movie.presentation.theater

import woowacourse.movie.data.ScreeningInfoData
import woowacourse.movie.domain.model.ScreeningInfo
import woowacourse.movie.domain.model.ScreeningInfos
import woowacourse.movie.domain.model.movie.Movie

class TheaterPresenter(
    private val view: TheaterContract.View,
    private val movie: Movie,
) : TheaterContract.Presenter {
    private val screeningInfos = ScreeningInfos(ScreeningInfoData.values)

    override fun onViewCreated() {
        view.showTheaters(screeningInfos.findByMovie(movie))
    }

    override fun onTheaterClicked(screeningInfo: ScreeningInfo) {
        view.navigateToBooking(screeningInfo)
    }
}