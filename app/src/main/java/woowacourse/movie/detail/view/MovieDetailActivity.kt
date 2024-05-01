package woowacourse.movie.detail.view

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import woowacourse.movie.R
import woowacourse.movie.data.MovieRepository.getMovieById
import woowacourse.movie.databinding.ActivityMovieDetailBinding
import woowacourse.movie.detail.presenter.MovieDetailPresenter
import woowacourse.movie.detail.presenter.contract.MovieDetailContract
import woowacourse.movie.model.Movie
import woowacourse.movie.model.MovieCount
import woowacourse.movie.model.MovieDate
import woowacourse.movie.seatselection.view.MovieSeatSelectionActivity
import woowacourse.movie.util.MovieIntentConstant.INVALID_VALUE_MOVIE_ID
import woowacourse.movie.util.MovieIntentConstant.INVALID_VALUE_THEATER_NAME
import woowacourse.movie.util.MovieIntentConstant.INVALID_VALUE_THEATER_POSITION
import woowacourse.movie.util.MovieIntentConstant.KEY_ITEM_POSITION
import woowacourse.movie.util.MovieIntentConstant.KEY_MOVIE_COUNT
import woowacourse.movie.util.MovieIntentConstant.KEY_MOVIE_DATE
import woowacourse.movie.util.MovieIntentConstant.KEY_MOVIE_ID
import woowacourse.movie.util.MovieIntentConstant.KEY_MOVIE_TIME
import woowacourse.movie.util.MovieIntentConstant.KEY_SELECTED_THEATER_NAME
import woowacourse.movie.util.MovieIntentConstant.KEY_SELECTED_THEATER_POSITION
import java.time.LocalTime
import java.time.format.DateTimeFormatter

class MovieDetailActivity : AppCompatActivity(), MovieDetailContract.View {
    private lateinit var movieDetailPresenter: MovieDetailPresenter
    private val movieId: Long by lazy { intent.getLongExtra(KEY_MOVIE_ID, INVALID_VALUE_MOVIE_ID) }
    private val selectedTheaterPosition: Int by lazy {
        intent.getIntExtra(
            KEY_SELECTED_THEATER_POSITION,
            INVALID_VALUE_THEATER_POSITION,
        )
    }

    private lateinit var binding: ActivityMovieDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_movie_detail)
        binding.activity = this

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        movieDetailPresenter = MovieDetailPresenter(this)
        movieDetailPresenter.loadMovieDetail(movieId, selectedTheaterPosition)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)

        val position = binding.runningTimeSpinner.selectedItemPosition
        outState.putInt(KEY_ITEM_POSITION, position)

        outState.putInt(KEY_MOVIE_COUNT, binding.reservationCount)
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
        movieCount: MovieCount,
    ) {
        movieData?.let { movie ->
            binding.movie = movie
            binding.reservationCount = movieCount.count
        }
    }

    override fun updateCountView(count: Int) {
        binding.reservationCount = count
    }

    override fun setUpDateSpinner(movieDate: MovieDate) {
        binding.dateSpinner.adapter =
            ArrayAdapter(
                this,
                androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,
                movieDate.generateDates(),
            )
    }

    override fun setUpTimeSpinner(screeningTimes: List<LocalTime>) {
        val times =
            screeningTimes.map { time ->
                time.format(DateTimeFormatter.ofPattern("kk:mm"))
            }

        binding.runningTimeSpinner.adapter =
            ArrayAdapter(
                this,
                androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,
                times,
            )
    }

    override fun navigateToSeatSelectionView(
        id: Long,
        date: String,
        time: String,
        count: Int,
    ) {
        val theaterName =
            getMovieById(movieId)?.let {
                it.theaters[selectedTheaterPosition].name
            } ?: INVALID_VALUE_THEATER_NAME

        Intent(this, MovieSeatSelectionActivity::class.java).apply {
            putExtra(KEY_MOVIE_ID, id)
            putExtra(KEY_MOVIE_DATE, date)
            putExtra(KEY_MOVIE_TIME, time)
            putExtra(KEY_MOVIE_COUNT, count)
            putExtra(KEY_SELECTED_THEATER_NAME, theaterName)
            startActivity(this)
        }
    }

    fun onMinusButtonClick() {
        movieDetailPresenter.minusReservationCount()
        binding.invalidateAll()
    }

    fun onPlusButtonClick() {
        movieDetailPresenter.plusReservationCount()
        binding.invalidateAll()
    }

    fun onSeatSelectionButtonClick() {
        movieDetailPresenter.reserveMovie(
            movieId,
            binding.dateSpinner.selectedItem.toString(),
            binding.runningTimeSpinner.selectedItem.toString(),
        )
    }
}
