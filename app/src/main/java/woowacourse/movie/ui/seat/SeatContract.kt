package woowacourse.movie.ui.seat
import woowacourse.movie.movie.Movie

interface SeatContract {

    interface View {

        fun initMovieTitleText(movieTitle: String)
    }

    interface Presenter {

        val movie: Movie

        fun initMovieTitle()
    }
}
