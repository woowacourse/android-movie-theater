package woowacourse.movie.feature.home.theater

import woowacourse.movie.model.Theater
import woowacourse.movie.util.InvalidMovieIdErrorListener

interface TheaterSelectionContract {
    interface View : InvalidMovieIdErrorListener {
        fun setUpTheaterAdapter(theaters: List<Theater>)
    }

    interface Presenter {
        fun loadTheaters(movieId: Long)
    }
}
