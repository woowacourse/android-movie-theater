package woowacourse.movie.view.reservation

import woowacourse.movie.domain.Reservation
import woowacourse.movie.domain.ScreeningTime
import woowacourse.movie.view.model.MovieListModel
import woowacourse.movie.view.model.ReservationOptions
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime

class ReservationPresenter(
    private val view: ReservationContract.View
) : ReservationContract.Presenter {

    lateinit var movie: MovieListModel.MovieUiModel
    private var peopleCountSaved = 1
    private lateinit var selectedScreeningDate: LocalDate
    private lateinit var selectedScreeningTime: LocalTime
    private var timeSpinnerPosition = 0

    override fun loadMovie() {
        movie = view.getMovie()
        view.initMovieView(movie)
    }

    override fun setUpScreeningDateTime() {
        selectedScreeningDate = movie.screeningStartDate
        selectedScreeningTime = ScreeningTime(selectedScreeningDate).getFirstScreeningTime()

        val screeningDates = movie.getAllScreeningDates()
        view.setUpDateSpinner(screeningDates)
    }

    override fun selectScreeningDate(date: LocalDate) {
        selectedScreeningDate = date
        setUpScreeningTime()
    }

    private fun setUpScreeningTime() {
        val screeningTimes = ScreeningTime(selectedScreeningDate).getAllScreeningTimes()
        view.setUpTimeSpinner(screeningTimes, timeSpinnerPosition)
    }

    override fun setPeopleCount() {
        view.setPeopleCountTextView(peopleCountSaved)
    }

    override fun increasePeopleCount() {
        if (peopleCountSaved < Reservation.MAX_PEOPLE_COUNT) {
            peopleCountSaved++
            view.setPeopleCountTextView(peopleCountSaved)
        }
    }

    override fun decreasePeopleCount() {
        if (peopleCountSaved > Reservation.MIN_PEOPLE_COUNT) {
            peopleCountSaved--
            view.setPeopleCountTextView(peopleCountSaved)
        }
    }

    override fun selectScreeningTime(time: LocalTime, position: Int) {
        selectedScreeningTime = time
        saveTimePosition(position)
    }

    override fun saveTimePosition(position: Int) {
        timeSpinnerPosition = position
    }

    override fun getReservationOptions(): ReservationOptions =
        ReservationOptions(
            movie.title,
            LocalDateTime.of(selectedScreeningDate, selectedScreeningTime),
            peopleCountSaved
        )

    override fun restoreReservationOptions(
        screeningDate: LocalDate,
        screeningTime: LocalTime,
        peopleCount: Int
    ) {
        selectedScreeningDate = screeningDate
        selectedScreeningTime = screeningTime
        peopleCountSaved = peopleCount
    }

    override fun reserve() {
        val reservationOptions = ReservationOptions(
            movie.title,
            LocalDateTime.of(selectedScreeningDate, selectedScreeningTime),
            peopleCountSaved
        )
        view.openSeatSelectionActivity(reservationOptions, movie)
    }
}
