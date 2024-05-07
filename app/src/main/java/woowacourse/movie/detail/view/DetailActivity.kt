package woowacourse.movie.detail.view

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import woowacourse.movie.R
import woowacourse.movie.databinding.ActivityDetailBinding
import woowacourse.movie.detail.contract.DetailContract
import woowacourse.movie.detail.model.Count
import woowacourse.movie.detail.presenter.DetailPresenter
import woowacourse.movie.list.model.Movie
import woowacourse.movie.list.view.HomeFragment.Companion.EXTRA_MOVIE_ID_KEY
import woowacourse.movie.seats.view.SeatsActivity
import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter

class DetailActivity : AppCompatActivity(), DetailContract.View {
    private lateinit var binding: ActivityDetailBinding
    private var toast: Toast? = null
    lateinit var selectedDate: LocalDate
    lateinit var selectedTime: LocalTime
    override val presenter = DetailPresenter(this@DetailActivity)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_detail)
        binding.detail = this
        val movieId = intent.getLongExtra(EXTRA_MOVIE_ID_KEY, 0)
        val theaterId = intent.getLongExtra(EXTRA_THEATER_ID_KEY, 0)
        initializePresenterTasks(movieId, theaterId)
        setOnPlusButtonClickListener()
        setOnMinusButtonClickListener()
        setOnTicketingButtonListener()
    }

    private fun initializePresenterTasks(movieId: Long, theaterId: Long) {
        presenter.setCurrentResultTicketCountInfo()
        presenter.storeMovieId(movieId)
        presenter.storeTheaterId(theaterId)
        presenter.setMovieInfo()
        presenter.setSpinnerInfo(theaterId)
        presenter.setSpinnerDateItemInfo()
        presenter.setSpinnerTimeItemInfo()
    }
    
    override fun setSpinners(
        screeningDates: List<LocalDate>,
        screeningTimes: List<LocalTime>,
    ) {
        val dateAdapter =
            ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, screeningDates)
        dateAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        val timeAdapter =
            ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, screeningTimes)
        timeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spinnerDate.adapter = dateAdapter
        binding.spinnerTime.adapter = timeAdapter
    }

    override fun setOnSpinnerDateItemSelectedListener(screeningDates: List<LocalDate>) {
        binding.spinnerDate.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long,
                ) {
                    selectedDate = screeningDates[position]
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {
                    selectedDate = screeningDates[0]
                }
            }
    }

    override fun setOnSpinnerTimeItemSelectedListener(screeningTimes: List<LocalTime>) {
        binding.spinnerTime.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long,
                ) {
                    selectedTime = screeningTimes[position]
                    presenter.storeSelectedTime(selectedTime)
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {
                    selectedTime = screeningTimes[0]
                }
            }
    }

    override fun setMovieView(info: Movie) {
        binding.movie = info
        binding.executePendingBindings()
    }

    override fun showToast(message: String) {
        toast?.cancel()
        toast = Toast.makeText(this, message, Toast.LENGTH_SHORT)
        toast?.show()
    }

    override fun showCurrentResultTicketCountView(info: Int) {
        binding.ticketCount.text = info.toString()
    }

    private fun setOnPlusButtonClickListener() {
        binding.plusButton.setOnClickListener {
            presenter.setPlusButtonClickInfo()
        }
    }

    private fun setOnMinusButtonClickListener() {
        binding.minusButton.setOnClickListener {
            presenter.setMinusButtonClickInfo()
        }
    }

    private fun setOnTicketingButtonListener() {
        binding.ticketingButton.setOnClickListener {
            presenter.setTicketingButtonClickInfo()
        }
    }

    override fun startMovieTicketActivity(
        count: Count,
        movieId: Long,
        theaterId: Long,
    ) {
        val intent = Intent(this, SeatsActivity::class.java)
        intent.putExtra(EXTRA_COUNT_KEY, count.number)
        intent.putExtra(EXTRA_MOVIE_ID_KEY, movieId)
        intent.putExtra(
            EXTRA_DATE_KEY,
            selectedDate.format(DateTimeFormatter.ofPattern(DATE_PATTERN)),
        )
        intent.putExtra(EXTRA_TIME_KEY, selectedTime.toString())
        intent.putExtra(EXTRA_THEATER_ID_KEY, theaterId)
        this.startActivity(intent)
    }

    companion object {
        const val EXTRA_COUNT_KEY = "count_key"
        const val EXTRA_DATE_KEY = "selected_date_key"
        const val EXTRA_TIME_KEY = "selected_time_key"
        const val EXTRA_THEATER_ID_KEY = "theater_id_key"
        private const val DATE_PATTERN = "yyyy.MM.dd"
    }
}
