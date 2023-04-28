package woowacourse.movie.presentation.view.main.home.moviedetail

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ImageView
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import com.example.domain.MovieSchedule
import woowacourse.movie.R
import woowacourse.movie.model.Movie
import woowacourse.movie.model.MovieBookingInfo
import woowacourse.movie.presentation.extension.getParcelableCompat
import woowacourse.movie.presentation.view.common.BackButtonActivity
import woowacourse.movie.presentation.view.main.home.seatpick.SeatPickerActivity

class MovieDetailActivity : BackButtonActivity() {
    private var restoreInstanceFlag = true

    private val dateSpinner: Spinner by lazy { findViewById(R.id.sp_movie_date) }
    private val timeSpinner: Spinner by lazy { findViewById(R.id.sp_movie_time) }
    private val ticketCountTextView: TextView by lazy { findViewById(R.id.tv_ticket_count) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_detail)
        restoreInstanceFlag = true
        val movieData = intent.getParcelableCompat<Movie>(MOVIE_DATA_INTENT_KEY)
        processEmptyMovieData(movieData)

        setViewData(movieData)

        val movieSchedule =
            MovieSchedule(movieData!!.startDate, movieData.endDate)
        val scheduleDate = movieSchedule.getScheduleDates()

        setViewData(movieData)
        setClickListener(movieData)
        setSpinnerSelectedListener(movieSchedule, scheduleDate, savedInstanceState)
        setSpinnerAdapter(scheduleDate, movieSchedule)
        reloadTicketCountInstance(savedInstanceState)
    }

    private fun setSpinnerAdapter(
        scheduleDate: List<String>,
        movieSchedule: MovieSchedule
    ) {
        dateSpinner.adapter = ArrayAdapter(
            this,
            android.R.layout.simple_spinner_item,
            scheduleDate
        )
        timeSpinner.adapter = ArrayAdapter(
            this,
            android.R.layout.simple_spinner_item,
            movieSchedule.getScheduleTimes(scheduleDate[0])
        )
    }

    private fun setViewData(movieData: Movie?) {
        findViewById<ImageView>(R.id.iv_movie_poster).setImageResource(movieData!!.poster)
        findViewById<TextView>(R.id.tv_movie_title).text = movieData.title
        findViewById<TextView>(R.id.tv_movie_release_date).text = movieData.releaseDate
        findViewById<TextView>(R.id.tv_movie_running_time).text = movieData.runningTime
        findViewById<TextView>(R.id.tv_movie_synopsis).text = movieData.synopsis
    }

    private fun reloadTicketCountInstance(
        savedInstanceState: Bundle?,
    ) {
        if (savedInstanceState != null) {
            ticketCountTextView.text =
                savedInstanceState.getString(USER_TICKET_COUNT_BUNDLE_KEY)
        }
    }

    private fun processEmptyMovieData(movieData: Movie?) {
        if (movieData == null) {
            Toast.makeText(this, getString(R.string.error_intent_message), Toast.LENGTH_SHORT)
                .show()
            this.finish()
        }
    }

    private fun setSpinnerSelectedListener(
        movieSchedule: MovieSchedule,
        scheduleDate: List<String>,
        savedInstanceState: Bundle?
    ) {

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
                    movieSchedule.getScheduleTimes(scheduleDate[position])
                )
                if (restoreInstanceFlag && savedInstanceState != null) {
                    timeSpinner.setSelection(
                        (
                                savedInstanceState.getString(MOVIE_INFO_TIME_BUNDLE_KEY)
                                    ?: movieSchedule.getScheduleTimes(dateSpinner.selectedItem.toString())
                                        .first()
                                ).toInt()
                    )
                    restoreInstanceFlag = false
                }
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {}
        }
    }

    private fun setClickListener(movieData: Movie) {

        findViewById<Button>(R.id.bt_ticket_count_minus).setOnClickListener {
            if (ticketCountTextView.text == BASE_TICKET_COUNT_CHARACTER) {
                Toast.makeText(
                    this,
                    getString(R.string.error_booking_over_one_ticket),
                    Toast.LENGTH_SHORT
                ).show()
                return@setOnClickListener
            }
            val newTicketCount = ticketCountTextView.text.toString().toInt() - 1
            ticketCountTextView.text = newTicketCount.toString()
        }

        findViewById<Button>(R.id.bt_ticket_count_plus).setOnClickListener {
            val newTicketCount = ticketCountTextView.text.toString().toInt() + 1
            ticketCountTextView.text = newTicketCount.toString()
        }

        findViewById<Button>(R.id.bt_book_complete).setOnClickListener {
            val intent = SeatPickerActivity.getIntent(this).apply {
                putExtra(
                    SeatPickerActivity.MOVIE_BOOKING_INFO_SCHEDULE_INTENT_KEY,
                    MovieBookingInfo(
                        movieData, dateSpinner.selectedItem.toString(),
                        timeSpinner.selectedItem.toString(),
                        ticketCountTextView.text.toString().toInt()
                    )
                )
            }
            startActivity(intent)
        }
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
        private const val BASE_TICKET_COUNT_CHARACTER = "1"
        const val MOVIE_DATA_INTENT_KEY = "MOVIE_DATA_INTENT_KEY"
        const val MOVIE_INFO_TIME_BUNDLE_KEY = "MOVIE_INFO_TIME_BUNDLE_KEY"
        const val USER_TICKET_COUNT_BUNDLE_KEY = "USER_TICKET_COUNT_BUNDLE_KEY"
    }
}
