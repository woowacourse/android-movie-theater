package woowacourse.movie.view.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import woowacourse.movie.R
import woowacourse.movie.contract.MovieReservationContract
import woowacourse.movie.getSerializableCompat
import woowacourse.movie.model.MovieUiModel
import woowacourse.movie.model.TicketDateUiModel
import woowacourse.movie.presenter.MovieReservationPresenter
import woowacourse.movie.view.Counter
import woowacourse.movie.view.DateSpinner
import woowacourse.movie.view.MovieDateTimePicker
import woowacourse.movie.view.MovieView
import woowacourse.movie.view.TimeSpinner
import java.time.LocalTime

class MovieReservationActivity : AppCompatActivity(), MovieReservationContract.View {
    override val presenter: MovieReservationContract.Presenter by lazy {
        MovieReservationPresenter(this, counter.getCount())
    }
    private val movieUiModel: MovieUiModel by lazy { getMovieModelView() }
    private val counter: Counter by lazy {
        Counter(
            findViewById(R.id.movie_reservation_people_count_minus),
            findViewById(R.id.movie_reservation_people_count_plus),
            findViewById(R.id.movie_reservation_people_count),
            COUNTER_SAVE_STATE_KEY
        )
    }
    private val movieDateTimePicker: MovieDateTimePicker by lazy {
        MovieDateTimePicker(
            DateSpinner(
                findViewById(R.id.movie_reservation_date_spinner),
                DATE_SPINNER_SAVE_STATE_KEY,
            ), TimeSpinner(
                findViewById(R.id.movie_reservation_time_spinner),
                TIME_SPINNER_SAVE_STATE_KEY,
            )
        )
    }
    private val reservationButton: Button by lazy {
        findViewById(R.id.movie_reservation_button)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_reservation)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        counter.load(savedInstanceState)
        renderMovieView(movieUiModel)
        counter.setButtonsClick(presenter::onMinusTicketCount, presenter::onPlusTicketCount)
        movieDateTimePicker.setDateList(movieUiModel)
        movieDateTimePicker.setDateSelectListener(presenter::onSelectDate, savedInstanceState)
        reservationButtonClick(presenter::onReservationButtonClick)
    }

    private fun renderMovieView(movieUiModel: MovieUiModel) {
        MovieView(
            poster = findViewById(R.id.movie_reservation_poster),
            title = findViewById(R.id.movie_reservation_title),
            date = findViewById(R.id.movie_reservation_date),
            runningTime = findViewById(R.id.movie_reservation_running_time),
            description = findViewById(R.id.movie_reservation_description)
        ).render(movieUiModel)
    }

    private fun finishActivityWithMessage(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
        finish()
    }

    private fun getMovieModelView(): MovieUiModel {
        val movieUiModel = intent.extras?.getSerializableCompat<MovieUiModel>(MOVIE_KEY_VALUE)
            ?: finishActivityWithMessage(getString(R.string.movie_data_null_error))
        return movieUiModel as MovieUiModel
    }

    private fun reservationButtonClick(clickEvent: () -> Unit) {
        reservationButton.setOnClickListener {
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

    override fun setTimeSpinner(times: List<LocalTime>) {
        movieDateTimePicker.timeSpinner.setAdapter(times)
    }

    override fun setCounterText(count: Int) {
        counter.applyToView(count.toString())
    }

    override fun startSeatSelectActivity(peopleCount: Int) {
        SelectSeatActivity.start(
            this,
            peopleCount,
            TicketDateUiModel(movieDateTimePicker.getSelectedDateTime()),
            movieUiModel
        )
    }

    companion object {
        private const val MOVIE_KEY_VALUE = "movie"
        fun start(context: Context, movieUiModel: MovieUiModel) {
            val intent = Intent(context, MovieReservationActivity::class.java)
            intent.putExtra(MOVIE_KEY_VALUE, movieUiModel)
            context.startActivity(intent)
        }

        private const val COUNTER_SAVE_STATE_KEY = "counter"
        private const val DATE_SPINNER_SAVE_STATE_KEY = "date_spinner"
        private const val TIME_SPINNER_SAVE_STATE_KEY = "time_spinner"
    }
}
