package woowacourse.movie.view.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import woowacourse.movie.R
import woowacourse.movie.contract.MovieReservationContract
import woowacourse.movie.databinding.ActivityMovieReservationBinding
import woowacourse.movie.getSerializableCompat
import woowacourse.movie.model.*
import woowacourse.movie.presenter.MovieReservationPresenter
import woowacourse.movie.view.Counter
import woowacourse.movie.view.DateSpinner
import woowacourse.movie.view.MovieDateTimePicker
import woowacourse.movie.view.MovieView
import woowacourse.movie.view.TimeSpinner
import java.time.LocalDate
import java.time.LocalTime

class MovieReservationActivity : AppCompatActivity(), MovieReservationContract.View {
    override val presenter: MovieReservationContract.Presenter by lazy {
        MovieReservationPresenter(this, counter.getCount())
    }

    private lateinit var binding: ActivityMovieReservationBinding
    private val movieUiModel: MovieUiModel by lazy { receiveMovieUiModel() }
    private val theaterUiModel: TheaterUiModel by lazy { receiveTheaterUiModel() }
    private val counter: Counter by lazy {
        Counter(
            binding.movieReservationPeopleCountMinus,
            binding.movieReservationPeopleCountPlus,
            binding.movieReservationPeopleCount,
            COUNTER_SAVE_STATE_KEY
        )
    }
    private val movieDateTimePicker: MovieDateTimePicker by lazy {
        MovieDateTimePicker(
            DateSpinner(
                binding.movieReservationDateSpinner,
                DATE_SPINNER_SAVE_STATE_KEY,
            ), TimeSpinner(
                binding.movieReservationTimeSpinner,
                TIME_SPINNER_SAVE_STATE_KEY,
            )
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_movie_reservation)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        initView(savedInstanceState)
        setViewListener(savedInstanceState)
    }

    private fun initView(savedInstanceState: Bundle?) {
        counter.load(savedInstanceState)
        renderMovieView(movieUiModel)
        counter.setButtonsClick(presenter::onMinusTicketCount, presenter::onPlusTicketCount)
        presenter.updateDateSpinner(theaterUiModel)
    }

    private fun setViewListener(savedInstanceState: Bundle?) {
        movieDateTimePicker.setDateSelectListener(
            theaterUiModel,
            presenter::onSelectDate,
            savedInstanceState
        )
        reservationButtonClick(presenter::onReservationButtonClick)
    }

    private fun renderMovieView(movieUiModel: MovieUiModel) {
        MovieView(
            poster = binding.movieReservationPoster,
            title = binding.movieReservationTitle,
            date = binding.movieReservationDate,
            runningTime = binding.movieReservationRunningTime,
            description = binding.movieReservationDescription
        ).render(movieUiModel)
    }

    private fun finishActivityWithMessage(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
        finish()
    }

    private fun receiveMovieUiModel(): MovieUiModel {
        val movieUiModel = intent.extras?.getSerializableCompat<MovieUiModel>(MOVIE_KEY_VALUE)
            ?: finishActivityWithMessage(getString(R.string.movie_data_null_error))
        return movieUiModel as MovieUiModel
    }

    private fun receiveTheaterUiModel(): TheaterUiModel {
        val theaterUiModel = intent.extras?.getSerializableCompat<TheaterUiModel>(THEATER_KEY_VALUE)
            ?: finishActivityWithMessage(getString(R.string.movie_data_null_error))
        return theaterUiModel as TheaterUiModel
    }


    private fun reservationButtonClick(clickEvent: () -> Unit) {
        binding.movieReservationButton.setOnClickListener {
            clickEvent()
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        counter.save(outState)
        movieDateTimePicker.save(outState)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> finish()
        }
        return super.onOptionsItemSelected(item)
    }

    override fun setDateSpinner(date: List<LocalDate>) {
        movieDateTimePicker.dateSpinner.setAdapter(date)
    }

    override fun setTimeSpinner(times: List<LocalTime>) {
        movieDateTimePicker.timeSpinner.setAdapter(times)
    }

    override fun setCounterText(count: Int) {
        counter.applyToView(count.toString())
    }

    override fun startSeatSelectActivity(peopleCount: Int) {
        val ticketOfficeUiModel = TicketOfficeUiModel(
            TicketsUiModel(listOf()),
            peopleCount,
            theaterUiModel.name,
            movieDateTimePicker.getSelectedDateTime(),
        )
        SelectSeatActivity.start(
            this,
            ticketOfficeUiModel,
            movieUiModel
        )
    }

    companion object {
        private const val MOVIE_KEY_VALUE = "movie"
        private const val THEATER_KEY_VALUE = "Theater"
        fun start(context: Context, movieUiModel: MovieUiModel, theaterUiModel: TheaterUiModel) {
            val intent = Intent(context, MovieReservationActivity::class.java)
            intent.putExtra(MOVIE_KEY_VALUE, movieUiModel)
            intent.putExtra(THEATER_KEY_VALUE, theaterUiModel)
            context.startActivity(intent)
        }

        private const val COUNTER_SAVE_STATE_KEY = "counter"
        private const val DATE_SPINNER_SAVE_STATE_KEY = "date_spinner"
        private const val TIME_SPINNER_SAVE_STATE_KEY = "time_spinner"
    }
}
