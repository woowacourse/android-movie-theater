package woowacourse.movie.ui.reservation

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.databinding.BindingAdapter
import androidx.databinding.DataBindingUtil
import woowacourse.movie.R
import woowacourse.movie.databinding.ActivityMovieReservationBinding
import woowacourse.movie.model.data.MovieContentsImpl
import woowacourse.movie.model.data.TheatersImpl
import woowacourse.movie.model.data.UserTicketsImpl
import woowacourse.movie.model.movie.MovieContent
import woowacourse.movie.model.movie.Theater
import woowacourse.movie.ui.base.BaseActivity
import woowacourse.movie.ui.selection.MovieSeatSelectionActivity
import woowacourse.movie.ui.utils.getImageFromId
import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter

class MovieReservationActivity :
    BaseActivity<MovieReservationPresenter>(),
    MovieReservationContract.View {
    private lateinit var binding: ActivityMovieReservationBinding
    private val theaterId by lazy { theaterId() }
    private val movieContentId by lazy { movieContentId() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_movie_reservation)
        binding.presenter = presenter

        if (movieContentId == DEFAULT_VALUE || theaterId == DEFAULT_VALUE) {
            presenter.handleError(NoSuchElementException())
            return
        }

        presenter.loadMovieContent(movieContentId, theaterId)
        presenter.updateReservationCount()
        setOnClickMovieTimeSpinnerListener()
        setOnClickMovieDateSpinnerListener()

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)

        outState.putInt(
            RESERVATION_COUNT_STATE_KEY,
            binding.tvReservationCount.text.toString().toInt(),
        )
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)

        savedInstanceState.let {
            val count = it.getInt(RESERVATION_COUNT_STATE_KEY)
            presenter.updateReservationCount(count)
        }
    }

    private fun setOnClickMovieTimeSpinnerListener() {
        binding.spScreeningTime.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long,
                ) {
                    val value = binding.spScreeningTime.getItemAtPosition(position)
                    presenter.selectTime(value as LocalTime)
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {
                }
            }
    }

    private fun setOnClickMovieDateSpinnerListener() {
        binding.spScreeningDate.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long,
                ) {
                    val movieDate = binding.spScreeningDate.getItemAtPosition(position)
                    presenter.selectDate(movieDate as LocalDate)
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {
                }
            }
    }

    override fun initializePresenter() = MovieReservationPresenter(this, MovieContentsImpl, TheatersImpl, UserTicketsImpl)

    private fun theaterId() = intent.getLongExtra(MovieReservationKey.THEATER_ID, DEFAULT_VALUE)

    private fun movieContentId() = intent.getLongExtra(MovieReservationKey.MOVIE_CONTENT_ID, DEFAULT_VALUE)

    override fun showError(throwable: Throwable) {
        Toast.makeText(this, resources.getString(R.string.invalid_key), Toast.LENGTH_LONG).show()
        finish()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> finish()
        }
        return super.onOptionsItemSelected(item)
    }

    override fun showMovieContent(
        movieContent: MovieContent,
        theater: Theater,
    ) {
        binding.movieContent = movieContent
        binding.theater = theater
    }

    override fun updateReservationCount(reservationCount: Int) {
        binding.tvReservationCount.text = reservationCount.toString()
    }

    override fun moveMovieSeatSelectionPage(userTicketId: Long) {
        MovieSeatSelectionActivity.startActivity(this, userTicketId)
    }

    companion object {
        private const val DEFAULT_VALUE = -1L
        private const val RESERVATION_COUNT_STATE_KEY = "reservationCount"

        fun startActivity(
            context: Context,
            movieContentId: Long,
            theaterId: Long,
        ) = Intent(context, MovieReservationActivity::class.java).run {
            putExtra(MovieReservationKey.MOVIE_CONTENT_ID, movieContentId)
            putExtra(MovieReservationKey.THEATER_ID, theaterId)
            context.startActivity(this)
        }
    }
}

@BindingAdapter("imgRes")
fun setImageViewResource(
    imageView: ImageView,
    imageName: String,
) {
    imageView.apply {
        setImageResource(imageName.getImageFromId(context))
    }
}

@BindingAdapter("openingDate", "endingDate")
fun setScreeningDate(
    textView: TextView,
    openingDate: LocalDate,
    endingDate: LocalDate,
) {
    textView.apply {
        val formattedOpeningDate =
            openingDate.format(
                DateTimeFormatter.ofPattern(context.getString(R.string.reservation_screening_date_format)),
            )
        val formattedEndingDate =
            endingDate.format(
                DateTimeFormatter.ofPattern(context.getString(R.string.reservation_screening_date_format)),
            )
        text = context.getString(R.string.screening_date, formattedOpeningDate, formattedEndingDate)
    }
}
