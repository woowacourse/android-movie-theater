package woowacourse.movie.presenter

import woowacourse.movie.contract.ReservationListContract
import woowacourse.movie.model.ReservationUiModel
import woowacourse.movie.model.mapper.ReservationMapper.toUi
import woowacourse.movie.database.ReservationDbHelperInterface

class ReservationListPresenter(
    val view: ReservationListContract.View,
    val reservationDb: ReservationDbHelperInterface
) :
    ReservationListContract.Presenter {

    override fun reservationItemClick(reservationUiModel: ReservationUiModel) {
        val movieUiModel = reservationUiModel.movie
        val ticketsUiModel = reservationUiModel.tickets
        view.startReservationResultActivity(movieUiModel, ticketsUiModel)
    }

    override fun updateReservationList() {
        val reservationList = reservationDb.getReservations()
        val reservationUiModelList = reservationList.map { it.toUi() }
        view.setAdapter(reservationUiModelList)
    }
}
