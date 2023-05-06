package woowacourse.movie.activity.moviedetail

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.woowacourse.domain.movie.Movie
import com.woowacourse.domain.movie.MovieBookingInfo
import com.woowacourse.domain.movie.MovieSchedule
import woowacourse.movie.DateFormatter
import woowacourse.movie.R
import woowacourse.movie.activity.BackButtonActivity
import woowacourse.movie.activity.seatPicker.SeatPickerActivity
import woowacourse.movie.databinding.ActivityMovieDetailBinding
import woowacourse.movie.getSerializableCompat
import woowacourse.movie.model.MovieUIModel
import woowacourse.movie.model.toDomain
import woowacourse.movie.model.toPresentation
import java.time.LocalDate

class MovieDetailActivity : BackButtonActivity(), MovieDetailContract.View {

    override lateinit var presenter: MovieDetailContract.Presenter
    private lateinit var binding: ActivityMovieDetailBinding
    private var needSpinnerInitialize = true
    private val dateSpinner: Spinner by lazy { binding.spMovieDate }
    private val timeSpinner: Spinner by lazy { binding.spMovieTime }
    private val personCountTextView by lazy { binding.tvTicketCount }
    lateinit var movieSchedule: MovieSchedule

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        presenter = MovieDetailPresenter(this)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_movie_detail)
        needSpinnerInitialize = true

        setCountText(1)

        val movieData = getMovieData()
        finishIfDummyData(movieData)

        presenter.getScheduleDate(movieData.startDate, movieData.endDate)
        presenter.initView(movieData.toDomain())
        setClickListener(movieData)

        val scheduleDate = movieSchedule.getScheduleDates()
        setSpinnerSelectedListener(movieSchedule, scheduleDate, savedInstanceState)
        setSpinnerAdapter(scheduleDate, movieSchedule)
        reloadTicketCountInstance(savedInstanceState)
    }

    private fun getMovieData() = (
        intent.getSerializableCompat(MOVIE_DATA_KEY)
            ?: MovieUIModel.dummyData
        )

    private fun finishIfDummyData(movieData: MovieUIModel) {
        if (movieData == MovieUIModel.dummyData) {
            Toast.makeText(this, getString(R.string.cant_get_movie_data), Toast.LENGTH_SHORT).show()
            this.finish()
        }
    }

    private fun setClickListener(movieData: MovieUIModel) {
        clickPlusBtn()
        clickMinusBtn()
        clickSeatPickerBtn(movieData)
    }

    private fun clickPlusBtn() {
        binding.btTicketCountPlus.setOnClickListener {
            presenter.addPeople()
        }
    }

    private fun clickMinusBtn() {
        binding.btTicketCountMinus.setOnClickListener {
            presenter.subPeople()
        }
    }

    private fun clickSeatPickerBtn(movieData: MovieUIModel) {
        binding.btToSeatPicker.setOnClickListener {
            presenter.getMovieBookingInfo(
                movieData.toDomain(), LocalDate.parse(dateSpinner.selectedItem.toString()),
                timeSpinner.selectedItem.toString()
            )
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
                if (needSpinnerInitialize && savedInstanceState != null) {
                    timeSpinner.setSelection(
                        (
                            savedInstanceState.getString(TIME_KEY)
                                ?: movieSchedule.getScheduleTimes(dateSpinner.selectedItem.toString())
                                    .first()
                            ).toInt()
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
            personCountTextView.text = savedInstanceState.getString(TICKET_COUNT_KEY)
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString(TIME_KEY, timeSpinner.selectedItemPosition.toString())
        outState.putInt(DATE_KEY, dateSpinner.selectedItemPosition)
        outState.putString(TICKET_COUNT_KEY, personCountTextView.text.toString())
    }

    override fun setScheduleDate(schedule: MovieSchedule) {
        movieSchedule = schedule
    }

    override fun initView(movieData: Movie) {
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

    override fun setCountText(count: Int) {
        personCountTextView.text = count.toString()
    }

    override fun showGuideMessage(msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
    }

    override fun setIntent(movieBookingInfo: MovieBookingInfo) {
        val intent = SeatPickerActivity.getIntent(this, movieBookingInfo.toPresentation())
        startActivity(intent)
        finish()
    }

    companion object {
        private const val MOVIE_DATA_KEY = "movieData"
        private const val TICKET_COUNT_KEY = "ticketCount"
        private const val DATE_KEY = "date"
        private const val TIME_KEY = "time"

        fun getIntent(context: Context, movie: MovieUIModel): Intent {
            val intent = Intent(context, MovieDetailActivity::class.java)
            intent.putExtra(MOVIE_DATA_KEY, movie)
            return intent
        }
    }
}
