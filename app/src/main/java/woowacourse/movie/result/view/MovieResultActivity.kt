package woowacourse.movie.result.view

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import woowacourse.movie.MovieMainActivity
import woowacourse.movie.R
import woowacourse.movie.databinding.ActivityMovieResultBinding
import woowacourse.movie.model.MovieTicket
import woowacourse.movie.result.presenter.MovieResultPresenter
import woowacourse.movie.result.presenter.contract.MovieResultContract
import woowacourse.movie.util.Formatter.formatRow
import woowacourse.movie.util.MovieIntentConstant.INVALID_VALUE_MOVIE_COUNT
import woowacourse.movie.util.MovieIntentConstant.INVALID_VALUE_MOVIE_DATE
import woowacourse.movie.util.MovieIntentConstant.INVALID_VALUE_MOVIE_ID
import woowacourse.movie.util.MovieIntentConstant.INVALID_VALUE_MOVIE_SEATS
import woowacourse.movie.util.MovieIntentConstant.INVALID_VALUE_MOVIE_TIME
import woowacourse.movie.util.MovieIntentConstant.INVALID_VALUE_THEATER_NAME
import woowacourse.movie.util.MovieIntentConstant.KEY_MOVIE_COUNT
import woowacourse.movie.util.MovieIntentConstant.KEY_MOVIE_DATE
import woowacourse.movie.util.MovieIntentConstant.KEY_MOVIE_ID
import woowacourse.movie.util.MovieIntentConstant.KEY_MOVIE_SEATS
import woowacourse.movie.util.MovieIntentConstant.KEY_MOVIE_TIME
import woowacourse.movie.util.MovieIntentConstant.KEY_SELECTED_THEATER_NAME

class MovieResultActivity : AppCompatActivity(), MovieResultContract.View {
    private lateinit var binding: ActivityMovieResultBinding
    private lateinit var movieResultPresenter: MovieResultPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        setUpBackButtonAction()

        binding = ActivityMovieResultBinding.inflate(layoutInflater)
        setContentView(binding.root)

        movieResultPresenter = MovieResultPresenter(this)
        movieResultPresenter.loadMovieTicket(
            intent.getLongExtra(KEY_MOVIE_ID, INVALID_VALUE_MOVIE_ID),
            intent.getStringExtra(KEY_MOVIE_DATE) ?: INVALID_VALUE_MOVIE_DATE,
            intent.getStringExtra(KEY_MOVIE_TIME) ?: INVALID_VALUE_MOVIE_TIME,
            intent.getIntExtra(KEY_MOVIE_COUNT, INVALID_VALUE_MOVIE_COUNT),
            intent.getStringExtra(KEY_MOVIE_SEATS) ?: INVALID_VALUE_MOVIE_SEATS,
            intent.getStringExtra(KEY_SELECTED_THEATER_NAME) ?: INVALID_VALUE_THEATER_NAME,
        )
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) finish()
        return super.onOptionsItemSelected(item)
    }

    override fun displayMovieTicket(movieTicketData: MovieTicket?) {
        movieTicketData?.let { movieTicket ->
            binding.movieTicket = movieTicket
            val seats =
                movieTicket.seats.selectedSeats.joinToString(", ") { seat ->
                    getString(R.string.seat, seat.row.formatRow(), seat.column.toString())
                }
            binding.resultInformation =
                resources.getString(
                    R.string.result,
                    movieTicket.seats.count,
                    seats,
                    movieTicket.theaterName,
                )
        }
    }

    private fun setUpBackButtonAction() {
        val onBackPressedDispatcher = onBackPressedDispatcher
        onBackPressedDispatcher.addCallback(
            this,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    val intent = Intent(this@MovieResultActivity, MovieMainActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
                    startActivity(intent)
                }
            },
        )
    }

    companion object {
        fun createIntent(
            context: Context,
            movieId: Long?,
            date: String?,
            time: String?,
            count: Int,
            seats: String,
            theaterName: String?,
        ): Intent {
            return Intent(context, MovieResultActivity::class.java).apply {
                putExtra(KEY_MOVIE_ID, movieId)
                putExtra(KEY_MOVIE_DATE, date)
                putExtra(KEY_MOVIE_TIME, time)
                putExtra(KEY_MOVIE_COUNT, count)
                putExtra(KEY_MOVIE_SEATS, seats)
                putExtra(KEY_SELECTED_THEATER_NAME, theaterName)
            }
        }
    }
}
