package woowacourse.movie.ui.main.reservationlist

import woowacourse.movie.data.model.mapper.ReservationMapper
import woowacourse.movie.data.model.uimodel.ReservationUIModel
import woowacourse.movie.db.DBHelper
import woowacourse.movie.repository.ReservationListRepository

class ReservationListPresenter(
    private val view: ReservationListContract.View,
    private var repository: ReservationListRepository,
    private val dbHelper: DBHelper
) : ReservationListContract.Presenter {

    override fun getReservations(): List<ReservationUIModel> {
        repository.reservations = dbHelper.getReservations()
        return repository.reservations.map { reservation ->
            ReservationMapper.toUI(reservation)
        }
    }

    override fun setUpClickListener() {
        repository.reservations.forEach { reservation ->
            ReservationMapper.toUI(reservation).toItemModel {
                view.setOnReservationItemClick(it)
            }
        }
        view.updateReservations()
    }
}
