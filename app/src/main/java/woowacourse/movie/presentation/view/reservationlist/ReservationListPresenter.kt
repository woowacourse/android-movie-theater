package woowacourse.movie.presentation.view.reservationlist

import android.os.Handler
import android.os.Looper
import woowacourse.movie.data.ReservationDao
import woowacourse.movie.data.mapper.ReservationMapper.toDomain
import woowacourse.movie.presentation.model.ReservationInfoUiModel
import woowacourse.movie.presentation.model.toPresentation
import kotlin.concurrent.thread

class ReservationListPresenter(
    val view: ReservationListContract.View,
    private val reservationDao: ReservationDao,
) : ReservationListContract.Presenter {
    override fun fetchMovies() {
        thread {
            val reservationEntities = reservationDao.getAllReservation()

            val reservationInfoUiModels =
                reservationEntities.map { entity ->
                    val domainModel = entity.toDomain()
                    domainModel.toPresentation(entity.theaterName)
                }

            Handler(Looper.getMainLooper()).post {
                view.showReservations(reservationInfoUiModels)
            }
        }
    }

    override fun reservationSelected(reservation: ReservationInfoUiModel) {
        view.navigateToComplete(reservation)
    }
}
