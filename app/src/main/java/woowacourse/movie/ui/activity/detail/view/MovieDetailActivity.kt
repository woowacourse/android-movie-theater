package woowacourse.movie.ui.activity.detail.view

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import woowacourse.movie.R
import woowacourse.movie.databinding.ActivityMovieDetailBinding
import woowacourse.movie.ui.activity.detail.MovieDetailContract
import woowacourse.movie.ui.activity.detail.presenter.MovieDetailPresenter
import woowacourse.movie.ui.activity.seatpicker.view.SeatPickerActivity
import woowacourse.movie.ui.model.MovieModel
import woowacourse.movie.ui.model.MovieTicketModel
import woowacourse.movie.ui.model.PeopleCountModel
import woowacourse.movie.ui.model.PriceModel
import woowacourse.movie.ui.model.TheaterModel
import woowacourse.movie.ui.model.TicketTimeModel
import woowacourse.movie.ui.utils.getParcelable
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.format.DateTimeFormatter

class MovieDetailActivity : AppCompatActivity(), MovieDetailContract.View {
    private lateinit var binding: ActivityMovieDetailBinding
    override lateinit var presenter: MovieDetailContract.Presenter
    private var theaterName = ""
    private lateinit var timeSpinnerAdapter: ArrayAdapter<LocalTime>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        presenter = MovieDetailPresenter(this)
        setUpBinding()
        setContentView(binding.root)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        intent.getParcelable<MovieModel>(MOVIE_EXTRA_KEY)?.let {
            setMovieInfo(it)
            setDateSpinner(it)
            setBookingButton(it)
        }
        intent.getParcelable<TheaterModel>(THEATER_EXTRA_KEY)?.let {
            theaterName = it.name
            setTimeSpinner(it)
        }

        loadSavedData(savedInstanceState)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)

        outState.putInt(DATE_SPINNER_POSITION_INSTANCE_KEY, binding.detailDateSpinner.selectedItemPosition)
        outState.putInt(TIME_SPINNER_POSITION_INSTANCE_KEY, binding.detailTimeSpinner.selectedItemPosition)
        outState.putInt(PEOPLE_COUNT_VALUE_INSTANCE_KEY, binding.detailPeopleCount.text.toString().toInt())
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                finish()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun setPeopleCount(count: Int) {
        binding.detailPeopleCount.text = count.toString()
    }

    private fun setUpBinding() {
        binding = ActivityMovieDetailBinding.inflate(layoutInflater)
        binding.presenter = presenter
    }

    private fun setMovieInfo(movie: MovieModel) {
        with(binding) {
            detailPoster.setImageResource(movie.poster)
            detailTitle.text = movie.title
            detailDate.text = movie.getScreenDate()
            detailRunningTime.text = movie.getRunningTime()
            detailDescription.text = movie.description
        }
    }

    private fun MovieModel.getScreenDate(): String =
        getString(R.string.screen_date, startDate.format(), endDate.format())

    private fun LocalDate.format(): String =
        format(DateTimeFormatter.ofPattern(getString(R.string.date_format)))

    private fun MovieModel.getRunningTime(): String = getString(R.string.running_time, runningTime)

    private fun setDateSpinner(movie: MovieModel) {
        val dateSpinnerAdapter = ArrayAdapter(
            this,
            android.R.layout.simple_spinner_item,
            movie.getDatesBetweenTwoDates()
        )
        dateSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.detailDateSpinner.adapter = dateSpinnerAdapter
        binding.detailDateSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                binding.detailTimeSpinner.setSelection(0)
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
            }
        }
    }

    private fun setBookingButton(movie: MovieModel) {
        binding.detailBookingButton.setOnClickListener {
            moveToSeatPickerActivity(movie)
        }
    }

    private fun setTimeSpinner(theater: TheaterModel) {
        timeSpinnerAdapter = ArrayAdapter(
            this,
            android.R.layout.simple_spinner_item,
            theater.times
        )
        timeSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.detailTimeSpinner.adapter = timeSpinnerAdapter
    }

    private fun moveToSeatPickerActivity(movie: MovieModel) {
        val ticket = MovieTicketModel(
            movie.title,
            TicketTimeModel(
                LocalDateTime.of(
                    binding.detailDateSpinner.selectedItem as LocalDate,
                    binding.detailTimeSpinner.selectedItem as LocalTime
                )
            ),
            PeopleCountModel(binding.detailPeopleCount.text.toString().toInt()),
            seats = emptySet(),
            PriceModel(0),
            theaterName
        )

        val intent = SeatPickerActivity.createIntent(this, ticket)
        startActivity(intent)
    }

    private fun loadSavedData(savedInstanceState: Bundle?) {
        val datePosition = savedInstanceState?.getInt(DATE_SPINNER_POSITION_INSTANCE_KEY) ?: 0
        val timePosition = savedInstanceState?.getInt(TIME_SPINNER_POSITION_INSTANCE_KEY) ?: 0
        val count = savedInstanceState?.getInt(PEOPLE_COUNT_VALUE_INSTANCE_KEY) ?: 1
        binding.detailDateSpinner.setSelection(datePosition)
        binding.detailTimeSpinner.setSelection(timePosition)
        presenter.setPeopleCount(count)
    }

    companion object {
        private const val DATE_SPINNER_POSITION_INSTANCE_KEY = "date_position"
        private const val MOVIE_EXTRA_KEY = "movie"
        private const val THEATER_EXTRA_KEY = "theater"
        private const val PEOPLE_COUNT_VALUE_INSTANCE_KEY = "count"
        private const val TIME_SPINNER_POSITION_INSTANCE_KEY = "time_position"

        fun createIntent(context: Context, movie: MovieModel, theater: TheaterModel): Intent {
            val intent = Intent(context, MovieDetailActivity::class.java)
            intent.putExtra(MOVIE_EXTRA_KEY, movie)
            intent.putExtra(THEATER_EXTRA_KEY, theater)
            return intent
        }
    }
}
