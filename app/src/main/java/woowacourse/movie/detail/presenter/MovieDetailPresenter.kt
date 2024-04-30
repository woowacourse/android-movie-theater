package woowacourse.movie.detail.presenter

import woowacourse.movie.data.MovieRepository.getMovieById
import woowacourse.movie.detail.presenter.contract.MovieDetailContract
import woowacourse.movie.model.MovieCount
import woowacourse.movie.model.MovieDate.Companion.isWeekend
import woowacourse.movie.model.MovieTime
import java.time.LocalDate

class MovieDetailPresenter(
    private val movieDetailContractView: MovieDetailContract.View,
) : MovieDetailContract.Presenter {
    private var movieCount: MovieCount = MovieCount()
    private var timeSpinnerPosition: Int = 0

    override fun loadMovieDetail(
        movieId: Long,
        theaterPosition: Int,
    ) {
        val movieData = getMovieById(movieId)
        movieData?.let { movie ->
            movieDetailContractView.displayMovieDetail(movie, movieCount)
            movieDetailContractView.setUpDateSpinner(movie.date)
            movieDetailContractView.setUpTimeSpinner(movie.theaters[theaterPosition].screeningTimes)
        }
    }

    override fun loadTimeSpinnerItem(localDate: LocalDate) {
        val movieTime = MovieTime(isWeekend(localDate))
    }

    override fun updateTimeSpinnerPosition(position: Int) {
        timeSpinnerPosition = position
    }

    override fun updateReservationCount(count: Int) {
        movieCount = movieCount.update(count)
        movieDetailContractView.updateCountView(movieCount.count)
    }

    override fun plusReservationCount() {
        movieCount = movieCount.inc()
        movieDetailContractView.updateCountView(movieCount.count)
    }

    override fun minusReservationCount() {
        movieCount = movieCount.dec()
        movieDetailContractView.updateCountView(movieCount.count)
    }

    override fun reserveMovie(
        id: Long,
        date: String,
        time: String,
    ) {
        movieDetailContractView.navigateToSeatSelectionView(
            id,
            date,
            time,
            movieCount.count,
        )
    }
}
