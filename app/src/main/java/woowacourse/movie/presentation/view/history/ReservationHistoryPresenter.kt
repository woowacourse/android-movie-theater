package woowacourse.movie.presentation.view.history

class ReservationHistoryPresenter(
    private val view: ReservationHistoryContract.View,
) : ReservationHistoryContract.Presenter {
    override fun fetchData() {
        view.showScreen()
    }
}
