package woowacourse.movie.ui.reservation

import woowacourse.movie.db.reservationhistory.ReservationHistory
import woowacourse.movie.db.reservationhistory.ReservationHistoryDatabase
import woowacourse.movie.domain.repository.ReservationRepository
import woowacourse.movie.domain.repository.TheaterRepository

class ReservationPresenter(
    private val view: ReservationContract.View,
    private val reservationRepository: ReservationRepository,
    private val theaterRepository: TheaterRepository,
    private val db: ReservationHistoryDatabase,
) : ReservationContract.Presenter {
    override fun loadReservation(
        reservationId: Int,
        theaterId: Int,
    ) {
        reservationRepository.findById(reservationId)
            .onSuccess {
                val theaterName = theaterRepository.findById(theaterId).name

                view.showReservation(it, theaterName, it.dateTime?.date!!, it.dateTime.time)
            }
            .onFailure { e ->
                when (e) {
                    is NoSuchElementException -> view.goToBack("해당하는 상영 정보가 없습니다.")
                    else -> view.unexpectedFinish("예상치 못한 에러가 발생했습니다")
                }
            }
    }

    override fun saveReservation(
        reservationId: Int,
        theaterId: Int,
    ) {
        val thread =
            Thread {
                reservationRepository.findById(reservationId)
                    .onSuccess {
                        db.reservationHistoryDao().upsert(
                            ReservationHistory(
                                reservation = it,
                                theaterName = theaterRepository.findById(theaterId).name,
                                screeningDate = it.dateTime?.date!!,
                                screeningTime = it.dateTime.time,
                            ),
                        )
                    }
                    .onFailure { e ->
                        when (e) {
                            is NoSuchElementException -> view.goToBack("해당하는 상영 정보가 없습니다.")
                            else -> view.unexpectedFinish("예상치 못한 에러가 발생했습니다")
                        }
                    }
            }
        thread.start()

        thread.join()
    }
}
