package woowacourse.movie.detail.view

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import woowacourse.movie.databinding.ActivityMovieDetailBinding
import woowacourse.movie.detail.presenter.MovieDetailPresenter
import woowacourse.movie.detail.presenter.contract.MovieDetailContract
import woowacourse.movie.detail.view.listener.MovieDetailClickListener
import woowacourse.movie.home.view.adapter.movie.HomeContent.Movie
import woowacourse.movie.model.MovieDate
import woowacourse.movie.model.MovieReservationCount
import woowacourse.movie.seatselection.view.MovieSeatSelectionActivity
import woowacourse.movie.util.MovieIntent.ITEM_POSITION
import woowacourse.movie.util.MovieIntent.MOVIE_ID
import woowacourse.movie.util.MovieIntent.RESERVATION_COUNT
import woowacourse.movie.util.MovieIntent.SELECTED_THEATER_POSITION
import java.time.LocalTime
import java.time.format.DateTimeFormatter

class MovieDetailActivity :
    AppCompatActivity(), MovieDetailContract.View, MovieDetailClickListener {
    private lateinit var binding: ActivityMovieDetailBinding
    private lateinit var movieDetailPresenter: MovieDetailPresenter

    private val movieId: Long by lazy {
        intent.getLongExtra(
            MOVIE_ID.key,
            MOVIE_ID.invalidValue as Long,
        )
    }
    private val selectedTheaterPosition: Int by lazy {
        intent.getIntExtra(
            SELECTED_THEATER_POSITION.key,
            SELECTED_THEATER_POSITION.invalidValue as Int,
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
        outState.putInt(ITEM_POSITION.key, position)

        val count = binding.reservationCount
        outState.putInt(RESERVATION_COUNT.key, count)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)

        val savedPosition = savedInstanceState.getInt(ITEM_POSITION.key)
        movieDetailPresenter.updateTimeSpinnerPosition(savedPosition)

        val savedCount = savedInstanceState.getInt(RESERVATION_COUNT.key)
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
        val intent =
            MovieSeatSelectionActivity.createIntent(
                baseContext,
                movieId,
                date,
                time,
                count,
                selectedTheaterPosition,
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
                putExtra(MOVIE_ID.key, movieId)
                putExtra(SELECTED_THEATER_POSITION.key, position)
            }
        }
    }
}
