package woowacourse.movie.feature.result

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.annotation.StringRes
import woowacourse.movie.MovieTheaterApplication
import woowacourse.movie.R
import woowacourse.movie.data.ticket.entity.Ticket
import woowacourse.movie.databinding.ActivityMovieResultBinding
import woowacourse.movie.feature.MovieMainActivity
import woowacourse.movie.feature.result.ui.MovieResultUiModel
import woowacourse.movie.util.BaseActivity
import woowacourse.movie.util.MovieIntentConstant.INVALID_VALUE_TICKET_ID
import woowacourse.movie.util.MovieIntentConstant.KEY_TICKET_ID

class MovieResultActivity :
    BaseActivity<MovieResultContract.Presenter>(),
    MovieResultContract.View {
    private lateinit var binding: ActivityMovieResultBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMovieResultBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initializeView()
    }

    override fun initializePresenter() = MovieResultPresenter(this)

    private fun initializeView() {
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        setUpBackButtonAction()

        presenter.loadTicket(
            (application as MovieTheaterApplication).ticketRepository,
            intent.getLongExtra(KEY_TICKET_ID, INVALID_VALUE_TICKET_ID),
        )
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) backToMovieMainActivity()
        return super.onOptionsItemSelected(item)
    }

    override fun displayTicket(ticket: Ticket) {
        binding.movieResult = MovieResultUiModel.from(ticket)
    }

    override fun showToastInvalidMovieIdError(throwable: Throwable) {
        Log.e(TAG, "invalid movie id - ${throwable.message}")
        showToast(R.string.invalid_movie_id)
        finish()
    }

    private fun showToast(
        @StringRes stringResId: Int,
    ) {
        Toast.makeText(this, resources.getString(stringResId), Toast.LENGTH_SHORT).show()
    }

    private fun setUpBackButtonAction() {
        val onBackPressedCallback =
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    backToMovieMainActivity()
                }
            }
        onBackPressedDispatcher.addCallback(this, onBackPressedCallback)
    }

    private fun backToMovieMainActivity() {
        val intent = Intent(this@MovieResultActivity, MovieMainActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
        startActivity(intent)
        finish()
    }

    companion object {
        private val TAG = MovieResultActivity::class.simpleName

        fun newIntent(
            context: Context,
            ticketId: Long,
        ): Intent {
            return Intent(context, MovieResultActivity::class.java).apply {
                putExtra(KEY_TICKET_ID, ticketId)
            }
        }
    }
}
