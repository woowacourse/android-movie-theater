package woowacourse.movie.presentation.booking

import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import woowacourse.movie.R
import woowacourse.movie.presentation.model.MovieModel
import woowacourse.movie.presentation.util.formatDotDate

class BookingMovieInformationView(private val view: ViewGroup, movieModel: MovieModel) {

    init {
        initView(movieModel)
    }

    private fun initView(movieModel: MovieModel) {
        setMoviePoster(movieModel)
        setMovieTitle(movieModel)
        setMovieScreeningDate(movieModel)
        setMovieRunningTime(movieModel)
        setMovieDescription(movieModel)
    }

    private fun setMoviePoster(movieModel: MovieModel) {
        movieModel.poster.let {
            view.findViewById<ImageView>(R.id.imageBookingPoster).setImageResource(it)
        }
    }

    private fun setMovieTitle(movieModel: MovieModel) {
        view.findViewById<TextView>(R.id.textBookingTitle).text = movieModel.title
    }

    private fun setMovieScreeningDate(movieModel: MovieModel) {
        view.findViewById<TextView>(R.id.textBookingScreeningDate).text =
            view.context.getString(R.string.screening_date).format(
                movieModel.screeningStartDate.formatDotDate(),
                movieModel.screeningEndDate.formatDotDate()
            )
    }

    private fun setMovieRunningTime(movieModel: MovieModel) {
        view.findViewById<TextView>(R.id.textBookingRunningTime).text =
            view.context.getString(R.string.running_time).format(movieModel.runningTime)
    }

    private fun setMovieDescription(movieModel: MovieModel) {
        view.findViewById<TextView>(R.id.textBookingDescription).text = movieModel.description
    }
}
