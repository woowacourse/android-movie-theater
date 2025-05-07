package woowacourse.movie.theater

import woowacourse.movie.mapper.toDomain
import woowacourse.movie.mapper.toUiModel
import woowacourse.movie.model.Movie
import woowacourse.movie.model.Theater
import woowacourse.movie.ui.model.MovieUiModel
import woowacourse.movie.ui.model.TheaterUiModel

class TheaterBottomSheetPresenter(
    private val view: TheaterBottomSheetContract.View,
) : TheaterBottomSheetContract.Presenter {
    private lateinit var theaters: List<Theater>
    private lateinit var movie: Movie

    override fun initializeInfo(
        movie: MovieUiModel,
        theaters: List<TheaterUiModel>,
    ) {
        this.theaters = theaters.map { it.toDomain() }
        this.movie = movie.toDomain()

        view.setUpTheaterList(theaters)
    }

    override fun selectTheater(theater: TheaterUiModel) {
        view.startBookingDetail(movie.toUiModel(), theater)
    }
}
