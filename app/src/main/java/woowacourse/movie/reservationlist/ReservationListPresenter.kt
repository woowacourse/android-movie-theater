package woowacourse.movie.reservationlist

import woowacourse.movie.model.Reservation
import woowacourse.movie.reservationlist.uimodel.toReservationUiModel
import woowacourse.movie.usecase.FetchAllReservationsUseCase
import java.util.concurrent.FutureTask

class ReservationListPresenter(
    private val view: ReservationListContract.View,
    private val fetchAllReservationsUseCase: FetchAllReservationsUseCase,
) : ReservationListContract.Presenter {
    private lateinit var reservations: List<Reservation>

    override fun loadContent() {
        val task =
            FutureTask {
                fetchAllReservationsUseCase()
            }
        Thread(task).start()
        task.get().onSuccess {
            reservations = it
            view.showContent(
                reservations.map { reservation ->
                    reservation.toReservationUiModel()
                },
            )
        }.onFailure {
            // view.showError()
        }
    }

    override fun selectReservation(reservationId: Long) {
        view.navigateToReservationDetail(reservationId)
    }
}
