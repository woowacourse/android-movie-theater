package woowacourse.movie.reservation

import woowacourse.movie.data.ReservationDao
import woowacourse.movie.mapper.toUiModel
import woowacourse.movie.ui.model.TicketUiModel

class ReservationRepository(private val dao: ReservationDao) {
    fun getAllReservations(): List<TicketUiModel> {
        return dao.getAll().map { it.toUiModel() }
    }
}
