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
import com.woowacourse.domain.movie.MovieBookingInfo
import com.woowacourse.domain.movie.MovieSchedule
import woowacourse.movie.DateFormatter
import woowacourse.movie.R
import woowacourse.movie.activity.BackButtonActivity
import woowacourse.movie.activity.seatPicker.SeatPickerActivity
import woowacourse.movie.databinding.ActivityMovieDetailBinding
import woowacourse.movie.getSerializableCompat
import woowacourse.movie.model.MovieUIModel
import woowacourse.movie.model.ScreeningScheduleUIModel
import woowacourse.movie.model.TheaterMovieUIModel
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_movie_detail)
        needSpinnerInitialize = true

        setCountText(1)

        val movieData = getMovieData()
        finishIfDummyData(movieData)

        presenter = MovieDetailPresenter(this, MovieSchedule(movieData.movieInfo.toDomain()))
        presenter.loadMovieData(movieData.movieInfo.movie.toDomain())
        setClickListener(movieData)

        presenter.loadScheduleDate(savedInstanceState)
        reloadTicketCountInstance(savedInstanceState)
    }

    private fun getMovieData() = (
        intent.getSerializableCompat(MOVIE_DATA_KEY)
            ?: TheaterMovieUIModel(
                "",
                ScreeningScheduleUIModel(MovieUIModel.dummyData, emptyList()),
            )
        )

    private fun finishIfDummyData(movieData: TheaterMovieUIModel) {
        if (movieData.movieInfo.movie == MovieUIModel.dummyData) {
            Toast.makeText(this, getString(R.string.cant_get_movie_data), Toast.LENGTH_SHORT).show()
            this.finish()
        }
    }

    private fun setClickListener(movieData: TheaterMovieUIModel) {
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

    private fun clickSeatPickerBtn(movieData: TheaterMovieUIModel) {
        binding.btToSeatPicker.setOnClickListener {
            presenter.loadMovieBookingInfo(
                movieData.toDomain(),
                LocalDate.parse(dateSpinner.selectedItem.toString()),
                timeSpinner.selectedItem.toString(),
            )
        }
    }

    override fun setUpSpinner(movieSchedule: MovieSchedule, savedInstanceState: Bundle?) {
        setSpinnerSelectedListener(movieSchedule, savedInstanceState)
        setSpinnerAdapter(movieSchedule.getScheduleDates(), movieSchedule)
    }

    private fun setSpinnerSelectedListener(
        movieSchedule: MovieSchedule,
        savedInstanceState: Bundle?,
    ) {
        dateSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                adapterView: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long,
            ) {
                timeSpinner.adapter = ArrayAdapter(
                    this@MovieDetailActivity,
                    android.R.layout.simple_spinner_item,
                    movieSchedule.getScheduleTimes(),
                )
                if (needSpinnerInitialize && savedInstanceState != null) {
                    timeSpinner.setSelection(
                        (
                            savedInstanceState.getString(TIME_KEY)
                                ?: movieSchedule.getScheduleTimes()
                                    .first()
                            ).toInt(),
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
            scheduleDate,
        )
        timeSpinner.adapter = ArrayAdapter(
            this,
            android.R.layout.simple_spinner_item,
            movieSchedule.getScheduleTimes(),
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

    override fun setUpMovieDetailView(movieData: MovieUIModel) {
        binding.ivMoviePoster.setImageResource(movieData.poster)
        binding.tvMovieTitle.text = movieData.title
        binding.tvMovieScreeningPeriod.text =
            getString(
                R.string.movie_screening_period,
                DateFormatter.format(movieData.startDate),
                DateFormatter.format(movieData.endDate),
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

        fun getIntent(context: Context, theaterMovie: TheaterMovieUIModel): Intent {
            val intent = Intent(context, MovieDetailActivity::class.java)
            intent.putExtra(MOVIE_DATA_KEY, theaterMovie)
            return intent
        }
    }
}
