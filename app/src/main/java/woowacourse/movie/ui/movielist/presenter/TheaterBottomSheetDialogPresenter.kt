package woowacourse.movie.ui.movielist.presenter

import woowacourse.movie.domain.model.Theater
import woowacourse.movie.domain.model.Theaters
import woowacourse.movie.sample.DUMMY_THEATERS
import woowacourse.movie.ui.movielist.contract.TheaterBottomSheetDialogContract

class TheaterBottomSheetDialogPresenter(
    private val view: TheaterBottomSheetDialogContract.View,
) : TheaterBottomSheetDialogContract.Presenter {
    private val entireTheaters: Theaters by lazy { DUMMY_THEATERS }
    private var movieId = 0L

    override fun loadAvailableTheaters(movieId: Long) {
        this.movieId = movieId
        view.showTheaters(entireTheaters)
    }

    override fun startBooking(theater: Theater) {
        view.showReservation(theater, movieId)
    }
}
