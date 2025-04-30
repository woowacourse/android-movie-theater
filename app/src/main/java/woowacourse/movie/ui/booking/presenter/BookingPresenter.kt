package woowacourse.movie.ui.booking.presenter

import woowacourse.movie.domain.model.Headcount
import woowacourse.movie.domain.model.Theater
import woowacourse.movie.sample.DUMMY_THEATERS
import woowacourse.movie.ui.booking.contract.BookingContract
import java.time.LocalDate
import java.time.LocalDateTime

class BookingPresenter(
    private val bookingView: BookingContract.View,
) : BookingContract.Presenter {
    private var _headcount: Headcount = Headcount()
    val headcount get() = _headcount.deepCopy()

    private val theater: Theater by lazy { loadTheater() }

    private var selectedDatePosition: Int = 0
    private var selectedTimePosition: Int = 0
    private val selectedDateTime: LocalDateTime get() = bookingView.getSelectedDateTime()

    fun updateViews() {
        refreshMovieInfo()
        setupDateSpinner()
    }

    override fun increaseHeadcount() {
        _headcount.increase()
        bookingView.updateHeadcountDisplay(_headcount)
    }

    override fun decreaseHeadcount() {
        _headcount.decrease()
        bookingView.updateHeadcountDisplay(_headcount)
    }

    override fun loadTheater(): Theater = bookingView.getTheater() ?: DUMMY_THEATERS.theaters.first()

    override fun refreshMovieInfo() {
        bookingView.setMovieInfoViews(theater.movieSchedules[0].movie)
    }

    override fun setHeadcount(headcount: Headcount) {
        this._headcount = headcount
    }

    override fun refreshHeadcountDisplay() {
        bookingView.updateHeadcountDisplay(_headcount)
    }

    override fun setupDateSpinner() {
        val screeningDateTime: List<LocalDate> =
            theater.movieSchedules.map { it.screeningDateTime.screeningDate }
        bookingView.setDateSpinner(screeningDateTime, selectedDatePosition)
    }

    override fun setupTimeSpinner() {
        val selectedDate = bookingView.getSelectedDate()
        val screeningTimesItems =
            theater.movieSchedules
                .filter { it.screeningDateTime.screeningDate.isEqual(selectedDate) }
                .map { it.screeningDateTime.screeningTime }
        bookingView.setTimeSpinner(screeningTimesItems, selectedTimePosition)
    }

    override fun setSelectedDatePosition(position: Int) {
        selectedDatePosition = position
        setupDateSpinner()
    }

    override fun setSelectedTimePosition(position: Int) {
        selectedTimePosition = position
        setupTimeSpinner()
    }

    override fun completeBooking() {
        bookingView.startBookingSeatActivity(
            theater.movieSchedules[0].movie.title,
            selectedDateTime,
            headcount,
            theater,
        )
    }
}
