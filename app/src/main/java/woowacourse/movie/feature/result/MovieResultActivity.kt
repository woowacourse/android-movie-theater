package woowacourse.movie.feature.result

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.annotation.StringRes
import woowacourse.movie.R
import woowacourse.movie.databinding.ActivityMovieResultBinding
import woowacourse.movie.feature.MovieMainActivity
import woowacourse.movie.feature.result.ui.MovieResultUiModel
import woowacourse.movie.model.MovieTicket
import woowacourse.movie.util.BaseActivity
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
import woowacourse.movie.util.MovieIntentConstant.KEY_THEATER_NAME

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

        presenter.loadMovieTicket(
            intent.getLongExtra(KEY_MOVIE_ID, INVALID_VALUE_MOVIE_ID),
            intent.getStringExtra(KEY_MOVIE_DATE) ?: INVALID_VALUE_MOVIE_DATE,
            intent.getStringExtra(KEY_MOVIE_TIME) ?: INVALID_VALUE_MOVIE_TIME,
            intent.getIntExtra(KEY_MOVIE_COUNT, INVALID_VALUE_MOVIE_COUNT),
            intent.getStringExtra(KEY_MOVIE_SEATS) ?: INVALID_VALUE_MOVIE_SEATS,
            intent.getStringExtra(KEY_THEATER_NAME) ?: INVALID_VALUE_THEATER_NAME,
        )
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) finish()
        return super.onOptionsItemSelected(item)
    }

    override fun displayMovieTicket(movieTicket: MovieTicket) {
        binding.movieResult = MovieResultUiModel.from(movieTicket)
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
                    val intent = Intent(this@MovieResultActivity, MovieMainActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
                    startActivity(intent)
                }
            }

        onBackPressedDispatcher.addCallback(this, onBackPressedCallback)
    }

    companion object {
        private val TAG = MovieResultActivity::class.simpleName

        fun newIntent(
            context: Context,
            movieId: Long,
            screeningDate: String?,
            screeningTime: String?,
            movieCount: Int,
            selectedSeats: String,
            theaterName: String?,
        ): Intent {
            return Intent(context, MovieResultActivity::class.java).apply {
                putExtra(KEY_MOVIE_ID, movieId)
                putExtra(KEY_MOVIE_DATE, screeningDate)
                putExtra(KEY_MOVIE_TIME, screeningTime)
                putExtra(KEY_MOVIE_COUNT, movieCount)
                putExtra(KEY_MOVIE_SEATS, selectedSeats)
                putExtra(KEY_THEATER_NAME, theaterName)
            }
        }
    }
}
