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
import woowacourse.movie.presentation.util.getParcelableExtraCompat
import woowacourse.movie.presentation.util.noIntentExceptionHandler
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime

class BookingActivity : AppCompatActivity(), BookingContract.View {

    override val presenter: BookingContract.Presenter by lazy {
        BookingPresenter(MockMovieData, this)
    }

    private lateinit var binding: ActivityBookingBinding

    private lateinit var cinemaModel: CinemaModel

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
        initCinemaModel()
        initAdapters()
        initMovieView()
        restoreData(savedInstanceState)
        initDateTimes()
        gatherClickListeners()
    }

    private fun initMovieView() {
        presenter.setMovieInfo(cinemaModel.movieId)
    }

    override fun setMovieInfo(movieModel: MovieModel) {
        binding.movieModel = movieModel
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finish()
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    private fun initCinemaModel() {
        cinemaModel = intent.getParcelableExtraCompat(CINEMA_MODEL)
            ?: return this.noIntentExceptionHandler(NO_CINEMA_INFO_ERROR)
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
        presenter.initMovieDates(cinemaModel.movieId)
        timeSpinnerAdapter.initItems(cinemaModel.movieTimes)
    }

    override fun setScreeningDates(localDates: List<LocalDate>) {
        dateSpinnerAdapter.initItems(localDates)
    }

    companion object {
        private const val CINEMA_MODEL = "CINEMA_MODEL"
        private const val TICKET_COUNT = "TICKET_COUNT"
        private const val DATE_POSITION = "DATE_POSITION"
        private const val TIME_POSITION = "TIME_POSITION"
        private const val INITIAL_TICKET_COUNT = 1
        private const val NO_CINEMA_INFO_ERROR = "극장 정보가 없습니다."
        fun getIntent(context: Context, cinemaModel: CinemaModel): Intent {
            return Intent(context, BookingActivity::class.java).apply {
                putExtra(CINEMA_MODEL, cinemaModel)
            }
        }
    }
}
