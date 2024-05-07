package woowacourse.movie.detail.view

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import woowacourse.movie.data.MovieRepository.getMovieById
import woowacourse.movie.databinding.ActivityMovieDetailBinding
import woowacourse.movie.detail.presenter.MovieDetailPresenter
import woowacourse.movie.detail.presenter.contract.MovieDetailContract
import woowacourse.movie.detail.view.listener.MovieDetailClickListener
import woowacourse.movie.home.view.adapter.movie.HomeContent.Movie
import woowacourse.movie.model.MovieDate
import woowacourse.movie.model.MovieReservationCount
import woowacourse.movie.seatselection.view.MovieSeatSelectionActivity
import woowacourse.movie.util.MovieIntentConstant.INVALID_VALUE_MOVIE_ID
import woowacourse.movie.util.MovieIntentConstant.INVALID_VALUE_THEATER_NAME
import woowacourse.movie.util.MovieIntentConstant.INVALID_VALUE_THEATER_POSITION
import woowacourse.movie.util.MovieIntentConstant.KEY_ITEM_POSITION
import woowacourse.movie.util.MovieIntentConstant.KEY_MOVIE_COUNT
import woowacourse.movie.util.MovieIntentConstant.KEY_MOVIE_ID
import woowacourse.movie.util.MovieIntentConstant.KEY_SELECTED_THEATER_POSITION
import java.time.LocalTime
import java.time.format.DateTimeFormatter

class MovieDetailActivity :
    AppCompatActivity(), MovieDetailContract.View, MovieDetailClickListener {
    private lateinit var binding: ActivityMovieDetailBinding
    private lateinit var movieDetailPresenter: MovieDetailPresenter

    private val movieId: Long by lazy { intent.getLongExtra(KEY_MOVIE_ID, INVALID_VALUE_MOVIE_ID) }
    private val selectedTheaterPosition: Int by lazy {
        intent.getIntExtra(
            KEY_SELECTED_THEATER_POSITION,
            INVALID_VALUE_THEATER_POSITION,
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        binding = ActivityMovieDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.detailClickListener = this

        movieDetailPresenter = MovieDetailPresenter(this)
        movieDetailPresenter.loadMovieDetail(movieId, selectedTheaterPosition)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)

        val position = binding.spinnerDetailDate.selectedItemPosition
        outState.putInt(KEY_ITEM_POSITION, position)

        val count = binding.reservationCount
        outState.putInt(KEY_MOVIE_COUNT, count)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)

        val savedPosition = savedInstanceState.getInt(KEY_ITEM_POSITION)
        movieDetailPresenter.updateTimeSpinnerPosition(savedPosition)

        val savedCount = savedInstanceState.getInt(KEY_MOVIE_COUNT)
        movieDetailPresenter.updateReservationCount(savedCount)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) finish()
        return super.onOptionsItemSelected(item)
    }

    override fun displayMovieDetail(
        movieData: Movie?,
        movieReservationCount: MovieReservationCount,
    ) {
        movieData?.let { movie ->
            binding.movie = movie
            binding.reservationCount = movieReservationCount.count
        }
    }

    override fun updateCountView(count: Int) {
        binding.reservationCount = count
    }

    override fun setUpDateSpinner(movieDate: MovieDate) {
        binding.dates =
            movieDate.generateDates().map { date ->
                date.toString()
            }
    }

    override fun setUpTimeSpinner(screeningTimes: List<LocalTime>) {
        binding.times =
            screeningTimes.map { time ->
                time.format(DateTimeFormatter.ofPattern("kk:mm"))
            }
    }

    override fun setUpTimeSpinnerPosition(position: Int) {
        binding.spinnerDetailRunningTime.setSelection(position)
    }

    override fun navigateToSeatSelectionView(
        movieId: Long,
        date: String,
        time: String,
        count: Int,
    ) {
        val theaterName =
            getMovieById(this.movieId)?.let {
                it.theaters[selectedTheaterPosition].name
            } ?: INVALID_VALUE_THEATER_NAME

        val intent =
            MovieSeatSelectionActivity.createIntent(
                baseContext,
                movieId,
                date,
                time,
                count,
                theaterName,
            )
        startActivity(intent)
    }

    override fun onMinusButtonClick() {
        movieDetailPresenter.minusReservationCount()
        binding.invalidateAll()
    }

    override fun onPlusButtonClick() {
        movieDetailPresenter.plusReservationCount()
        binding.invalidateAll()
    }

    override fun onSeatSelectionButtonClick() {
        movieDetailPresenter.reserveMovie(
            movieId,
            binding.spinnerDetailDate.selectedItem.toString(),
            binding.spinnerDetailRunningTime.selectedItem.toString(),
        )
    }

    companion object {
        fun createIntent(
            context: Context,
            movieId: Long,
            position: Int,
        ): Intent {
            return Intent(context, MovieDetailActivity::class.java).apply {
                putExtra(KEY_MOVIE_ID, movieId)
                putExtra(KEY_SELECTED_THEATER_POSITION, position)
            }
        }
    }
}
