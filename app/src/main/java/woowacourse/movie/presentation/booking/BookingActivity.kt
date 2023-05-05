package woowacourse.movie.presentation.booking

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import androidx.appcompat.app.AppCompatActivity
import woowacourse.movie.R
import woowacourse.movie.databinding.ActivityBookingBinding
import woowacourse.movie.presentation.choiceSeat.ChoiceSeatActivity
import woowacourse.movie.presentation.model.MovieModel
import woowacourse.movie.presentation.util.formatDotDate
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime

class BookingActivity : AppCompatActivity(), BookingContract.View {

    private lateinit var binding: ActivityBookingBinding

    override val movieId: Long by lazy { intent.getLongExtra(MOVIE_ID, -1) }

    override val presenter: BookingContract.Presenter by lazy { BookingPresenter(this) }

    private val movieModel: MovieModel by lazy { presenter.requireMovieModel(movieId) }

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

        initAdapters()
        initView()
        restoreData(savedInstanceState)
        initDateTimes()
        gatherClickListeners()
        initDateSpinnerSelectedListener()
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
        val reservation = presenter.reserveMovie(dateTime)
        startActivity(ChoiceSeatActivity.getIntent(this, reservation))
    }

    private fun initAdapters() {
        binding.spinnerScreeningDate.adapter = dateSpinnerAdapter
        binding.spinnerScreeningTime.adapter = timeSpinnerAdapter
    }

    private fun initDateTimes() {
        dateSpinnerAdapter.initItems(presenter.getScreeningDate())
        timeSpinnerAdapter.initItems(presenter.getScreeningTime(INITIAL_DATE_SPINNER_POSITION))
    }

    private fun initDateSpinnerSelectedListener() {
        binding.spinnerScreeningDate.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long,
                ) {
                    convertTimeItems(position)
                }

                override fun onNothingSelected(parent: AdapterView<*>?) = Unit
            }
    }

    override fun convertTimeItems(position: Int) {
        val times: List<LocalTime> = presenter.getScreeningTime(position)
        timeSpinnerAdapter.initItems(times)
    }

    companion object {
        private const val MOVIE_ID = "MOVIE_ID"
        private const val TICKET_COUNT = "TICKET_COUNT"
        private const val DATE_POSITION = "DATE_POSITION"
        private const val TIME_POSITION = "TIME_POSITION"
        private const val INITIAL_DATE_SPINNER_POSITION = 0
        private const val INITIAL_TICKET_COUNT = 1
        fun getIntent(context: Context, movieId: Long): Intent {
            return Intent(context, BookingActivity::class.java).apply {
                putExtra(MOVIE_ID, movieId)
            }
        }
    }
}
