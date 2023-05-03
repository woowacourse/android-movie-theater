package woowacourse.movie.ui.main.cinemaBottomSheet

import woowacourse.movie.data.CinemaRepository
import woowacourse.movie.model.MovieState

class CinemaListPresenter(
    private val view: CinemaListContract.View,
    private val repository: CinemaRepository = CinemaRepository
) : CinemaListContract.Presenter {
    override fun getCinemaList(movie: MovieState) {
        view.setAdapter(repository.findCinema(movie))
    }
}
