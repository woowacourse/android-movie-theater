package woowacourse.movie.feature.seatselection

import woowacourse.movie.data.MovieRepository
import woowacourse.movie.model.MovieSelectedSeats

class MovieSeatSelectionPresenter(
    private val view: MovieSeatSelectionContract.View,
) : MovieSeatSelectionContract.Presenter {
    lateinit var movieSelectedSeats: MovieSelectedSeats

    override fun loadMovieTitle(movieId: Long) {
        val movie =
            runCatching {
                MovieRepository.getMovieById(movieId)
            }.getOrElse {
                view.showToastInvalidMovieIdError(it)
                return
            }

        view.displayMovieTitle(movie.title)
    }

    override fun loadTableSeats(count: Int) {
        movieSelectedSeats = MovieSelectedSeats(count)
        view.setUpTableSeats(movieSelectedSeats.getBaseSeats())
    }

    override fun updateSelectedSeats(count: Int) {
        movieSelectedSeats = MovieSelectedSeats(count)
    }

    override fun clickTableSeat(index: Int) {
        val seat = movieSelectedSeats.getBaseSeats()[index]
        if (movieSelectedSeats.isSelected(index)) {
            view.updateSeatBackgroundColor(index, true)
            movieSelectedSeats.unSelectSeat(seat)
        } else {
            if (!movieSelectedSeats.isSelectionComplete()) {
                view.updateSeatBackgroundColor(index, false)
                movieSelectedSeats.selectSeat(seat)
            }
        }
        view.updateSelectResult(movieSelectedSeats)
    }

    override fun clickPositiveButton() {
        view.navigateToResultView(movieSelectedSeats)
    }
}
