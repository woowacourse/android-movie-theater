package woowacourse.movie.presentation.view.reservation.complete

import woowacourse.movie.data.ReservationDao
import woowacourse.movie.data.mapper.ReservationMapper.toEntity
import woowacourse.movie.presentation.model.ReservationInfoUiModel
import woowacourse.movie.presentation.model.toDomain
import kotlin.concurrent.thread

class ReservationCompletePresenter(
    val view: ReservationCompleteContract.View,
    private val dao: ReservationDao,
) : ReservationCompleteContract.Presenter {
    override fun fetchData(reservationInfoUiModel: ReservationInfoUiModel?) {
        if (reservationInfoUiModel == null) {
            view.showErrorDialog()
            return
        }

        thread {
            val entity =
                reservationInfoUiModel.toDomain().toEntity(reservationInfoUiModel.theaterName)
            dao.saveReservation(entity)
        }

        view.showReservationInfo(reservationInfoUiModel)
    }
}
