package woowacourse.movie.result.view

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import woowacourse.movie.MovieMainActivity
import woowacourse.movie.databinding.ActivityMovieResultBinding
import woowacourse.movie.model.MovieTicket
import woowacourse.movie.result.presenter.MovieResultPresenter
import woowacourse.movie.result.presenter.contract.MovieResultContract
import woowacourse.movie.util.MovieIntent.MOVIE_DATE
import woowacourse.movie.util.MovieIntent.MOVIE_ID
import woowacourse.movie.util.MovieIntent.MOVIE_SEATS
import woowacourse.movie.util.MovieIntent.MOVIE_TIME
import woowacourse.movie.util.MovieIntent.RESERVATION_COUNT
import woowacourse.movie.util.MovieIntent.SELECTED_THEATER_POSITION

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
            intent.getLongExtra(MOVIE_ID.key, MOVIE_ID.invalidValue as Long),
            intent.getStringExtra(MOVIE_DATE.key) ?: MOVIE_DATE.invalidValue as String,
            intent.getStringExtra(MOVIE_TIME.key) ?: MOVIE_TIME.invalidValue as String,
            intent.getIntExtra(RESERVATION_COUNT.key, RESERVATION_COUNT.invalidValue as Int),
            intent.getStringExtra(MOVIE_SEATS.key) ?: MOVIE_SEATS.invalidValue as String,
            intent.getIntExtra(
                SELECTED_THEATER_POSITION.key,
                SELECTED_THEATER_POSITION.invalidValue as Int,
            ),
        )
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) finish()
        return super.onOptionsItemSelected(item)
    }

    override fun displayMovieTicket(movieTicketData: MovieTicket?) {
        movieTicketData?.let { movieTicket ->
            binding.movieTicket = movieTicket
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
            movieId: Long,
            date: String,
            time: String,
            count: Int,
            seats: String,
            theaterPosition: Int,
        ): Intent {
            return Intent(context, MovieResultActivity::class.java).apply {
                putExtra(MOVIE_ID.key, movieId)
                putExtra(MOVIE_DATE.key, date)
                putExtra(MOVIE_TIME.key, time)
                putExtra(RESERVATION_COUNT.key, count)
                putExtra(MOVIE_SEATS.key, seats)
                putExtra(SELECTED_THEATER_POSITION.key, theaterPosition)
            }
        }
    }
}
