package woowacourse.movie.presentation.home.movies.dialog

import woowacourse.movie.presentation.common.model.MovieUiModel
import woowacourse.movie.presentation.common.model.TheaterUiModel
import woowacourse.movie.presentation.common.model.TheatersUiModel

class TheaterBottomSheetDialogPresenter(
    var view: TheaterBottomSheetDialogContract.View,
) : TheaterBottomSheetDialogContract.Presenter {
    private lateinit var movie: MovieUiModel

    override fun fetch(
        theaters: TheatersUiModel,
        movie: MovieUiModel,
    ) {
        this.movie = movie
        view.showTheaters(theaters)
    }

    override fun presentTheaterItem(theater: TheaterUiModel) {
        view.showDetail(movie, theater)
    }
}
