package woowacourse.movie.view.home.theater

import woowacourse.movie.domain.Movie
import woowacourse.movie.domain.Showings

interface TheaterContract {
    interface Presenter {
        fun fetchData(movie: Movie)
    }

    interface View {
        fun handleInvalidTicket()

        fun showTheaterList(showings: List<Showings>)
    }
}
