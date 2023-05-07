package woowacourse.movie.activity.moviedetail

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.woowacourse.domain.Counter
import com.woowacourse.domain.MovieSchedule
import woowacourse.movie.BundleKeys.MOVIE_BOOKING_INFO_KEY
import woowacourse.movie.BundleKeys.MOVIE_DATA_KEY
import woowacourse.movie.DateFormatter
import woowacourse.movie.R
import woowacourse.movie.activity.BackButtonActivity
import woowacourse.movie.activity.seatpicker.SeatPickerActivity
import woowacourse.movie.databinding.ActivityMovieDetailBinding
import woowacourse.movie.getSerializableCompat
import woowacourse.movie.movie.Movie
import woowacourse.movie.movie.MovieBookingInfo
import java.time.LocalDate

class MovieDetailActivity : BackButtonActivity(), MovieDetailContract.View {
    private lateinit var binding: ActivityMovieDetailBinding
    override lateinit var presenter: MovieDetailContract.Presenter
    private var needSpinnerInitialize = true

    private val dateSpinner: Spinner by lazy { findViewById(R.id.sp_movie_date) }
    private val timeSpinner: Spinner by lazy { findViewById(R.id.sp_movie_time) }
    private val personCountTextView by lazy { findViewById<TextView>(R.id.tv_ticket_count) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_movie_detail)
        presenter = MovieDetailPresenter(this, movieData = getMovieData())
        needSpinnerInitialize = true

        val movieData = getMovieData()
        finishIfDummyData(movieData)

        initView(movieData)

        val movieSchedule = MovieSchedule(movieData.startDate, movieData.endDate)
        val scheduleDate = movieSchedule.getScheduleDates()

        initView(movieData)
        setClickListener(movieData)
        setSpinnerSelectedListener(movieSchedule, scheduleDate, savedInstanceState)
        setSpinnerAdapter(scheduleDate, movieSchedule)
        reloadTicketCountInstance(savedInstanceState)
    }

    private fun getMovieData() = (intent.getSerializableCompat(MOVIE_DATA_KEY) ?: Movie.dummyData)

    private fun finishIfDummyData(movieData: Movie) {
        if (movieData == Movie.dummyData) {
            Toast.makeText(this, getString(R.string.cant_get_movie_data), Toast.LENGTH_SHORT).show()
            this.finish()
        }
    }

    private fun initView(movieData: Movie) {
        binding.ivMoviePoster.setImageResource(movieData.poster)
        binding.tvMovieTitle.text = movieData.title
        binding.tvMovieScreeningPeriod.text =
            getString(
                R.string.movie_screening_period,
                DateFormatter.format(movieData.startDate),
                DateFormatter.format(movieData.endDate)
            )
        binding.tvMovieRunningTime.text =
            getString(R.string.movie_running_time, movieData.runningTime)
        binding.tvMovieSynopsis.text = movieData.synopsis
    }

    private fun setClickListener(movieData: Movie) {

        binding.btTicketCountMinus.setOnClickListener {
            if (presenter.isMinTicketCount()) {
                Toast.makeText(this, getString(R.string.cant_lower_one), Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            presenter.subTicket()
        }

        binding.btTicketCountPlus.setOnClickListener {
            presenter.addTicket()
        }

        binding.btToSeatPicker.setOnClickListener {
            startSeatPickerPage(movieData)
            finish()
        }
    }

    override fun startSeatPickerPage(movieData: Movie) {
        val intent = SeatPickerActivity.intent(this)
        intent.putExtra(
            MOVIE_BOOKING_INFO_KEY,
            MovieBookingInfo(
                movieData.title,
                DateFormatter.format(LocalDate.parse(dateSpinner.selectedItem.toString())),
                timeSpinner.selectedItem.toString(),
                presenter.getTicketCount()
            )
        )
        startActivity(intent)
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
                if (needSpinnerInitialize && savedInstanceState != null) {
                    timeSpinner.setSelection(
                        (savedInstanceState.getString(TIME_KEY) ?: movieSchedule.getScheduleTimes(
                            dateSpinner.selectedItem.toString()
                        ).first()).toInt()
                    )
                    needSpinnerInitialize = false
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) = Unit
        }
    }

    private fun setSpinnerAdapter(scheduleDate: List<String>, movieSchedule: MovieSchedule) {
        dateSpinner.adapter = ArrayAdapter(
            this,
            android.R.layout.simple_spinner_item,
            scheduleDate
        )
        timeSpinner.adapter = ArrayAdapter(
            this,
            android.R.layout.simple_spinner_item,
            movieSchedule.getScheduleTimes(scheduleDate.first())
        )
    }

    private fun reloadTicketCountInstance(savedInstanceState: Bundle?) {
        if (savedInstanceState != null) {
            presenter = MovieDetailPresenter(
                this,
                Counter(savedInstanceState.getString(TICKET_COUNT_KEY)?.toInt() ?: 1),
                getMovieData()
            )
            setTicketCountText(presenter.getTicketCount())
        }
    }

    override fun setTicketCountText(count: Int) {
        personCountTextView.text = count.toString()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString(TIME_KEY, timeSpinner.selectedItemPosition.toString())
        outState.putInt(DATE_KEY, dateSpinner.selectedItemPosition)
        outState.putString(TICKET_COUNT_KEY, personCountTextView.text.toString())
    }

    companion object {
        private const val TICKET_COUNT_KEY = "ticketCount"
        private const val DATE_KEY = "date"
        private const val TIME_KEY = "time"

        fun intent(context: Context) = Intent(context, MovieDetailActivity::class.java)
    }
}
