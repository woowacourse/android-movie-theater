package woowacourse.movie.ui.reservation

import woowacourse.movie.db.reservationhistory.ReservationHistory
import woowacourse.movie.db.reservationhistory.ReservationHistoryDatabase
import woowacourse.movie.domain.model.Reservation
import woowacourse.movie.domain.repository.ReservationRepository
import woowacourse.movie.domain.repository.TheaterRepository
import kotlin.concurrent.thread

class ReservationPresenter(
    private val view: ReservationContract.View,
    private val reservationRepository: ReservationRepository,
    private val theaterRepository: TheaterRepository,
    private val reservationHistoryDatabase: ReservationHistoryDatabase,
) : ReservationContract.Presenter {
    override fun loadReservation(
        reservationId: Int,
        theaterId: Int,
    ) {
        reservationRepository.findById(reservationId)
            .onSuccess { reservation ->
                val theaterName = theaterRepository.findById(theaterId).name

                saveReservation(reservation, theaterName)

                view.showReservation(
                    reservation,
                    theaterName,
                    reservation.dateTime?.date!!,
                    reservation.dateTime.time,
                )
            }
            .onFailure { exception ->
                when (exception) {
                    is NoSuchElementException -> view.goToBack("해당하는 상영 정보가 없습니다.")
                    else -> view.unexpectedFinish("예상치 못한 에러가 발생했습니다")
                }
            }
    }

    private fun saveReservation(
        reservation: Reservation,
        theaterName: String,
    ) {
        thread {
            reservationHistoryDatabase.reservationHistoryDao().upsert(
                ReservationHistory(
                    reservation = reservation,
                    theaterName = theaterName,
                    screeningDate = reservation.dateTime?.date!!,
                    screeningTime = reservation.dateTime.time,
                ),
            )
        }.join()
    }
}
