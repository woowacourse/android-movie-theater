package woowacourse.movie.presentation.theater

import woowacourse.movie.data.ScreeningInfoData
import woowacourse.movie.domain.model.ScreeningInfo
import woowacourse.movie.domain.model.ScreeningInfos
import woowacourse.movie.domain.model.movie.Movie

class TheaterPresenter(
    private val view: TheaterContract.View,
) : TheaterContract.Presenter {
    private val screeningInfos = ScreeningInfos(ScreeningInfoData.values)
    private lateinit var movie: Movie

    override fun initializeTheater(movie: Movie) {
        this.movie = movie
        view.showTheaters(screeningInfos.findByMovie(movie))
    }

    override fun selectTheater(screeningInfo: ScreeningInfo) {
        view.navigateToBooking(screeningInfo)
    }
}