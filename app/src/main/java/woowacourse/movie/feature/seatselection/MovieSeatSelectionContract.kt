package woowacourse.movie.feature.seatselection

import woowacourse.movie.model.MovieSeat
import woowacourse.movie.model.MovieSelectedSeats
import woowacourse.movie.util.InvalidMovieIdErrorListener

interface MovieSeatSelectionContract {
    interface View : InvalidMovieIdErrorListener {
        fun displayMovieTitle(movieTitle: String)

        fun setUpTableSeats(baseSeats: List<MovieSeat>)

        fun updateSeatBackgroundColor(
            index: Int,
            isSelected: Boolean,
        )

        fun displayDialog()

        fun updateSelectResult(movieSelectedSeats: MovieSelectedSeats)

        fun navigateToResultView(movieSelectedSeats: MovieSelectedSeats)
    }

    interface Presenter {
        fun loadMovieTitle(movieId: Long)

        fun loadTableSeats(count: Int)

        fun clickTableSeat(index: Int)

        fun clickPositiveButton()

        fun updateSelectedSeats(count: Int)
    }
}
