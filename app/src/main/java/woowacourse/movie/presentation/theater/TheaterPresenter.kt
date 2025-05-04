package woowacourse.movie.presentation.theater

import woowacourse.movie.data.ScreeningData
import woowacourse.movie.domain.model.Movie
import woowacourse.movie.domain.model.Screening
import woowacourse.movie.domain.model.ScreeningInfos

class TheaterPresenter(
    private val view: TheaterContract.View,
    private val movie: Movie,
    private val screeningData: ScreeningData,
) : TheaterContract.Presenter {
    override fun loadTheaterList() {
        val screeningInfos = ScreeningInfos(screeningData.values)
        view.showTheaters(screeningInfos.findByMovie(movie))
    }

    override fun startBooking(screening: Screening) {
        view.navigateToBooking(screening)
    }
}
