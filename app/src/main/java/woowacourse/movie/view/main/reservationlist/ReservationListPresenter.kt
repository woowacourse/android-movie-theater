package woowacourse.movie.view.main.reservationlist

import woowacourse.movie.database.ReservationDbHelperInterface
import woowacourse.movie.model.ReservationUiModel
import woowacourse.movie.model.mapper.ReservationMapper.toUi

class ReservationListPresenter(
    val view: ReservationListContract.View,
    private val reservationDb: ReservationDbHelperInterface,
) : ReservationListContract.Presenter {
    override fun showReservationResult(reservationUiModel: ReservationUiModel) {
        val movieUiModel = reservationUiModel.movie
        val ticketsUiModel = reservationUiModel.tickets
        view.showReservationResult(movieUiModel, ticketsUiModel)
    }

    override fun updateReservationList() {
        val reservationList = reservationDb.getReservations()
        val reservationUiModelList = reservationList.map { it.toUi() }
        view.setReservationList(reservationUiModelList)
    }
}
