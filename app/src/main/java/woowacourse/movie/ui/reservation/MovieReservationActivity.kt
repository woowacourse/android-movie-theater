package woowacourse.movie.ui.reservation

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.databinding.BindingAdapter
import androidx.databinding.DataBindingUtil
import woowacourse.movie.R
import woowacourse.movie.databinding.ActivityMovieReservationBinding
import woowacourse.movie.model.movie.MovieContent
import woowacourse.movie.model.movie.MovieContentDao
import woowacourse.movie.model.movie.MovieDatabase
import woowacourse.movie.model.movie.ReservationDetail
import woowacourse.movie.model.movie.Theater
import woowacourse.movie.model.movie.TheaterDao
import woowacourse.movie.ui.base.BaseActivity
import woowacourse.movie.ui.selection.MovieSeatSelectionActivity
import woowacourse.movie.ui.utils.getImageFromId
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class MovieReservationActivity :
    BaseActivity<MovieReservationPresenter>(),
    MovieReservationContract.View {
    private lateinit var binding: ActivityMovieReservationBinding
    private val theaterDao: TheaterDao by lazy { MovieDatabase.getDatabase(applicationContext).theaterDao() }
    private val movieContentDao: MovieContentDao by lazy { MovieDatabase.getDatabase(applicationContext).movieContentDao() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_movie_reservation)
        binding.presenter = presenter

        val theaterId = intent.getLongExtra(MovieReservationKey.THEATER_ID, DEFAULT_VALUE)
        val movieContentId =
            intent.getLongExtra(MovieReservationKey.MOVIE_CONTENT_ID, DEFAULT_VALUE)
        if (movieContentId == DEFAULT_VALUE || theaterId == DEFAULT_VALUE) {
            presenter.handleError(NoSuchElementException())
            return
        }

        presenter.loadScreeningContent(movieContentId, theaterId)
        presenter.updateReservationCount()
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)

        outState.putInt(
            RESERVATION_COUNT_STATE_KEY,
            binding.reservationCountText.text.toString().toInt(),
        )
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)

        savedInstanceState.let {
            val count = it.getInt(RESERVATION_COUNT_STATE_KEY)
            presenter.updateReservationCount(count)
        }
    }

    override fun initializePresenter() =
        MovieReservationPresenter(this, movieContentDao, theaterDao)

    override fun showError(throwable: Throwable) {
        Toast.makeText(this, resources.getString(R.string.toast_invalid_key), Toast.LENGTH_LONG)
            .show()
        finish()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> finish()
        }
        return super.onOptionsItemSelected(item)
    }

    override fun updateReservationCount(reservationCount: Int) {
        binding.reservationCountText.text = reservationCount.toString()
    }

    override fun moveMovieSeatSelectionPage(reservationDetail: ReservationDetail) {
        Intent(this, MovieSeatSelectionActivity::class.java)
            .putExtra(MovieReservationKey.RESERVATION_DETAIL, reservationDetail)
            .also(::startActivity)
    }

    override fun showScreeningContent(
        movieContent: MovieContent,
        theater: Theater,
    ) {
        runOnUiThread {
            binding.movieContent = movieContent
            binding.theater = theater
            binding.executePendingBindings()
        }
    }

    companion object {
        private const val DEFAULT_VALUE = -1L
        private const val RESERVATION_COUNT_STATE_KEY = "reservationCount"
    }
}

@BindingAdapter("drawableResourceId")
fun setImageViewResource(
    imageView: ImageView,
    imageName: String?,
) {
    imageName?.let {
        imageView.setImageResource(it.getImageFromId(imageView.context))
    }
}

@BindingAdapter("openingDate", "endingDate")
fun setScreeningDate(
    textView: TextView,
    openingDate: LocalDate?,
    endingDate: LocalDate?,
) {
    if (openingDate != null && endingDate != null) {
        val context = textView.context
        val formattedOpeningDate =
            openingDate.format(DateTimeFormatter.ofPattern(context.getString(R.string.reservation_screening_date_format)))
        val formattedEndingDate =
            endingDate.format(DateTimeFormatter.ofPattern(context.getString(R.string.reservation_screening_date_format)))
        textView.text =
            context.getString(R.string.home_screening_date, formattedOpeningDate, formattedEndingDate)
    }
}
