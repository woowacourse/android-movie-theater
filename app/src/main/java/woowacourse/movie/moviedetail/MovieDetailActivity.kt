package woowacourse.movie.moviedetail

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import domain.movieinfo.MovieDate
import domain.movieinfo.MovieTime
import woowacourse.movie.R
import woowacourse.movie.databinding.ActivityMovieDetailBinding
import woowacourse.movie.dto.movie.MovieDto
import woowacourse.movie.dto.movie.SeatMovieDto
import woowacourse.movie.dto.theater.MovieTheaterDto
import woowacourse.movie.mapper.movie.mapToMovieDateDto
import woowacourse.movie.mapper.movie.mapToMovieTimeDto
import woowacourse.movie.mapper.ticket.mapToTicketCountDto
import woowacourse.movie.seat.SeatSelectionActivity
import woowacourse.movie.theater.TheaterSheetFragment.Companion.THEATER_KEY
import woowacourse.movie.utils.getParcelableCompat
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class MovieDetailActivity : AppCompatActivity(), MovieDetailContract.View {
    private lateinit var binding: ActivityMovieDetailBinding
    private lateinit var presenter: MovieDetailContract.Presenter

    private var dateSpinnerPosition = 0
    private var timeSpinnerPosition = 0

    private val selectDateSpinner by lazy { binding.selectDate }
    private val selectTimeSpinner by lazy { binding.selectTime }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMovieDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        presenter = MovieDetailPresenter(this)

        setToolBar()
        setUpState(savedInstanceState)
        initDetailData()
        presenter.updateMovieInfo()
        presenter.setSpinner()
        onClickPlusBtnListener()
        onClickSubBtnListener()
        presenter.setBookingButton()
    }

    private fun setToolBar() {
        setSupportActionBar(binding.movieDetailToolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    private fun setUpState(savedInstanceState: Bundle?) {
        if (savedInstanceState != null) {
            dateSpinnerPosition = savedInstanceState.getInt(DATE_SPINNER_POSITION)
            timeSpinnerPosition = savedInstanceState.getInt(TIME_SPINNER_POSITION)
        }
    }

    private fun initDetailData() {
        val movie = intent.getParcelableCompat<MovieDto>(MOVIE_KEY)
        val theater = intent.getParcelableCompat<MovieTheaterDto>(THEATER_KEY)
        movie?.let { theater?.let { presenter.getData(movie, theater) } }
    }

    override fun showMovieInfo(movieDto: MovieDto) {
        binding.movieTitle.text = movieDto.title
        binding.movieDescription.text = movieDto.description
        binding.moviePoster.setImageResource(movieDto.moviePoster)
        binding.movieDate.text = formatDate(movieDto.startDate, movieDto.endDate)
        binding.movieTime.text = formatRunningTime(movieDto.runningTime)
    }

    override fun formatDate(startDate: LocalDate, endDate: LocalDate): String {
        val start = startDate.format(DateTimeFormatter.ofPattern(getString(R.string.date_format)))
        val end = endDate.format(DateTimeFormatter.ofPattern(getString(R.string.date_format)))
        return getString(R.string.movie_running_date).format(start, end)
    }

    override fun formatRunningTime(runningTime: Int): String {
        return getString(R.string.movie_running_time).format(runningTime)
    }

    override fun showTicketCount(numberOfPeople: Int) {
        binding.bookerNum.text = numberOfPeople.toString()
    }

    private fun onClickPlusBtnListener() {
        binding.plusButton.setOnClickListener {
            presenter.plusTicketCount()
        }
    }

    private fun onClickSubBtnListener() {
        binding.minusButton.setOnClickListener {
            presenter.subTicketCount()
        }
    }

    override fun onClickBookBtnListener(movie: MovieDto, theater: MovieTheaterDto) {
        binding.bookButton.setOnClickListener {
            val selectedDate = MovieDate.of(selectDateSpinner.selectedItem.toString())
            val selectedTime = MovieTime.of(selectTimeSpinner.selectedItem.toString())
            val intent = Intent(this, SeatSelectionActivity::class.java)

            intent.putExtra(
                SEAT_BASE_INFORMATION_KEY,
                SeatMovieDto(
                    presenter.movieTicket.mapToTicketCountDto(),
                    movie,
                    selectedDate.mapToMovieDateDto(),
                    selectedTime.mapToMovieTimeDto(),
                    theater.place
                )
            )
            startActivity(intent)
            finish()
        }
    }

    override fun setDateSpinner(intervalDate: List<String>) {
        selectDateSpinner.adapter = getDateSpinnerAdapter(intervalDate)
        selectDateSpinner.setSelection(dateSpinnerPosition)

        selectDateSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long,
            ) {
                setTimeSpinner()
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {
            }
        }
    }

    override fun setTimeSpinner() {
        selectTimeSpinner.adapter = getTimeSpinnerAdapter()
        selectTimeSpinner.setSelection(timeSpinnerPosition)

        selectTimeSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long,
            ) {
                timeSpinnerPosition = position
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {
            }
        }
    }

    private fun getDateSpinnerAdapter(intervalDate: List<String>): ArrayAdapter<String> {
        val dateAdapter = ArrayAdapter(
            this,
            android.R.layout.simple_spinner_item,
            intervalDate,
        )
        dateAdapter.setDropDownViewResource(androidx.appcompat.R.layout.support_simple_spinner_dropdown_item)

        return dateAdapter
    }

    private fun getTimeSpinnerAdapter(): ArrayAdapter<String> {
        val timeAdapter = ArrayAdapter(
            this,
            android.R.layout.simple_spinner_item,
            presenter.getTimes(),
        )

        timeAdapter.setDropDownViewResource(androidx.appcompat.R.layout.support_simple_spinner_dropdown_item)

        return timeAdapter
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putInt(DATE_SPINNER_POSITION, dateSpinnerPosition)
        outState.putInt(TIME_SPINNER_POSITION, timeSpinnerPosition)
        super.onSaveInstanceState(outState)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                finish()
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }

    companion object {
        const val SEAT_BASE_INFORMATION_KEY = "seat_base_info"
        const val MOVIE_KEY = "movie"
        private const val DATE_SPINNER_POSITION = "date_spinner_position"
        private const val TIME_SPINNER_POSITION = "time_spinner_position"
    }
}
