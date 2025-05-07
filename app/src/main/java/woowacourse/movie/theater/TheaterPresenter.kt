package woowacourse.movie.theater

import woowacourse.movie.ui.model.MovieUiModel
import woowacourse.movie.ui.model.TheaterUiModel

class TheaterPresenter(
    private val view: TheaterContract.View,
) : TheaterContract.Presenter {
    private lateinit var movie: MovieUiModel
    private lateinit var theaters: ArrayList<TheaterUiModel>

    override fun initialize(
        movie: MovieUiModel,
        theaters: ArrayList<TheaterUiModel>,
    ) {
        this.movie = movie
        this.theaters = theaters
        view.showTheaters(theaters)
    }

    override fun clickTheater(theater: TheaterUiModel) {
        view.navigateToBookingDetail(theater, movie)
    }
}
