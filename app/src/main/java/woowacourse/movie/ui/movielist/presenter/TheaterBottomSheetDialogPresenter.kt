package woowacourse.movie.ui.movielist.presenter

import woowacourse.movie.domain.model.Movie
import woowacourse.movie.domain.model.Theaters
import woowacourse.movie.sample.DUMMY_THEATERS
import woowacourse.movie.ui.movielist.contract.TheaterBottomSheetDialogContract

class TheaterBottomSheetDialogPresenter(
    private val view: TheaterBottomSheetDialogContract.View,
) : TheaterBottomSheetDialogContract.Presenter {
    private val entireTheaters: Theaters by lazy { loadEntireTheaters() }
    private lateinit var availableTheaters: Theaters

    override fun loadEntireTheaters(): Theaters {
        return DUMMY_THEATERS
    }

    override fun loadAvailableTheaters(movie: Movie) {
        availableTheaters = entireTheaters.availableTheaters(movie)
        view.showTheaters(availableTheaters)
    }
}
