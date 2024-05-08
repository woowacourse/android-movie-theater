package woowacourse.movie.moviedetail

import woowacourse.movie.model.HeadCount
import woowacourse.movie.moviedetail.uimodel.BookingDetailUiModel
import woowacourse.movie.moviedetail.uimodel.toHeadCountUiModel
import woowacourse.movie.moviedetail.uimodel.toMovieDetailUiModel
import woowacourse.movie.moviedetail.uimodel.toScreeningDateTimeUiModel
import woowacourse.movie.repository.MovieRepository

class MovieDetailPresenter(
    private val view: MovieDetailContract.View,
    private val repository: MovieRepository,
) : MovieDetailContract.Presenter {
    private var count = HeadCount(1)

    override fun loadMovieDetail(screeningId: Long) {
        val screening = repository.screeningById(screeningId)
        if (screening != null) {
            view.showMovieInfo(screening.toMovieDetailUiModel())
            view.showBookingDetail(
                screening.toScreeningDateTimeUiModel(),
                BookingDetailUiModel(
                    HeadCount.MIN_COUNT,
                    screening.startDate,
                    screening.schedules.first().times.first(),
                ),
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
}
