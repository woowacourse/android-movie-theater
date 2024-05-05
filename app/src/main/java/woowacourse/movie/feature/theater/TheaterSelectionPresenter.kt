package woowacourse.movie.feature.theater

class TheaterSelectionPresenter(
    private val view: TheaterSelectionContract.View,
    private val movieId: Int,
) : TheaterSelectionContract.Presenter {
    override fun sendTheaterInfoToReservation(theaterId: Int) {
        view.navigateToReservation(movieId, theaterId)
    }
}
