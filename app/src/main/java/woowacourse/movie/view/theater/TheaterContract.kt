package woowacourse.movie.view.theater

import androidx.annotation.StringRes
import woowacourse.movie.model.Movie
import woowacourse.movie.model.TheaterUIModel

interface TheaterContract {
    interface View {
        fun showTheaters(theaters: List<TheaterUIModel>)

        fun navigateToReservation(theaterUIModel: TheaterUIModel)

        fun dismissView()

        fun showErrorMessage(
            @StringRes messageResId: Int,
        )
    }

    interface Presenter {
        fun fetchTheaters(getMovie: () -> Movie?)

        fun theaterSelected(theaterUIModel: TheaterUIModel)
    }
}
