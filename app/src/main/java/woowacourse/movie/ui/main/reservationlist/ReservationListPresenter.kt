package woowacourse.movie.ui.main.reservationlist

import woowacourse.movie.data.model.mapper.ReservationMapper
import woowacourse.movie.data.model.uimodel.ReservationUiModel
import woowacourse.movie.repository.ReservationListRepository

class ReservationListPresenter(
    private val view: ReservationListContract.View,
    private var repository: ReservationListRepository
) : ReservationListContract.Presenter {

    override fun getReservations(): List<ReservationUiModel> {
        return repository.reservations.map { reservation ->
            ReservationMapper.toUi(reservation)
        }
    }

    override fun setUpClickListener() {
        repository.reservations.forEach { reservation ->
            ReservationMapper.toUi(reservation).toItemModel {
                view.setOnReservationItemClick(it)
            }
        }
        view.updateReservations()
    }
}
