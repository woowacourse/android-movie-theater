package woowacourse.movie.movie.activity

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import domain.DayOfWeek
import domain.movieinfo.MovieDate
import domain.movieinfo.MovieTime
import domain.screeningschedule.ReservationDate
import domain.screeningschedule.ReservationTime
import woowacourse.movie.R
import woowacourse.movie.databinding.ActivityMovieDetailBinding
import woowacourse.movie.movie.dto.movie.MovieDateDto
import woowacourse.movie.movie.dto.movie.MovieDto
import woowacourse.movie.movie.dto.ticket.TicketCountDto
import woowacourse.movie.movie.mapper.movie.mapToMovieDateDto
import woowacourse.movie.movie.mapper.movie.mapToMovieTimeDto
import woowacourse.movie.movie.mapper.ticket.mapToTicketCount
import woowacourse.movie.movie.mapper.ticket.mapToTicketCountDto
import woowacourse.movie.movie.utils.getParcelableCompat
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class MovieDetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMovieDetailBinding
    private var movieTikcet = TicketCountDto()
    private var dateSpinnerPosition = 0
    private var timeSpinnerPosition = 0

    private val selectDateSpinner by lazy { binding.selectDate }
    private val selectTimeSpinner by lazy { binding.selectTime }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMovieDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setToolBar()
        setUpState(savedInstanceState)

        val movie = intent.getParcelableCompat<MovieDto>(MOVIE_KEY)!!

        setDateSpinner(movie.startDate, movie.endDate)
        setUpMovieData(movie)
        setNumberOfPeople()
        onClickBookBtnListener(movie)
    }

    private fun setUpState(savedInstanceState: Bundle?) {
        if (savedInstanceState != null) {
            dateSpinnerPosition = savedInstanceState.getInt(DATE_SPINNER_POSITION)
            timeSpinnerPosition = savedInstanceState.getInt(TIME_SPINNER_POSITION)
        }
    }

    private fun setToolBar() {
        setSupportActionBar(binding.movieDetailToolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    private fun setUpMovieData(movie: MovieDto) {
        binding.moviePoster.setImageResource(movie.moviePoster)
        binding.movieTitle.text = movie.title
        binding.movieDate.text = formatMovieRunningDate(movie)
        binding.movieTime.text = getString(R.string.movie_running_time).format(movie.runningTime)
        binding.movieDescription.text = movie.description
    }

    private fun formatMovieRunningDate(item: MovieDto): String {
        val startDate =
            item.startDate.format(DateTimeFormatter.ofPattern(getString(R.string.date_format)))
        val endDate =
            item.endDate.format(DateTimeFormatter.ofPattern(getString(R.string.date_format)))
        return getString(R.string.movie_running_date).format(startDate, endDate)
    }

    private fun setNumberOfPeople() {
        val booker = binding.bookerNum
        booker.text = movieTikcet.numberOfPeople.toString()
        onClickDecreaseBtnListener(booker)
        onClickIncreaseBtnListener(booker)
    }

    private fun onClickDecreaseBtnListener(booker: TextView) {
        binding.minusButton.setOnClickListener {
            val ticketDecrease = movieTikcet.mapToTicketCount().decrease()
            movieTikcet = ticketDecrease.mapToTicketCountDto()
            booker.text = movieTikcet.numberOfPeople.toString()
        }
    }

    private fun onClickIncreaseBtnListener(booker: TextView) {
        binding.plusButton.setOnClickListener {
            val ticketIncrease = movieTikcet.mapToTicketCount().increase()
            movieTikcet = ticketIncrease.mapToTicketCountDto()
            booker.text = movieTikcet.numberOfPeople.toString()
        }
    }

    private fun onClickBookBtnListener(movie: MovieDto) {
        binding.bookButton.setOnClickListener {
            val selectedDate = MovieDate.of(selectDateSpinner.selectedItem.toString())
            val selectedTime = MovieTime.of(selectTimeSpinner.selectedItem.toString())
            val intent = Intent(this, SeatSelectionActivity::class.java)
            intent.putExtra(TICKET_KEY, movieTikcet)
            intent.putExtra(MOVIE_KEY, movie)
            intent.putExtra(DATE_KEY, selectedDate.mapToMovieDateDto())
            intent.putExtra(TIME_KEY, selectedTime.mapToMovieTimeDto())
            startActivity(intent)
            finish()
        }
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

    private fun setDateSpinner(startDate: LocalDate, endDate: LocalDate) {
        selectDateSpinner.adapter = getDateSpinnerAdapter(startDate, endDate)
        selectDateSpinner.setSelection(dateSpinnerPosition)

        selectDateSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long,
            ) {
                setTimeSpinner(
                    MovieDate.of(selectDateSpinner.getItemAtPosition(position) as String)
                        .mapToMovieDateDto(),
                )
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
            }
        }
    }

    private fun setTimeSpinner(selectedDay: MovieDateDto) {
        selectTimeSpinner.adapter = getTimeSpinnerAdapter(selectedDay)
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

    private fun getDateSpinnerAdapter(
        startDate: LocalDate,
        endDate: LocalDate,
    ): ArrayAdapter<String> {
        val dateAdapter = ArrayAdapter(
            this,
            android.R.layout.simple_spinner_item,
            ReservationDate(startDate, endDate).getIntervalDays(),
        )
        dateAdapter.setDropDownViewResource(androidx.appcompat.R.layout.support_simple_spinner_dropdown_item)

        return dateAdapter
    }

    private fun getTimeSpinnerAdapter(selectedDay: MovieDateDto): ArrayAdapter<String> {
        val timeAdapter = ArrayAdapter(
            this,
            android.R.layout.simple_spinner_item,
            ReservationTime(DayOfWeek.checkDayOfWeek(selectedDay.date)).getIntervalTimes(),
        )

        timeAdapter.setDropDownViewResource(androidx.appcompat.R.layout.support_simple_spinner_dropdown_item)

        return timeAdapter
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putInt(DATE_SPINNER_POSITION, dateSpinnerPosition)
        outState.putInt(TIME_SPINNER_POSITION, timeSpinnerPosition)
        super.onSaveInstanceState(outState)
    }

    companion object {
        private const val TICKET_KEY = "ticket"
        private const val MOVIE_KEY = "movie"
        private const val DATE_KEY = "movie_date"
        private const val TIME_KEY = "movie_time"
        private const val DATE_SPINNER_POSITION = "date_spinner_position"
        private const val TIME_SPINNER_POSITION = "time_spinner_position"
    }
}
