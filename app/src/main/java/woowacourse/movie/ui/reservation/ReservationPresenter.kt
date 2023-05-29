package woowacourse.movie.ui.reservation

import woowacourse.movie.data.reservation.ReservationRepository

class ReservationPresenter(
    private val view: ReservationContract.View,
    private val repository: ReservationRepository,
) : ReservationContract.Presenter {

    override fun initAdapter() {
        view.setReservationViewAdapter(repository.getData())
    }

    override fun clickItem(position: Int) {
        view.clickItem(repository.getData()[position])
    }

    override fun checkDataExisting() {
        val isDataEmpty = repository.getData().isEmpty()
        view.setEmptyStateText(isDataEmpty)
    }
}
