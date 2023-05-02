package woowacourse.movie.presentation.activities.ticketing

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
import woowacourse.movie.presentation.activities.seatpicker.SeatPickerActivity
import woowacourse.movie.presentation.activities.ticketing.contract.TicketingContract
import woowacourse.movie.presentation.activities.ticketing.listener.OnSpinnerItemSelectedListener
import woowacourse.movie.presentation.activities.ticketing.presenter.TicketingPresenter
import woowacourse.movie.presentation.extensions.getParcelableCompat
import woowacourse.movie.presentation.extensions.showBackButton
import woowacourse.movie.presentation.extensions.showToast
import woowacourse.movie.presentation.model.MovieDate
import woowacourse.movie.presentation.model.MovieTime
import woowacourse.movie.presentation.model.Ticket
import woowacourse.movie.presentation.model.movieitem.ListItem
import woowacourse.movie.presentation.model.movieitem.Movie

class TicketingActivity : AppCompatActivity(), TicketingContract.View, View.OnClickListener {
    override val presenter: TicketingContract.Presenter by lazy {
        TicketingPresenter(
            view = this,
            movie = intent.getParcelableCompat(MOVIE_KEY)!!
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
        presenter.onCreate()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        presenter.onSaveState(outState)
    }

    override fun saveViewState(
        outState: Bundle,
        ticket: Ticket,
        movieDate: MovieDate?,
        movieTime: MovieTime?
    ) {
        outState.putParcelable(TICKET_COUNT_STATE_KEY, ticket)
        outState.putParcelable(SELECTED_DATE_STATE_KEY, movieDate)
        outState.putParcelable(SELECTED_TIME_STATE_KEY, movieTime)
    }

    override fun onRestoreInstanceState(bundle: Bundle) {
        super.onRestoreInstanceState(bundle)
        val ticket = bundle.getParcelableCompat<Ticket>(TICKET_COUNT_STATE_KEY) ?: return
        val movieDate = bundle.getParcelableCompat<MovieDate>(SELECTED_DATE_STATE_KEY) ?: return
        val movieTime = bundle.getParcelableCompat<MovieTime>(SELECTED_TIME_STATE_KEY) ?: return
        presenter.onRestoreState(ticket, movieDate, movieTime)
    }

    override fun restoreViewState(ticketCount: Int, movieDatePos: Int, movieTimePos: Int) {
        updateCount(ticketCount)
        updateSpinnersState(
            movieDatePos = movieDatePos,
            movieTimePos = movieTimePos,
        )
    }

    override fun initView(movieDates: List<MovieDate>) {
        showBackButton()
        showMovieIntroduce()
        initSpinnerConfig()
        updateMovieDates(movieDates)
        initViewClickListener()
    }

    private fun showMovieIntroduce() {
        intent.getParcelableCompat<Movie>(MOVIE_KEY)?.run {
            findViewById<ImageView>(R.id.poster_iv).setImageResource(thumbnail)
            findViewById<TextView>(R.id.title_tv).text = title
            findViewById<TextView>(R.id.date_tv).text = getString(
                R.string.movie_release_date,
                startDate.formattedDate,
                endDate.formattedDate
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

    override fun updateCount(value: Int) {
        ticketCountTextView.text = value.toString()
    }

    override fun updateRunningTimes(runningTimes: List<MovieTime>) {
        val runningTimeTexts: List<String> =
            runningTimes.map { getString(R.string.book_time, it.hour, it.min) }
        movieTimeAdapter.clear()
        movieTimeAdapter.addAll(runningTimeTexts)
    }

    override fun updateSpinnersState(movieDatePos: Int, movieTimePos: Int) {
        movieDateSpinner.setSelection(movieDatePos)
        movieTimeSpinner.setSelection(movieTimePos)
    }

    override fun startSeatPickerActivity(
        movie: Movie,
        ticket: Ticket,
        selectedDate: MovieDate,
        selectedTime: MovieTime
    ) {
        val seatPickerIntent =
            SeatPickerActivity.getIntent(this, movie, ticket, selectedDate, selectedTime)
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

    companion object {
        private const val MOVIE_KEY = "movie_key"

        internal const val SELECTED_DATE_STATE_KEY = "selectedDate"
        internal const val SELECTED_TIME_STATE_KEY = "selectedTime"
        internal const val TICKET_COUNT_STATE_KEY = "ticketCountState"

        fun getIntent(context: Context, movie: ListItem): Intent =
            Intent(context, TicketingActivity::class.java).putExtra(MOVIE_KEY, movie)
    }
}
