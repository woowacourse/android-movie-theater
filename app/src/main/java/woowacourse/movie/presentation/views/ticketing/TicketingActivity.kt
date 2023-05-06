package woowacourse.movie.presentation.views.ticketing

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ImageView
import android.widget.Spinner
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import woowacourse.movie.R
import woowacourse.movie.presentation.extensions.getParcelableCompat
import woowacourse.movie.presentation.extensions.showBackButton
import woowacourse.movie.presentation.extensions.showToast
import woowacourse.movie.presentation.model.MovieDate
import woowacourse.movie.presentation.model.MovieTime
import woowacourse.movie.presentation.model.TicketingState
import woowacourse.movie.presentation.model.movieitem.ListItem
import woowacourse.movie.presentation.model.movieitem.Movie
import woowacourse.movie.presentation.model.theater.Theater
import woowacourse.movie.presentation.views.seatpicker.SeatPickerActivity
import woowacourse.movie.presentation.views.ticketing.contract.TicketingContract
import woowacourse.movie.presentation.views.ticketing.listener.OnSpinnerItemSelectedListener
import woowacourse.movie.presentation.views.ticketing.presenter.TicketingPresenter

class TicketingActivity : AppCompatActivity(), TicketingContract.View, View.OnClickListener {
    override val presenter: TicketingContract.Presenter by lazy {
        TicketingPresenter(
            state = TicketingState(
                movie = intent.getParcelableCompat(MOVIE_KEY)!!,
                theater = intent.getParcelableCompat(THEATER_KEY)!!
            )
        )
    }

    private val movieDateAdapter: ArrayAdapter<String> by lazy {
        ArrayAdapter(this, android.R.layout.simple_spinner_item, mutableListOf())
    }
    private val movieTimeAdapter: ArrayAdapter<String> by lazy {
        ArrayAdapter(this, android.R.layout.simple_spinner_item, mutableListOf())
    }

    private val movieTimeSpinner: Spinner by lazy { findViewById(R.id.movie_time_spinner) }
    private val movieDateSpinner: Spinner by lazy { findViewById(R.id.movie_date_spinner) }

    private val ticketCountTextView: TextView by lazy { findViewById(R.id.ticket_count_tv) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ticketing)
        presenter.attach(this)
    }

    override fun onSaveInstanceState(bundle: Bundle) {
        super.onSaveInstanceState(bundle)
        bundle.putParcelable(TICKETING_STATE_KEY, presenter.getState())
    }

    override fun onRestoreInstanceState(bundle: Bundle) {
        super.onRestoreInstanceState(bundle)
        val ticketingState = bundle.getParcelableCompat<TicketingState>(TICKETING_STATE_KEY)
        ticketingState?.let { presenter.setState(it) }
    }

    override fun initView(movie: Movie, movieDates: List<MovieDate>) {
        showBackButton()
        showMovieIntroduce(movie)
        initSpinnerConfig()
        updateMovieDates(movieDates)
        initViewClickListener()
    }

    private fun showMovieIntroduce(movie: Movie) {
        with(movie) {
            findViewById<ImageView>(R.id.poster_iv).setImageResource(thumbnail)
            findViewById<TextView>(R.id.title_tv).text = title
            findViewById<TextView>(R.id.date_tv).text = getString(
                R.string.movie_release_date,
                formattedStartDate,
                formattedEndDate,
            )
            findViewById<TextView>(R.id.running_time_tv).text =
                getString(R.string.movie_running_time, runningTime)
            findViewById<TextView>(R.id.introduce_tv).text = introduce
        }
    }

    private fun initSpinnerConfig() {
        initSpinnerAdapter()
        initSpinnerListener()
    }

    private fun initSpinnerAdapter() {
        movieDateSpinner.adapter = movieDateAdapter.also { it.setNotifyOnChange(true) }
        movieTimeSpinner.adapter = movieTimeAdapter.also { it.setNotifyOnChange(true) }
    }

    private fun initSpinnerListener() {
        initMovieTimeSpinnerListener()
        initMovieDateSpinnerListener()
    }

    private fun updateMovieDates(movieDates: List<MovieDate>) {
        val movieDateTexts =
            movieDates.map { getString(R.string.book_date, it.year, it.month, it.day) }
        movieDateAdapter.clear()
        movieDateAdapter.addAll(movieDateTexts)
    }

    private fun initViewClickListener() {
        findViewById<Button>(R.id.minus_btn).setOnClickListener(this@TicketingActivity)
        findViewById<Button>(R.id.plus_btn).setOnClickListener(this@TicketingActivity)
        findViewById<Button>(R.id.ticketing_btn).setOnClickListener(this@TicketingActivity)
    }

    private fun initMovieTimeSpinnerListener() {
        movieTimeSpinner.onItemSelectedListener =
            OnSpinnerItemSelectedListener { presenter.onSelectMovieTime(it) }
    }

    private fun initMovieDateSpinnerListener() {
        movieDateSpinner.onItemSelectedListener =
            OnSpinnerItemSelectedListener { presenter.onSelectMovieDate(it) }
    }

    override fun showTicketingState(ticketCount: Int, movieDatePos: Int, movieTimePos: Int) {
        updateCount(ticketCount)
        updateSpinnersState(movieDatePos = movieDatePos, movieTimePos = movieTimePos)
    }

    override fun updateCount(value: Int) {
        ticketCountTextView.text = value.toString()
    }

    override fun updateSpinnersState(movieDatePos: Int, movieTimePos: Int) {
        movieDateSpinner.setSelection(movieDatePos)
        movieTimeSpinner.setSelection(movieTimePos)
    }

    override fun updateRunningTimes(runningTimes: List<MovieTime>) {
        val runningTimeTexts: List<String> =
            runningTimes.map { getString(R.string.book_time, it.hour, it.min) }
        movieTimeAdapter.clear()
        movieTimeAdapter.addAll(runningTimeTexts)
    }

    override fun showSeatPickerScreen(ticketingState: TicketingState) {
        val seatPickerIntent = SeatPickerActivity.getIntent(this, ticketingState)
        startActivity(seatPickerIntent)
        finish()
    }

    override fun showUnSelectDateTimeAlertMessage() {
        showToast(getString(R.string.select_date_and_time))
    }

    override fun onClick(view: View) {
        when (view.id) {
            R.id.minus_btn -> presenter.minusCount()
            R.id.plus_btn -> presenter.plusCount()
            R.id.ticketing_btn -> presenter.onClickTicketingButton()
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> finish()
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onDestroy() {
        presenter.detach()
        super.onDestroy()
    }

    companion object {
        private const val MOVIE_KEY = "movie_key"
        private const val THEATER_KEY = "theater_key"
        private const val TICKETING_STATE_KEY = "ticketing_state_key"

        fun getIntent(context: Context, movie: ListItem, theater: Theater): Intent =
            Intent(context, TicketingActivity::class.java)
                .putExtra(MOVIE_KEY, movie)
                .putExtra(THEATER_KEY, theater)
    }
}
