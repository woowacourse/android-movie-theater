package woowacourse.movie.view.home.theaters

import woowacourse.movie.data.TheaterStore
import woowacourse.movie.domain.model.theater.Theater
import woowacourse.movie.view.home.model.ScreeningInfo

class TheaterListPresenter(
    private val view: TheaterListContract.View,
    private val theaterStore: TheaterStore,
) : TheaterListContract.Presenter {
    override fun loadTheaters(movieId: Int) {
        val theaters = theaterStore.createTheaters()
        view.showTheaters(theaters.availableTheaters(movieId))
    }

    override fun selectTheater(
        movieId: Int,
        selectedTheater: Theater,
    ) {
        val theaterName = selectedTheater.name
        val screeningTimes = selectedTheater.screeningTimes(movieId)
        val screeningInfo = ScreeningInfo(movieId, theaterName, screeningTimes)

        view.moveToBooking(screeningInfo)
    }
}
