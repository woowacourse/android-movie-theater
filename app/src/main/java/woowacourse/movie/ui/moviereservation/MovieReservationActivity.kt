package woowacourse.movie.ui.moviereservation

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import domain.Movie
import woowacourse.movie.R
import woowacourse.movie.data.model.Counter
import woowacourse.movie.data.model.DateSpinner
import woowacourse.movie.data.model.MovieDateTimePicker
import woowacourse.movie.data.model.TimeSpinner
import woowacourse.movie.data.model.mapper.MovieMapper
import woowacourse.movie.data.model.uimodel.TheaterUIModel
import woowacourse.movie.data.model.uimodel.TicketDateUIModel
import woowacourse.movie.databinding.ActivityMovieReservationBinding
import woowacourse.movie.getSerializableCompat
import woowacourse.movie.ui.selectseat.SelectSeatActivity

class MovieReservationActivity : AppCompatActivity(), MovieReservationContract.View {
    override lateinit var presenter: MovieReservationContract.Presenter

    private var _binding: ActivityMovieReservationBinding? = null
    private val binding
        get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        _binding = DataBindingUtil.setContentView(this, R.layout.activity_movie_reservation)

        setUpUiModels()
        setUpPresenter()
        setUpDateTimePicker(savedInstanceState)
        setUpCounter(savedInstanceState)
        setUpOnCLick()
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    private fun setUpUiModels() {
        binding.movie = intent.extras?.getSerializableCompat(MOVIE_KEY_VALUE) ?: throw IllegalStateException(MOVIE_DATA_NOT_FOUND_ERROR)
        binding.theater = intent.extras?.getSerializableCompat(THEATER_KEY_VALUE) ?: throw IllegalStateException(MOVIE_DATA_NOT_FOUND_ERROR)
    }

    private fun setUpPresenter() {
        presenter = MovieReservationPresenter(
            view = this,
            counter = Counter(binding.tvReservationCount, COUNTER_SAVE_STATE_KEY),
            picker = MovieDateTimePicker(
                DateSpinner(
                    binding.spReservationDate,
                    DATE_SPINNER_SAVE_STATE_KEY,
                ),
                TimeSpinner(
                    binding.spReservationTime,
                    TIME_SPINNER_SAVE_STATE_KEY,
                    binding.theater!!
                )
            )
        )
    }

    private fun setUpCounter(savedInstanceState: Bundle?) {
        presenter.setCount(savedInstanceState)
    }

    private fun setUpDateTimePicker(savedInstanceState: Bundle?) {
        presenter.setPicker(binding.movie!!, savedInstanceState)
    }

    private fun setUpOnCLick() {
        binding.btnMinus.setOnClickListener {
            presenter.sub()
        }
        binding.btnPlus.setOnClickListener {
            presenter.add()
        }
        binding.btnMovieReservation.setOnClickListener {
            val intent = SelectSeatActivity.getIntent(
                context = this,
                peopleCount = presenter.getCount(),
                ticketDateUiModel = TicketDateUIModel(presenter.getSelectedDateTime()),
                movieUiModel = binding.movie!!,
                theaterUiModel = binding.theater!!
            )
            startActivity(intent)
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)

        presenter.save(outState)
    }

    override fun setCounterText(count: Int) {
        binding.tvReservationCount.text = count.toString()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> finish()
        }
        return super.onOptionsItemSelected(item)
    }

    companion object {
        private const val MOVIE_KEY_VALUE = "movie"
        private const val THEATER_KEY_VALUE = "theater"
        private const val COUNTER_SAVE_STATE_KEY = "counter"
        private const val DATE_SPINNER_SAVE_STATE_KEY = "date_spinner"
        private const val TIME_SPINNER_SAVE_STATE_KEY = "time_spinner"
        private const val MOVIE_DATA_NOT_FOUND_ERROR = "영화 데이터가 찾을 수 없습니다."

        fun getIntent(context: Context, movie: Movie, theaterUiModel: TheaterUIModel): Intent {
            val intent = Intent(context, MovieReservationActivity::class.java)
            val movieUiModel = MovieMapper.toUI(movie)
            intent.putExtra(MOVIE_KEY_VALUE, movieUiModel)
            intent.putExtra(THEATER_KEY_VALUE, theaterUiModel)
            return intent
        }
    }
}
