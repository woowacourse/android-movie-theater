package woowacourse.movie.presenter.theater

import woowacourse.movie.db.theater.TheaterDao

class TheaterSelectionPresenter(
    private val view: TheaterSelectionContract.View,
    private val movieId: Int,
) : TheaterSelectionContract.Presenter {
    override fun loadTheaters(movieId: Int) {
        view.showTheaters(TheaterDao().findTheaterByMovieId(movieId))
    }

    override fun loadTheater(theaterId: Int) {
        view.navigateToDetail(movieId, theaterId)
    }
}
