package woowacourse.movie.ui.moviedetail.presenter

import android.content.Context
import android.content.Intent
import woowacourse.movie.model.MovieListModel.MovieModel
import woowacourse.movie.ui.seat.SeatSelectionActivity
import woowacourse.movie.utils.getParcelableCompat

class MovieDetailPresenter(
    private val view: MovieDetailContract.View,
    private val movieDetailContext: Context,
) : MovieDetailContract.Presenter {
    private lateinit var movie: MovieModel

    override fun getMovieData(intent: Intent) {
        movie = intent.getParcelableCompat(KEY_MOVIE) ?: throw IllegalArgumentException()

        view.apply {
            setMovieInfo(movie)
            setEventOnBookingButton(::moveToSeatSelectionActivity)
        }
    }

    override fun initSpinner() {
        val dateTimeSpinner = view.dateTimeSpinnerView

        dateTimeSpinner.apply {
            setDateSpinner(movie)
            setTimeSpinner()
        }
    }

    override fun moveToSeatSelectionActivity() {
        val intent = Intent(movieDetailContext, SeatSelectionActivity::class.java).apply {
            putExtra(KEY_TITLE, movie.title)
            putExtra(KEY_TIME, view.dateTimeSpinnerView.selectedItem)
            putExtra(KEY_PEOPLE_COUNT, view.peopleCountControllerView.peopleCountModel)
        }
        movieDetailContext.startActivity(intent)
    }

    companion object {
        private const val KEY_TITLE = "title"
        private const val KEY_TIME = "time"
        private const val KEY_PEOPLE_COUNT = "count"
        private const val KEY_MOVIE = "movie"
    }
}
