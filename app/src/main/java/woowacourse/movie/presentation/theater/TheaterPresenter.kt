package woowacourse.movie.presentation.theater

import woowacourse.movie.data.repository.ScreeningRepository
import woowacourse.movie.domain.model.Movie
import woowacourse.movie.domain.model.Screening
import woowacourse.movie.domain.model.ScreeningInfos

class TheaterPresenter(
    private val view: TheaterContract.View,
    private val movie: Movie,
    private val screeningRepository: ScreeningRepository,
) : TheaterContract.Presenter {
    override fun loadTheaterList() {
        val screeningInfos = ScreeningInfos(screeningRepository.fetch())
        view.showTheaters(screeningInfos.findByMovie(movie))
    }

    override fun selectTheater(screening: Screening) {
        view.navigateToBooking(screening)
    }
}
