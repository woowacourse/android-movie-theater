package woowacourse.movie.moviedetail

import woowacourse.movie.model.HeadCount
import woowacourse.movie.model.Screening
import woowacourse.movie.model.ScreeningSchedule
import woowacourse.movie.moviedetail.uimodel.BookingDetailUiModel
import woowacourse.movie.moviedetail.uimodel.BookingInfoUiModel
import woowacourse.movie.moviedetail.uimodel.HeadCountUiModel
import woowacourse.movie.moviedetail.uimodel.ScheduleUiModels
import woowacourse.movie.moviedetail.uimodel.toHeadCountUiModel
import woowacourse.movie.moviedetail.uimodel.toMovieDetailUiModel
import woowacourse.movie.moviedetail.uimodel.toScheduleUiModels
import woowacourse.movie.usecase.FetchScreeningScheduleWithMovieIdAndTheaterIdUseCase
import woowacourse.movie.usecase.FetchScreeningsWithMovieIdAndTheaterIdUseCase
import woowacourse.movie.util.runOnOtherThreadAndReturn

class MovieDetailPresenter(
    private val view: MovieDetailContract.View,
    private val fetchScreeningScheduleWithMovieIdAndTheaterIdUseCase: FetchScreeningScheduleWithMovieIdAndTheaterIdUseCase,
    private val fetchScreeningsWithMovieIdAndTheaterId: FetchScreeningsWithMovieIdAndTheaterIdUseCase,
) : MovieDetailContract.Presenter {
    private var count = HeadCount(1)
    private lateinit var bookingDetailUiModel: BookingDetailUiModel
    private lateinit var scheduleUiModels: ScheduleUiModels
    private var datePosition: Int = -1
    private var timePosition: Int = -1

    private var movieId: Long = -1L
    private var theaterId: Long = -1L
    private lateinit var screeningSchedule: ScreeningSchedule

    override fun loadMovieDetail(
        movieId: Long,
        theaterId: Long,
    ) {
        this.movieId = movieId
        this.theaterId = theaterId
        runOnOtherThreadAndReturn {
            fetchScreeningScheduleWithMovieIdAndTheaterIdUseCase(movieId, theaterId)
        }.onSuccess {
            screeningSchedule = it
            bookingDetailUiModel =
                BookingDetailUiModel(
                    HeadCount.MIN_COUNT,
                    it.startDate,
                    it.schedules.first().times.first(),
                )
            view.showMovieInfo(it.toMovieDetailUiModel())
            scheduleUiModels = it.toScheduleUiModels()
            view.showBookingDetail(
                scheduleUiModels,
                bookingDetailUiModel,
            )
        }.onFailure {
            view.showScreeningMovieError()
        }
    }

    override fun plusCount() {
        count = count.increase()
        view.updateHeadCount(count.toHeadCountUiModel())
    }

    override fun minusCount() {
        count = count.decrease()
        view.updateHeadCount(count.toHeadCountUiModel())
    }

    override fun selectDate(position: Int) {
        val dateString = scheduleUiModels.dates()[position]
        bookingDetailUiModel =
            bookingDetailUiModel.updateDate(
                position, dateString,
            )
        datePosition = position
        view.showTime(scheduleUiModels.timesAt(position))
    }

    override fun selectTime(position: Int) {
        timePosition = position
    }

    override fun confirm() {
        getSelectedScreening().onSuccess {
            val bookingInfoUiModel =
                BookingInfoUiModel(
                    it.id,
                    HeadCountUiModel(count.count),
                )
            view.navigateToSeatSelect(bookingInfoUiModel)
        }.onFailure {
            view.showMovieReservationError()
            error(it)
        }
    }

    private fun getSelectedScreening(): Result<Screening> =
        runOnOtherThreadAndReturn {
            fetchScreeningsWithMovieIdAndTheaterId(movieId, theaterId)
        }.map { screenings ->
            val dateTime = screeningSchedule.getDateTimeAt(datePosition, timePosition)
            screenings.first { it.localDateTime == dateTime }
        }
}
