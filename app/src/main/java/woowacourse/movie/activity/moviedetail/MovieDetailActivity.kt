package woowacourse.movie.activity.moviedetail

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.woowacourse.domain.Counter
import com.woowacourse.domain.MovieSchedule
import woowacourse.movie.BundleKeys.MOVIE_BOOKING_INFO_KEY
import woowacourse.movie.BundleKeys.MOVIE_DATA_KEY
import woowacourse.movie.BundleKeys.THEATER_DATA_KEY
import woowacourse.movie.DateFormatter
import woowacourse.movie.R
import woowacourse.movie.Theater
import woowacourse.movie.activity.BackButtonActivity
import woowacourse.movie.activity.seatpicker.SeatPickerActivity
import woowacourse.movie.databinding.ActivityMovieDetailBinding
import woowacourse.movie.getSerializableCompat
import woowacourse.movie.movie.Movie
import woowacourse.movie.movie.MovieBookingInfo
import java.time.LocalDate

class MovieDetailActivity : BackButtonActivity(), MovieDetailContract.View {
    private var _binding: ActivityMovieDetailBinding? = null
    private val binding get() = _binding!!
    override lateinit var presenter: MovieDetailContract.Presenter
    private var needSpinnerInitialize = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = DataBindingUtil.setContentView(this, R.layout.activity_movie_detail)
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

    private fun getTheaterData() =
        (intent.getSerializableCompat(THEATER_DATA_KEY) ?: Theater.dummyData)

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
                DateFormatter.format(LocalDate.parse(binding.spMovieDate.selectedItem.toString())),
                binding.spMovieTime.selectedItem.toString(),
                presenter.getTicketCount()
            )
        )
        intent.putExtra(
            THEATER_DATA_KEY,
            getTheaterData()
        )
        startActivity(intent)
    }

    private fun setSpinnerSelectedListener(
        movieSchedule: MovieSchedule,
        scheduleDate: List<String>,
        savedInstanceState: Bundle?
    ) {
        binding.spMovieDate.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                adapterView: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                binding.spMovieTime.adapter = ArrayAdapter(
                    this@MovieDetailActivity,
                    android.R.layout.simple_spinner_item,
                    movieSchedule.getScheduleTimes(scheduleDate[position])
                )
                if (needSpinnerInitialize && savedInstanceState != null) {
                    binding.spMovieTime.setSelection(
                        (
                            savedInstanceState.getString(TIME_KEY)
                                ?: movieSchedule.getScheduleTimes(
                                    binding.spMovieDate.selectedItem.toString()
                                ).first()
                            ).toInt()
                    )
                    needSpinnerInitialize = false
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) = Unit
        }
    }

    private fun setSpinnerAdapter(scheduleDate: List<String>, movieSchedule: MovieSchedule) {
        binding.spMovieDate.adapter = ArrayAdapter(
            this,
            android.R.layout.simple_spinner_item,
            scheduleDate
        )
        binding.spMovieTime.adapter = ArrayAdapter(
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
        binding.tvTicketCount.text = count.toString()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString(TIME_KEY, binding.spMovieTime.selectedItemPosition.toString())
        outState.putInt(DATE_KEY, binding.spMovieDate.selectedItemPosition)
        outState.putString(TICKET_COUNT_KEY, binding.tvTicketCount.text.toString())
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    companion object {
        private const val TICKET_COUNT_KEY = "ticketCount"
        private const val DATE_KEY = "date"
        private const val TIME_KEY = "time"

        fun intent(context: Context) = Intent(context, MovieDetailActivity::class.java)
    }
}
