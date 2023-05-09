package woowacourse.movie.presentation.booking

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import woowacourse.movie.R
import woowacourse.movie.data.movie.MockMovieData
import woowacourse.movie.databinding.ActivityBookingBinding
import woowacourse.movie.presentation.choiceSeat.ChoiceSeatActivity
import woowacourse.movie.presentation.model.CinemaModel
import woowacourse.movie.presentation.model.MovieModel
import woowacourse.movie.presentation.model.ReservationModel
import woowacourse.movie.presentation.util.formatDotDate
import woowacourse.movie.presentation.util.getParcelableExtraCompat
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime

class BookingActivity : AppCompatActivity(), BookingContract.View {

    override val presenter: BookingContract.Presenter by lazy {
        BookingPresenter(
            MockMovieData,
            this,
        )
    }

    private lateinit var binding: ActivityBookingBinding

    private val cinemaModel: CinemaModel by lazy {
        intent.getParcelableExtraCompat(CINEMA_MODEL) ?: throw NoSuchElementException()
    }

    private val movieModel: MovieModel by lazy { presenter.requireMovieModel(cinemaModel.movieId) }

    private val dateSpinnerAdapter by lazy {
        SpinnerAdapter<LocalDate>(this, R.layout.screening_date_time_item, R.id.textSpinnerDateTime)
    }
    private val timeSpinnerAdapter by lazy {
        SpinnerAdapter<LocalTime>(this, R.layout.screening_date_time_item, R.id.textSpinnerDateTime)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBookingBinding.inflate(layoutInflater)
        setContentView(binding.root)
        this.supportFragmentManager
        initAdapters()
        initView()
        restoreData(savedInstanceState)
        initDateTimes()
        gatherClickListeners()
        val aa = (1..2).toList()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finish()
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    private fun restoreData(savedInstanceState: Bundle?) {
        if (savedInstanceState == null) {
            presenter.setTicketCount(INITIAL_TICKET_COUNT)
            return
        }

        with(savedInstanceState) {
            presenter.setTicketCount(getInt(TICKET_COUNT))
            binding.spinnerScreeningDate.setSelection(getInt(DATE_POSITION), false)
            binding.spinnerScreeningTime.setSelection(getInt(TIME_POSITION), false)
        }
    }

    override fun setTicketCount(count: Int) {
        binding.textBookingTicketCount.text = count.toString()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.run {
            putInt(TICKET_COUNT, binding.textBookingTicketCount.text.toString().toInt())
            putInt(DATE_POSITION, binding.spinnerScreeningDate.selectedItemPosition)
            putInt(TIME_POSITION, binding.spinnerScreeningTime.selectedItemPosition)
        }
        super.onSaveInstanceState(outState)
    }

    private fun gatherClickListeners() {
        clickMinus()
        clickPlus()
        clickBookingComplete()
    }

    private fun initView() {
        setMoviePoster()
        setMovieTitle()
        setMovieScreeningDate()
        setMovieRunningTime()
        setMovieDescription()
    }

    private fun setMoviePoster() {
        binding.imageBookingPoster.setImageResource(movieModel.poster)
    }

    private fun setMovieTitle() {
        binding.textBookingTitle.text = movieModel.title
    }

    private fun setMovieScreeningDate() {
        binding.textBookingScreeningDate.text =
            getString(R.string.screening_date).format(
                movieModel.screeningStartDate.formatDotDate(),
                movieModel.screeningEndDate.formatDotDate(),
            )
    }

    private fun setMovieRunningTime() {
        binding.textBookingRunningTime.text =
            getString(R.string.running_time).format(movieModel.runningTime)
    }

    private fun setMovieDescription() {
        binding.textBookingDescription.text = movieModel.description
    }

    private fun clickMinus() {
        binding.buttonBookingMinus.setOnClickListener {
            presenter.subTicket()
        }
    }

    private fun clickPlus() {
        binding.buttonBookingPlus.setOnClickListener {
            presenter.addTicket()
        }
    }

    private fun clickBookingComplete() {
        binding.buttonBookingChooseSeat.setOnClickListener {
            bookMovie()
        }
    }

    private fun bookMovie() {
        val dateTime = LocalDateTime.of(
            dateSpinnerAdapter.getItem(binding.spinnerScreeningDate.selectedItemPosition),
            timeSpinnerAdapter.getItem(binding.spinnerScreeningTime.selectedItemPosition),
        )
        presenter.reserveMovie(cinemaModel, dateTime)
    }

    override fun reservationMovie(reservationModel: ReservationModel) {
        startActivity(ChoiceSeatActivity.getIntent(this, reservationModel))
    }

    private fun initAdapters() {
        binding.spinnerScreeningDate.adapter = dateSpinnerAdapter
        binding.spinnerScreeningTime.adapter = timeSpinnerAdapter
    }

    private fun initDateTimes() {
        dateSpinnerAdapter.initItems(presenter.getScreeningDate(movieModel.id))
        timeSpinnerAdapter.initItems(cinemaModel.movieTimes)
    }

    companion object {
        private const val CINEMA_MODEL = "CINEMA_MODEL"
        private const val TICKET_COUNT = "TICKET_COUNT"
        private const val DATE_POSITION = "DATE_POSITION"
        private const val TIME_POSITION = "TIME_POSITION"
        private const val INITIAL_TICKET_COUNT = 1
        fun getIntent(context: Context, cinemaModel: CinemaModel): Intent {
            return Intent(context, BookingActivity::class.java).apply {
                putExtra(CINEMA_MODEL, cinemaModel)
            }
        }
    }
}
