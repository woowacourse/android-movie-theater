package woowacourse.movie.view.activities.seatselection

import woowacourse.movie.domain.theater.Point
import woowacourse.movie.domain.screening.Screening
import woowacourse.movie.domain.theater.Theater
import woowacourse.movie.repository.ReservationRepository
import woowacourse.movie.repository.ScreeningRepository
import woowacourse.movie.repository.TheaterRepository
import java.time.LocalDateTime

class SeatSelectionPresenter(
    private val view: SeatSelectionContract.View,
    private val screeningId: Long,
    private val screeningDateTime: LocalDateTime,
    private val theaterId: Long,
    private val screeningRepository: ScreeningRepository,
    private val theaterRepository: TheaterRepository,
    private val reservationRepository: ReservationRepository
) : SeatSelectionContract.Presenter {

    private lateinit var screening: Screening
    private lateinit var theater: Theater

    override fun loadScreening() {
        screening = screeningRepository.findById(screeningId) ?: return
        theater = theaterRepository.findById(theaterId) ?: return

        view.setSeats(SeatsUIState.from(theater))
        view.setMovieTitle(screening.movie.title)
        view.setReservationFee(0)
    }

    override fun setSelectedSeats(seatNames: Set<String>) {
        val seatPoints = seatNames.map { convertSeatNameToSeatPoint(it) }
        if (seatPoints.isEmpty()) {
            view.setReservationFee(0)
            return
        }
        val reservation = screening.reserve(screeningDateTime, theater, seatPoints)
        view.setReservationFee(reservation.fee.amount)
    }

    private fun convertSeatNameToSeatPoint(seatName: String): Point {
        val row = seatName[0] - 'A' + 1
        val column = seatName[1].toString().toInt()
        return Point(row, column)
    }

    override fun reserve(seatNames: Set<String>) {
        require(seatNames.isNotEmpty()) { "좌석 선택을 해야 예매할 수 있습니다." }

        val seatPoints = seatNames.map { convertSeatNameToSeatPoint(it) }
        val reservation = screening.reserve(screeningDateTime, theater, seatPoints)
        val reservationId = reservationRepository.save(reservation)
        view.setReservation(reservationId)
    }
}
