package woowacourse.movie.view.theater

import woowacourse.movie.domain.model.Movie
import woowacourse.movie.domain.model.TheaterUIModel

interface TheaterContract {
    interface View {
        fun showTheaters(theaters: List<TheaterUIModel>)

        fun navigateToReservation(theaterUIModel: TheaterUIModel)
    }

    interface Presenter {
        fun fetchTheaters(movie: Movie)

        fun theaterSelected(theaterUIModel: TheaterUIModel)
    }
}
