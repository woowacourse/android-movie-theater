package woowacourse.movie.feature.bookingdetail.presenter

import woowacourse.movie.domain.model.BookingInfo
import woowacourse.movie.domain.model.MovieDate
import woowacourse.movie.domain.model.MovieDates
import woowacourse.movie.domain.model.MovieTime
import woowacourse.movie.domain.model.Screening
import woowacourse.movie.feature.bookingdetail.contract.BookingDetailContract
import woowacourse.movie.feature.bookingdetail.contract.BookingDetailContract.Presenter
import woowacourse.movie.feature.mapper.toDomain
import woowacourse.movie.feature.mapper.toUi
import woowacourse.movie.feature.model.BookingInfoUiModel
import woowacourse.movie.feature.model.MovieDateUiModel
import woowacourse.movie.feature.model.MovieTimeUiModel

class BookingDetailPresenter(
    private val view: BookingDetailContract.View,
) : Presenter {
    private lateinit var bookingInfo: BookingInfo

    override fun prepareBookingInfo(screening: Screening) {
        bookingInfo = BookingInfo(screening.movie, screening.theater)
        bookingInfo.updateMovieTime(screening.times.first())

        val movieDates =
            MovieDates(
                bookingInfo.movie.startDate,
                bookingInfo.movie.endDate,
            ).value.map { it.toUi() }
        view.setupDateView(movieDates)

        val movieTimes = screening.times.map { it.toUi().toString() }
        view.setupTimeView(movieTimes)

        view.updateView(bookingInfo.toUi())
    }

    override fun selectDate(date: String) {
        val movieDate: MovieDate = MovieDateUiModel.from(date).toDomain()
        bookingInfo.updateDate(movieDate)
    }

    override fun selectTime(time: String) {
        val movieTime: MovieTime = MovieTimeUiModel.from(time).toDomain()
        bookingInfo.updateMovieTime(movieTime)
    }

    override fun decreaseTicketCount() {
        bookingInfo.decreaseTicketCount()
        view.updateTicketCount(bookingInfo.currentTicketCount)
    }

    override fun increaseTicketCount() {
        bookingInfo.increaseTicketCount()
        view.updateTicketCount(bookingInfo.currentTicketCount)
    }

    override fun confirmBookingInfo() {
        view.navigateToBookingSeat(bookingInfo.toUi())
    }

    override fun onBackButtonClicked() {
        view.navigateToBack()
    }

    override fun saveBookingInfo(): BookingInfoUiModel = bookingInfo.toUi()

    override fun loadBookingInfo(existBookingInfo: BookingInfoUiModel) {
        bookingInfo = existBookingInfo.toDomain()
        view.updateView(existBookingInfo)
    }
}
