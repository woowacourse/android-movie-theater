
package woowacourse.movie.presenter.theater

class TheaterSelectionPresenter(
    private val view: TheaterSelectionContract.View,
) : TheaterSelectionContract.Presenter {
    override fun loadTheater(
        movieId: Int,
        theaterId: Int,
    ) {
        view.navigateToDetail(movieId, theaterId)
    }
}
