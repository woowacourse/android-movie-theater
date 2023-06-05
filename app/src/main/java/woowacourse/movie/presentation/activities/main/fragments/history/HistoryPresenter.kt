package woowacourse.movie.presentation.activities.main.fragments.history

class HistoryPresenter(
    private val view: HistoryContract.View,
    private val db: HistoryDbHelper,
) : HistoryContract.Presenter {

    override fun loadReservationData() {
        val data = db.getData()
        view.showReservations(data)
    }
}
