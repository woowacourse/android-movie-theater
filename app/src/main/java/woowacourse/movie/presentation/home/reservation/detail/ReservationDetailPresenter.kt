package woowacourse.movie.presentation.home.reservation.detail

import woowacourse.movie.domain.model.cinema.Seats
import woowacourse.movie.domain.model.cinema.Theater
import woowacourse.movie.domain.model.movie.Movie
import woowacourse.movie.domain.model.reservation.ReservationCount
import woowacourse.movie.domain.model.reservation.ReservationInfo
import woowacourse.movie.presentation.common.model.MovieUiModel
import woowacourse.movie.presentation.common.model.TheaterUiModel
import woowacourse.movie.presentation.common.model.toDomain
import woowacourse.movie.presentation.common.model.toUiModel
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime

class ReservationDetailPresenter(
    private val view: ReservationDetailContract.View,
) : ReservationDetailContract.Presenter {
    private val seats: Seats = Seats.DEFAULT_SEATS
    private lateinit var movie: Movie
    private lateinit var theater: Theater
    private var reservationCount = ReservationCount()

    override fun fetchData(
        movie: MovieUiModel,
        theater: TheaterUiModel,
        initCount: Int?,
        dateTime: LocalDateTime?,
    ) {
        this.movie = movie.toDomain()
        this.theater = theater.toDomain(movie.id)
        initCount?.let { initializeReservationCount(it) }
        setupInitialView(dateTime)
    }

    override fun updateReservationCount(updateCount: Int) {
        if (updateCount >= 0 && reservationCount.value >= seats.seats.size) {
            view.notifyReservationLimitReached()
            return
        }

        reservationCount += updateCount
        view.updateReservationCount(reservationCount.value, !reservationCount.isMin())
    }

    override fun onSelectDate(
        date: LocalDate,
        selectedTime: LocalTime?,
    ) {
        val times = getAvailableTimesFor(date)
        view.updateTimes(times, selectedTime)
    }

    override fun onReserve(reservationDateTime: LocalDateTime) {
        val reservationInfo =
            ReservationInfo(
                movie.title,
                reservationDateTime,
                reservationCount,
            ).toUiModel(theater.name)

        view.notifyReservationConfirm(reservationInfo, seats.toUiModel(), theater.name)
    }

    private fun setupInitialView(dateTime: LocalDateTime?) {
        view.showScreen(movie.toUiModel())
        view.updateReservationCount(reservationCount.value, !reservationCount.isMin())
        updateAvailableDatesAndTimes(dateTime)
    }

    private fun initializeReservationCount(count: Int) {
        runCatching { ReservationCount(count) }
            .onSuccess { reservationCount = it }
    }

    private fun updateAvailableDatesAndTimes(selectedDateTime: LocalDateTime?) {
        val availableDates = getAvailableDates()

        if (availableDates.isEmpty()) {
            view.notifyNoAvailableDates()
            return
        }

        val selectedDate = getValidSelectedDate(selectedDateTime, availableDates)
        val availableTimes = selectedDate?.let { getAvailableTimesFor(it) }.orEmpty()

        view.updateDates(availableDates, availableTimes, selectedDateTime)
    }

    private fun getValidSelectedDate(
        selectedDateTime: LocalDateTime?,
        availableDates: List<LocalDate>,
    ): LocalDate? {
        val preferredDate = selectedDateTime?.toLocalDate()
        return when {
            preferredDate != null && getAvailableTimesFor(preferredDate).isNotEmpty() -> preferredDate
            else -> availableDates.firstOrNull { getAvailableTimesFor(it).isNotEmpty() }
        }
    }

    private fun getAvailableDates(): List<LocalDate> {
        val now = LocalDateTime.now()
        return theater
            .availableShowTimes(movie.id, now)
            .map { it.toLocalDate() }
            .distinct()
    }

    private fun getAvailableTimesFor(date: LocalDate): List<LocalTime> {
        val now = LocalDateTime.now()
        return theater
            .availableShowTimes(movie.id, now)
            .filter { it.toLocalDate().isEqual(date) }
            .map { it.toLocalTime() }
    }
}
