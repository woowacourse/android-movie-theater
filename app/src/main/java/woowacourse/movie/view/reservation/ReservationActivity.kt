package woowacourse.movie.view.reservation

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.databinding.DataBindingUtil
import woowacourse.movie.R
import woowacourse.movie.databinding.ActivityReservationBinding
import woowacourse.movie.model.movie.Movie
import woowacourse.movie.model.movie.MovieToReserve
import woowacourse.movie.model.theater.TheaterMovieSchedule
import woowacourse.movie.presenter.reservation.ReservationContract
import woowacourse.movie.presenter.reservation.ReservationPresenter
import woowacourse.movie.view.extension.getSerializableExtraData
import woowacourse.movie.view.extension.showShortToast
import woowacourse.movie.view.seatSelection.SeatSelectionActivity
import java.time.LocalDate
import java.time.LocalTime

class ReservationActivity :
    AppCompatActivity(),
    ReservationContract.View {
    private val presenter: ReservationContract.Presenter = ReservationPresenter(this)
    private lateinit var timeSpinnerAdapter: TimeSpinnerAdapter
    private lateinit var binding: ActivityReservationBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = DataBindingUtil.setContentView(this, R.layout.activity_reservation)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        setupClickListener()
        setupTimeAdapter()
        updateMovieToPresenter()
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    private fun updateMovieToPresenter() {
        val intentMovieData: TheaterMovieSchedule =
            intent.getSerializableExtraData<TheaterMovieSchedule>(SCREENING_INFO_KEY)
                ?: run {
                    showShortToast("예상치 못한 오류로 영화 선택 화면으로 돌아갑니다.")
                    finish()
                    return
                }
        presenter.updateMovieData(intentMovieData)
    }

    private fun setupClickListener() {
        binding.btnReservationMinusTicketCount.setOnClickListener {
            presenter.decreaseTicketCount()
        }

        binding.btnReservationPlusTicketCount.setOnClickListener {
            presenter.increaseTicketCount()
        }

        binding.btnReservationSelectComplete.setOnClickListener {
            presenter.updateMovieToReserve()
        }
    }

    override fun setupDateAdapter(dates: List<LocalDate>) {
        val dateAdapter =
            ArrayAdapter(
                this,
                com.google.android.material.R.layout.support_simple_spinner_dropdown_item,
                dates,
            )
        binding.spinnerReservationDate.apply {
            adapter = dateAdapter
            onItemSelectedListener =
                object : AdapterView.OnItemSelectedListener {
                    override fun onItemSelected(
                        parent: AdapterView<*>?,
                        view: View?,
                        position: Int,
                        id: Long,
                    ) {
                        presenter.updateMovieDate(dates[position])
                    }

                    override fun onNothingSelected(parent: AdapterView<*>?) = Unit
                }
        }
    }

    private fun setupTimeAdapter() {
        timeSpinnerAdapter = TimeSpinnerAdapter(this, mutableListOf())
        binding.spinnerReservationTime.apply {
            adapter = timeSpinnerAdapter
            onItemSelectedListener =
                object : AdapterView.OnItemSelectedListener {
                    override fun onItemSelected(
                        parent: AdapterView<*>?,
                        view: View?,
                        position: Int,
                        id: Long,
                    ) {
                        val selectedTime: LocalTime = timeSpinnerAdapter.getItem(position) ?: return
                        presenter.updateMovieTime(selectedTime)
                    }

                    override fun onNothingSelected(parent: AdapterView<*>?) = Unit
                }
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt(
            TICKET_COUNT_DATA_KEY,
            binding.tvReservationTicketCount.text
                .toString()
                .toInt(),
        )
        outState.putInt(
            TICKET_DATE_POSITION_DATA_KEY,
            binding.spinnerReservationDate.selectedItemPosition,
        )
        outState.putInt(
            MOVIE_TIME_POSITION_DATA_KEY,
            binding.spinnerReservationTime.selectedItemPosition,
        )
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        val savedCount: Int = savedInstanceState.getInt(TICKET_COUNT_DATA_KEY)
        val savedDatePosition: Int = savedInstanceState.getInt(TICKET_DATE_POSITION_DATA_KEY)
        val savedTimePosition: Int = savedInstanceState.getInt(MOVIE_TIME_POSITION_DATA_KEY)

        presenter.updateTicketCount(savedCount)
        presenter.updateSelectedDatePosition(savedDatePosition)
        presenter.updateSelectedTimePosition(savedTimePosition)
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return super.onSupportNavigateUp()
    }

    override fun showTicketCount(count: Int) {
        binding.tvReservationTicketCount.text = count.toString()
    }

    override fun showMovieInfo(movie: Movie) {
        binding.movie = movie
    }

    override fun showErrorToastMessage(message: String) {
        showShortToast(message)
    }

    override fun showSeatSelectionView(movieToReserve: MovieToReserve) {
        startActivity(SeatSelectionActivity.getIntent(this, movieToReserve))
    }

    override fun updateTimes(times: List<LocalTime>) {
        timeSpinnerAdapter.updateTimeItems(times)
    }

    override fun showSelectedDate(position: Int) {
        binding.spinnerReservationDate.setSelection(position)
    }

    override fun showSelectedTime(position: Int) {
        binding.spinnerReservationTime.setSelection(position)
    }

    companion object {
        private const val TICKET_COUNT_DATA_KEY = "count"
        private const val TICKET_DATE_POSITION_DATA_KEY = "date"
        private const val MOVIE_TIME_POSITION_DATA_KEY = "time"
        private const val SCREENING_INFO_KEY = "data"

        fun getIntent(
            context: Context,
            theaterMovieSchedule: TheaterMovieSchedule,
        ): Intent =
            Intent(
                context,
                ReservationActivity::class.java,
            ).apply { putExtra(SCREENING_INFO_KEY, theaterMovieSchedule) }
    }
}
