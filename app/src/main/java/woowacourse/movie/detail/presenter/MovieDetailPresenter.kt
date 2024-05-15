package woowacourse.movie.detail.presenter

import woowacourse.movie.data.repository.HomeContentRepository.getMovieById
import woowacourse.movie.detail.presenter.contract.MovieDetailContract
import woowacourse.movie.model.MovieReservationCount

class MovieDetailPresenter(
    private val movieDetailContractView: MovieDetailContract.View,
) : MovieDetailContract.Presenter {
    private var movieReservationCount: MovieReservationCount = MovieReservationCount()

    override fun loadMovieDetail(
        movieId: Long,
        theaterPosition: Int,
    ) {
        val movieData = getMovieById(movieId)
        movieData?.let { movie ->
            movieDetailContractView.displayMovieDetail(movie, movieReservationCount)
            movieDetailContractView.setUpDateSpinner(movie.date)
            movieDetailContractView.setUpTimeSpinner(movie.theaters[theaterPosition].screeningTimes)
        }
    }

    override fun updateTimeSpinnerPosition(position: Int) {
        movieDetailContractView.setUpTimeSpinnerPosition(position)
    }

    override fun updateReservationCount(count: Int) {
        movieReservationCount = movieReservationCount.update(count)
        movieDetailContractView.updateCountView(movieReservationCount.count)
    }

    override fun plusReservationCount() {
        movieReservationCount = movieReservationCount.inc()
        movieDetailContractView.updateCountView(movieReservationCount.count)
    }

    override fun minusReservationCount() {
        movieReservationCount = movieReservationCount.dec()
        movieDetailContractView.updateCountView(movieReservationCount.count)
    }

    override fun reserveMovie(
        movieId: Long,
        date: String,
        time: String,
    ) {
        movieDetailContractView.navigateToSeatSelectionView(
            movieId,
            date,
            time,
            movieReservationCount.count,
        )
    }
}
