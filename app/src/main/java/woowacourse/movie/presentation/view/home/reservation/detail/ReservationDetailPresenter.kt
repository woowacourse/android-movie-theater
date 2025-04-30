package woowacourse.movie.presentation.view.home.reservation.detail

import woowacourse.movie.domain.model.cinema.Theater
import woowacourse.movie.domain.model.cinema.screen.Screen
import woowacourse.movie.domain.model.movie.Movie
import woowacourse.movie.domain.model.reservation.ReservationCount
import woowacourse.movie.domain.model.reservation.ReservationInfo
import woowacourse.movie.presentation.model.MovieUiModel
import woowacourse.movie.presentation.model.TheaterUiModel
import woowacourse.movie.presentation.model.toModel
import woowacourse.movie.presentation.model.toUiModel
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime

class ReservationDetailPresenter(
    private val view: ReservationDetailContract.View,
) : ReservationDetailContract.Presenter {
    private val screen: Screen = Screen.DEFAULT_SCREEN
    private lateinit var movie: Movie
    private lateinit var theater: Theater
    private var reservationCount = ReservationCount()

    override fun fetchData(
        movie: MovieUiModel,
        theater: TheaterUiModel,
        initCount: Int?,
        dateTime: LocalDateTime?,
    ) {
        this.movie = movie.toModel()
        this.theater = theater.toModel(movie.id)
        initCount?.let { initializeReservationCount(it) }
        setupInitialView(dateTime)
    }

    override fun updateReservationCount(updateCount: Int) {
        if (updateCount >= 0 && reservationCount.value >= screen.seats.size) {
            view.notifyReservationLimitReached()
            return
        }

        reservationCount += updateCount
        view.updateReservationCount(reservationCount.value, reservationCount.isValid())
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

        view.notifyReservationConfirm(reservationInfo, screen.toUiModel(), theater.name)
    }

    private fun setupInitialView(dateTime: LocalDateTime?) {
        view.showScreen(movie.toUiModel())
        view.updateReservationCount(reservationCount.value, reservationCount.isValid())
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
        val selectedDateTimeToShow = selectedDate?.atTime(availableTimes.firstOrNull() ?: LocalTime.MIN)

        view.updateDates(availableDates, availableTimes, selectedDateTimeToShow)
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
        return getAvailableShowTimes(now)
            .map { it.toLocalDate() }
            .distinct()
    }

    private fun getAvailableTimesFor(date: LocalDate): List<LocalTime> {
        val now = LocalDateTime.now()
        return getAvailableShowTimes(now)
            .filter { it.toLocalDate().isEqual(date) }
            .map { it.toLocalTime() }
    }

    private fun getAvailableShowTimes(currentTime: LocalDateTime): List<LocalDateTime> =
        theater.getAvailableShowTimesFor(movie.id, currentTime)

    private fun ReservationCount.isValid(): Boolean = value > ReservationCount.RESERVATION_MIN_COUNT
}
