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
import woowacourse.movie.util.MovieIntent.MOVIE_TICKET_ID

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
            this,
            intent.getLongExtra(MOVIE_TICKET_ID.key, MOVIE_TICKET_ID.invalidValue as Long),
        )
        movieResultPresenter.registrationMovieNotification(
            this,
            intent.getLongExtra(MOVIE_TICKET_ID.key, MOVIE_TICKET_ID.invalidValue),
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
            ticketId: Long,
        ): Intent {
            return Intent(context, MovieResultActivity::class.java).apply {
                putExtra(MOVIE_TICKET_ID.key, ticketId)
            }
        }
    }
}
