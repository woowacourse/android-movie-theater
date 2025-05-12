package woowacourse.movie.view.reservation.complete

import woowacourse.movie.R
import woowacourse.movie.model.reservation.ReservationInfo

class ReservationCompletePresenter(
    val view: ReservationCompleteContract.View,
) : ReservationCompleteContract.Presenter {
    private lateinit var reservationInfo: ReservationInfo

    override fun fetchData(getReservationInfo: () -> ReservationInfo?) {
        val result = getReservationInfo()
        if (result == null) {
            view.showErrorMessage(R.string.reservation_complete_error_reservation_info_load_failed)
            view.finishView()
            return
        }
        reservationInfo = result
        view.showReservationInfo(reservationInfo, getSeatLabels())
    }

    private fun getSeatLabels(): List<String> =
        reservationInfo.seats.value.map {
            getRowSeatText(it.row.index) + getColSeatText(it.col.index)
        }

    private fun getColSeatText(index: Int) = (index + 1).toString()

    private fun getRowSeatText(index: Int) = ('A'.code + index).toChar().toString()
}
