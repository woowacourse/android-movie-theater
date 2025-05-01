package woowacourse.movie.presentation.theater

import woowacourse.movie.data.ScreeningData
import woowacourse.movie.domain.model.Screening
import woowacourse.movie.domain.model.ScreeningInfos
import woowacourse.movie.domain.model.movie.Movie

class TheaterPresenter(
    private val view: TheaterContract.View,
    private val movie: Movie,
) : TheaterContract.Presenter {
    private val screeningInfos = ScreeningInfos(ScreeningData.values)

    override fun onViewCreated() {
        view.showTheaters(screeningInfos.findByMovie(movie))
    }

    override fun onTheaterClicked(screening: Screening) {
        view.navigateToBooking(screening)
    }
}
