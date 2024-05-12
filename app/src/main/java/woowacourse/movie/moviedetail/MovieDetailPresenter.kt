package woowacourse.movie.moviedetail

import woowacourse.movie.model.HeadCount
import woowacourse.movie.moviedetail.uimodel.BookingDetailUiModel
import woowacourse.movie.moviedetail.uimodel.BookingInfoUiModel
import woowacourse.movie.moviedetail.uimodel.HeadCountUiModel
import woowacourse.movie.moviedetail.uimodel.ScheduleUiModels
import woowacourse.movie.moviedetail.uimodel.toHeadCountUiModel
import woowacourse.movie.moviedetail.uimodel.toMovieDetailUiModel
import woowacourse.movie.moviedetail.uimodel.toScheduleUiModels
import woowacourse.movie.repository.EverythingRepository
import woowacourse.movie.usecase.FetchAllMoviesUseCase

class MovieDetailPresenter(
    private val view: MovieDetailContract.View,
    private val repository: EverythingRepository,
) : MovieDetailContract.Presenter {

    private var count = HeadCount(1)
    private lateinit var bookingDetailUiModel: BookingDetailUiModel
    private lateinit var scheduleUiModels: ScheduleUiModels
    private var screeningScheduleId: Long = -1
    private var datePosition: Int = -1
    private var timePosition: Int = -1

    override fun loadMovieDetail(screeningScheduleId: Long) {
        this.screeningScheduleId = screeningScheduleId
        val screeningSchedule = repository.screeningScheduleById(screeningScheduleId)
        if (screeningSchedule != null) {
            bookingDetailUiModel =
                BookingDetailUiModel(
                    HeadCount.MIN_COUNT,
                    screeningSchedule.startDate,
                    screeningSchedule.schedules.first().times.first(),
                )
            view.showMovieInfo(screeningSchedule.toMovieDetailUiModel())
            scheduleUiModels = screeningSchedule.toScheduleUiModels()
            view.showBookingDetail(
                scheduleUiModels,
                bookingDetailUiModel,
            )
        } else {
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
        val screeningSchedule = repository.screeningScheduleById(screeningScheduleId)
        if (screeningSchedule != null) {
            val movieId = screeningSchedule.movie.id
            val theaterId = screeningSchedule.theater.id
            val dateTime = screeningSchedule.getDateTimeAt(datePosition, timePosition)
            val screening =
                repository.screeningsByMovieIdAndTheaterId(movieId, theaterId).first {
                    it.localDateTime == dateTime
                }
            val bookingInfoUiModel =
                BookingInfoUiModel(
                    screening.id,
                    HeadCountUiModel(count.count),
                )
            view.navigateToSeatSelect(bookingInfoUiModel)
        }
    }
}
