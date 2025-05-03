package woowacourse.movie.view.theater

import woowacourse.movie.model.TheaterUIModel
import woowacourse.movie.view.item.Movie

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
