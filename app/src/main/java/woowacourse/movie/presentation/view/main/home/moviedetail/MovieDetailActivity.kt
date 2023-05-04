package woowacourse.movie.presentation.view.main.home.moviedetail

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ImageView
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.core.os.bundleOf
import woowacourse.movie.R
import woowacourse.movie.model.Movie
import woowacourse.movie.model.MovieBookingInfo
import woowacourse.movie.presentation.extension.getParcelableCompat
import woowacourse.movie.presentation.view.common.BackButtonActivity
import woowacourse.movie.presentation.view.main.home.seatpick.SeatPickerActivity

class MovieDetailActivity : BackButtonActivity(), MovieDetailContract.View {

    private var restoreInstanceFlag = true
    private val presenter: MovieDetailContract.Presenter by lazy {
        MovieDetailPresenter(
            view = this,
            movie = intent.getParcelableCompat(MOVIE_DATA_INTENT_KEY)
        )
    }

    private val dateSpinner: Spinner by lazy { findViewById(R.id.sp_movie_date) }
    private val timeSpinner: Spinner by lazy { findViewById(R.id.sp_movie_time) }
    private val ticketCountTextView: TextView by lazy { findViewById(R.id.tv_ticket_count) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_detail)
        restoreInstanceFlag = true
        presenter.onCreate()
        presenter.getMovieSchedule()
        setClickListener()
        reloadTicketCountInstance(savedInstanceState)
    }

    override fun initView(movie: Movie) {
        findViewById<ImageView>(R.id.iv_movie_poster).setImageResource(movie.poster)
        findViewById<TextView>(R.id.tv_movie_title).text = movie.title
        findViewById<TextView>(R.id.tv_movie_release_date).text = movie.releaseDate
        findViewById<TextView>(R.id.tv_movie_running_time).text = movie.runningTime
        findViewById<TextView>(R.id.tv_movie_synopsis).text = movie.synopsis
    }

    override fun finishErrorView() {
        Toast.makeText(this, getString(R.string.error_intent_message), Toast.LENGTH_SHORT)
            .show()
        this.finish()
    }

    override fun updateScheduleDateView(scheduleDate: List<String>, scheduleTime: List<String>) {
        dateSpinner.adapter = ArrayAdapter(
            this,
            android.R.layout.simple_spinner_item,
            scheduleDate
        )
        timeSpinner.adapter = ArrayAdapter(
            this,
            android.R.layout.simple_spinner_item,
            scheduleTime
        )
    }

    private fun reloadTicketCountInstance(
        savedInstanceState: Bundle?,
    ) {
        if (savedInstanceState != null) {
            ticketCountTextView.text =
                savedInstanceState.getString(USER_TICKET_COUNT_BUNDLE_KEY)
        }
    }

    override fun updateScheduleTimeView(movieScheduleTime: List<String>) {
        dateSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                adapterView: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                timeSpinner.adapter = ArrayAdapter(
                    this@MovieDetailActivity,
                    android.R.layout.simple_spinner_item,
                    movieScheduleTime
                )
                if (restoreInstanceFlag && bundleOf().isEmpty.not()) {
                    timeSpinner.setSelection(
                        (
                                bundleOf().getString(MOVIE_INFO_TIME_BUNDLE_KEY)
                                    ?: movieScheduleTime.first()
                                ).toInt()
                    )
                    restoreInstanceFlag = false
                }
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {}
        }
    }

    private fun setClickListener() {
        findViewById<Button>(R.id.bt_ticket_count_minus).setOnClickListener {
            presenter.removeTicket(ticketCountTextView.text.toString().toInt())
        }

        findViewById<Button>(R.id.bt_ticket_count_plus).setOnClickListener {
            presenter.addTicket(ticketCountTextView.text.toString().toInt())
        }

        findViewById<Button>(R.id.bt_book_complete).setOnClickListener {
            presenter.getMovieBookingInfo(
                ticketCountTextView.text.toString().toInt(),
                dateSpinner.selectedItem.toString(),
                timeSpinner.selectedItem.toString()
            )
        }
    }

    override fun updateTicketCountView(currentTicketCount: Int) {
        ticketCountTextView.text = currentTicketCount.toString()
    }

    override fun showErrorTicketCountIsZeroView() {
        Toast.makeText(
            this,
            getString(R.string.error_booking_over_one_ticket),
            Toast.LENGTH_SHORT
        ).show()
    }

    override fun showInfoSelectedView(movieBookingInfo: MovieBookingInfo) {
        val intent = SeatPickerActivity.getIntent(this).apply {
            putExtra(
                SeatPickerActivity.MOVIE_BOOKING_INFO_SCHEDULE_INTENT_KEY,
                movieBookingInfo
            )
        }
        startActivity(intent)
    }


    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString(
            MOVIE_INFO_TIME_BUNDLE_KEY,
            timeSpinner.selectedItemPosition.toString()
        )
        outState.putString(
            USER_TICKET_COUNT_BUNDLE_KEY,
            ticketCountTextView.text.toString()
        )
    }

    companion object {
        const val MOVIE_DATA_INTENT_KEY = "MOVIE_DATA_INTENT_KEY"
        const val MOVIE_INFO_TIME_BUNDLE_KEY = "MOVIE_INFO_TIME_BUNDLE_KEY"
        const val USER_TICKET_COUNT_BUNDLE_KEY = "USER_TICKET_COUNT_BUNDLE_KEY"

        fun getIntent(context: Context): Intent {
            return Intent(context, MovieDetailActivity::class.java)
        }
    }
}
