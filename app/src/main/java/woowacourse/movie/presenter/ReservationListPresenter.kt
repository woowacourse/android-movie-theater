package woowacourse.movie.presenter

import woowacourse.movie.contract.ReservationListContract
import woowacourse.movie.mock.MockReservationsFactory
import woowacourse.movie.model.ReservationUiModel
import woowacourse.movie.model.mapper.ReservationMapper.toUi

class ReservationListPresenter(val view: ReservationListContract.View) :
    ReservationListContract.Presenter {

    override fun reservationItemClick(reservationUiModel: ReservationUiModel) {
        val movieUiModel = reservationUiModel.movie
        val ticketsUiModel = reservationUiModel.tickets
        view.startReservationResultActivity(movieUiModel, ticketsUiModel)
    }

    override fun updateReservationList() {
        val reservationList = MockReservationsFactory.makeReservations()
        val reservationUiModelList = reservationList.map { it.toUi() }
        view.setAdapter(reservationUiModelList)
    }
}
