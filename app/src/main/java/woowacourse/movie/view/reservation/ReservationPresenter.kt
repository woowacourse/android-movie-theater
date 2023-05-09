package woowacourse.movie.view.reservation

import woowacourse.movie.domain.Reservation
import woowacourse.movie.domain.ScreeningTime
import woowacourse.movie.view.model.MovieListModel
import woowacourse.movie.view.model.MovieTheater
import woowacourse.movie.view.model.ReservationOptions
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime

class ReservationPresenter(
    private val view: ReservationContract.View,
    private val movie: MovieListModel.MovieUiModel
) : ReservationContract.Presenter {

    private var peopleCountSaved = 1
    private lateinit var selectedScreeningDate: LocalDate
    private lateinit var selectedScreeningTime: LocalTime
    private var timeSpinnerPosition = 0

    init {
        view.initMovieView(movie)
    }

    override fun setUpScreeningDateTime() {
        selectedScreeningDate = movie.screeningStartDate
        selectedScreeningTime = ScreeningTime(selectedScreeningDate, null).getFirstScreeningTime()

        val screeningDates = movie.getAllScreeningDates()
        view.setUpDateSpinner(screeningDates)
    }

    override fun selectScreeningDate(date: LocalDate, timeslot: List<Int>?) {
        selectedScreeningDate = date
        val screeningTimes = ScreeningTime(selectedScreeningDate, timeslot).getScreeningTimes()
        view.setUpTimeSpinner(screeningTimes, timeSpinnerPosition)
    }

    override fun setPeopleCount() {
        view.setPeopleCountText(peopleCountSaved)
    }

    override fun increasePeopleCount() {
        if (peopleCountSaved < Reservation.MAX_PEOPLE_COUNT) {
            peopleCountSaved++
            view.setPeopleCountText(peopleCountSaved)
        }
    }

    override fun decreasePeopleCount() {
        if (peopleCountSaved > Reservation.MIN_PEOPLE_COUNT) {
            peopleCountSaved--
            view.setPeopleCountText(peopleCountSaved)
        }
    }

    override fun selectScreeningTime(time: LocalTime, position: Int) {
        selectedScreeningTime = time
        saveTimePosition(position)
    }

    override fun saveTimePosition(position: Int) {
        timeSpinnerPosition = position
    }

    override fun getReservationOptions(theaterName: String): ReservationOptions =
        ReservationOptions(
            theaterName,
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

    override fun reserve(movieTheater: MovieTheater) {
        val reservationOptions = ReservationOptions(
            movieTheater.name,
            movie.title,
            LocalDateTime.of(selectedScreeningDate, selectedScreeningTime),
            peopleCountSaved
        )
        view.toSeatSelectionScreen(reservationOptions, movie)
    }
}
