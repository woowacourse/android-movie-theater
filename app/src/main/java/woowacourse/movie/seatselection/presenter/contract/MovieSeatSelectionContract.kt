package woowacourse.movie.seatselection.presenter.contract

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

        fun updateSelectedSeats(movieSelectedSeats: MovieSelectedSeats)

        fun navigateToResultView(ticketId: Long)
    }

    interface Presenter {
        fun loadDetailMovie(id: Long)

        fun loadTableSeats(count: Int)

        fun clickTableSeat(index: Int)

        fun clickPositiveButton(
            movieId: Long,
            date: String,
            time: String,
            theaterPosition: Int,
        )

        fun updateSelectedSeats(count: Int)
    }
}
