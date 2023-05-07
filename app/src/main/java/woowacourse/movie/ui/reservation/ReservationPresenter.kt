package woowacourse.movie.ui.reservation

import woowacourse.movie.data.reservation.ReservationRepository

class ReservationPresenter(
    private val view: ReservationContract.View,
    private val repository: ReservationRepository,
) : ReservationContract.Presenter {

    override fun setAdapter() {
        view.setReservationViewAdapter(repository.getData())
    }

    override fun clickItem(position: Int) {
        view.clickItem(repository.getData()[position])
    }

    override fun isReservationEmpty(): Boolean {
        return repository.getData().isEmpty()
    }
}
