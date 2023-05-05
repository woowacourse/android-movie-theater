package woowacourse.movie.fragment.reservationlist

import woowacourse.movie.repository.ReservationListRepository
import woowacourse.movie.view.mapper.ReservationMapper
import woowacourse.movie.view.model.ReservationUiModel

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
