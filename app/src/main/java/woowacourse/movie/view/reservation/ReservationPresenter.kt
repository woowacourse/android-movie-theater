package woowacourse.movie.view.reservation

import woowacourse.movie.domain.model.ReservationCount
import woowacourse.movie.domain.model.ReservationInfo
import woowacourse.movie.domain.model.Screening
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.format.DateTimeFormatter

class ReservationPresenter(
    private val view: ReservationContract.View,
) : ReservationContract.Presenter {
    private lateinit var screening: Screening
    private var reservationCount: ReservationCount = ReservationCount()

    override fun loadData(
        screening: Screening,
        count: Int?,
        dateTime: String?,
    ) {
        this.screening = screening
        view.showMovieDetail(screening)

        count?.let { reservationCount = ReservationCount(it) }
        dateTime?.let {
            val times = screening.screeningTimes
            view.updateTimeSet(times, it.toLocalDateTime().toLocalTime())
        }

        view.updateReservationCount(reservationCount.value)
    }

    override fun increaseCount(count: Int) {
        reservationCount += count
        view.updateReservationCount(reservationCount.value)
    }

    override fun decreaseCount(count: Int) {
        reservationCount -= count
        view.updateReservationCount(reservationCount.value)
    }

    override fun selectDate(date: LocalDate) {
        val times = screening.screeningTimes
        val temp = screening.movie.availableDates(LocalDateTime.now())
        view.updateDateSet(temp)
        view.updateTimeSet(times)
    }

    override fun onReserve(
        reservationDate: LocalDate,
        reservationTime: LocalTime,
    ) {
        val reservationInfo =
            ReservationInfo(
                title = screening.movie.title,
                reservationDateTime = LocalDateTime.of(reservationDate, reservationTime),
                reservationCount = reservationCount,
                cinema = screening.cinema,
            )

        view.navigateToSeatSelectionScreen(reservationInfo)
    }
}

private fun String.toLocalDateTime(): LocalDateTime {
    val formatter = DateTimeFormatter.ofPattern(SPINNER_DATETIME_FORMAT)
    return LocalDateTime.parse(this, formatter)
}

private const val SPINNER_DATETIME_FORMAT = "yyyy-MM-dd'T'HH:mm"
