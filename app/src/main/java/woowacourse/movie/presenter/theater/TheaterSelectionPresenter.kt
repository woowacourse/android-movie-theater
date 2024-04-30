package woowacourse.movie.presenter.theater

class TheaterSelectionPresenter(
    private val view: TheaterSelectionContract.View,
    private val movieId: Int,
) : TheaterSelectionContract.Presenter {
    override fun loadTheater(theaterId: Int) {
        view.navigateToDetail(movieId, theaterId)
    }
}
