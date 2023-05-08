package woowacourse.movie.ui.moviedetail.presenter

import android.content.Intent
import woowacourse.movie.model.MovieListModel.MovieModel
import woowacourse.movie.ui.common.BaseView
import woowacourse.movie.ui.moviedetail.DateTimeSpinnerView
import woowacourse.movie.ui.moviedetail.PeopleCountControllerView

interface MovieDetailContract {
    interface View : BaseView<Presenter> {
        val dateTimeSpinnerView: DateTimeSpinnerView
        val peopleCountControllerView: PeopleCountControllerView

        fun setMovieInfo(movie: MovieModel)
        fun setEventOnBookingButton(movingToSeatSelectionActivity: () -> Unit)
    }

    interface Presenter {
        fun initSpinner()
        fun getMovieData(intent: Intent)
        fun moveToSeatSelectionActivity()
    }
}
