package woowacourse.movie.feature.seatselection

import woowacourse.movie.model.MovieSeat
import woowacourse.movie.model.MovieSelectedSeats

interface MovieSeatSelectionContract {
    interface View {
        fun displayMovieTitle(movieTitle: String)

        fun setUpTableSeats(baseSeats: List<MovieSeat>)

        fun updateSeatBackgroundColor(
            index: Int,
            isSelected: Boolean,
        )

        fun displayDialog()

        fun updateSelectResult(movieSelectedSeats: MovieSelectedSeats)

        fun navigateToResultView(movieSelectedSeats: MovieSelectedSeats)

        fun showToastInvalidMovieIdError(throwable: Throwable)
    }

    interface Presenter {
        fun loadMovieTitle(movieId: Long)

        fun loadTableSeats(count: Int)

        fun clickTableSeat(index: Int)

        fun clickPositiveButton()

        fun updateSelectedSeats(count: Int)
    }
}
