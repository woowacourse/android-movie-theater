package woowacourse.movie.view.reservation.detail

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.databinding.DataBindingUtil
import woowacourse.movie.R
import woowacourse.movie.databinding.ActivityReservationBinding
import woowacourse.movie.domain.model.MovieTicket
import woowacourse.movie.domain.model.TheaterUIModel
import woowacourse.movie.view.Extras
import woowacourse.movie.view.getParcelableExtraCompat
import woowacourse.movie.view.model.MovieUiModel
import woowacourse.movie.view.movie.MoviesActivity
import woowacourse.movie.view.reservation.seat.SeatSelectActivity
import java.time.LocalDate

class ReservationDetailActivity :
    AppCompatActivity(),
    ReservationDetailContract.View {
    private lateinit var binding: ActivityReservationBinding
    private val reservationDialog by lazy { ReservationDetailDialog() }
    private val presenter: ReservationDetailPresenter by lazy { ReservationDetailPresenter(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = DataBindingUtil.setContentView(this, R.layout.activity_reservation)
        binding.presenter = presenter
        ViewCompat.setOnApplyWindowInsetsListener(binding.svReservation) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val theater =
            intent?.getParcelableExtraCompat<TheaterUIModel>(Extras.TheaterData.THEATER_UI_MODEL_KEY)
        presenter.fetchData(theater)

        setupButtonClickListener()
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    private fun setupButtonClickListener() {
        setupCompleteButtonClick()
    }

    override fun showMovieInfo(movie: MovieUiModel) {
        setupMovieReservationInfo(movie)
        presenter.initDateAdapter()
    }

    override fun showErrorDialog() {
        reservationDialog.show(
            this,
            getString(R.string.reservation_error_dialog_title),
            getString(R.string.reservation_error_dialog_message),
            null,
        ) { _ ->
            val intent = Intent(this, MoviesActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    override fun showTicketCount(count: Int) {
        binding.tvReservationTicketCount.text = count.toString()
    }

    override fun updateDateAdapter(
        duration: List<LocalDate>,
        selected: Int,
    ) {
        val dateAdapter =
            ArrayAdapter(
                this,
                android.R.layout.simple_spinner_dropdown_item,
                duration,
            )

        binding.spinnerReservationDate.apply {
            adapter = dateAdapter
            setSelection(selected)
            onItemSelectedListener =
                object : AdapterView.OnItemSelectedListener {
                    override fun onItemSelected(
                        parent: AdapterView<*>?,
                        view: View?,
                        position: Int,
                        id: Long,
                    ) {
                        presenter.selectDate(duration[position])
                    }

                    override fun onNothingSelected(parent: AdapterView<*>?) = Unit
                }
        }
    }

    override fun updateTimeAdapter(times: List<String>) {
        val timeAdapter =
            ArrayAdapter(
                this,
                android.R.layout.simple_spinner_dropdown_item,
                times,
            )

        binding.spinnerReservationTime.apply {
            adapter = timeAdapter
            onItemSelectedListener =
                object : AdapterView.OnItemSelectedListener {
                    override fun onItemSelected(
                        parent: AdapterView<*>?,
                        view: View?,
                        position: Int,
                        id: Long,
                    ) {
                        presenter.selectTime(position)
                    }

                    override fun onNothingSelected(parent: AdapterView<*>?) = Unit
                }
        }
    }

    override fun navigateToSeatSelect(ticket: MovieTicket) {
        val intent =
            Intent(this, SeatSelectActivity::class.java).apply {
                putExtra(Extras.TicketData.TICKET_KEY, ticket)
            }
        startActivity(intent)
    }

    override fun showToast(stringResId: Int) {
        Toast.makeText(this, getString(stringResId), Toast.LENGTH_SHORT).show()
    }

    override fun showTimeNotSelectedError() {
        showToast(R.string.reservation_error_empty_selected_movie_time)
    }

    private fun setupMovieReservationInfo(movie: MovieUiModel) {
        binding.ivReservationPoster.setImageResource(movie.poster)
        binding.tvReservationTitle.text = movie.name
        binding.tvReservationScreeningDate.text =
            resources.getString(R.string.movie_screening_date, movie.startDate, movie.endDate)
        binding.tvReservationRunningTime.text =
            getString(R.string.movie_running_time).format(movie.runningTime)
    }

    private fun setupCompleteButtonClick() {
        binding.btnReservationSelectComplete.setOnClickListener {
            presenter.completeSelected()
        }
    }

    private fun setupSavedData(savedInstanceState: Bundle?) {
        val savedCount = savedInstanceState?.getInt(Extras.ReservationData.TICKET_COUNT_KEY) ?: 1
        presenter.restoreTicketCount(savedCount)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt(Extras.ReservationData.TICKET_COUNT_KEY, presenter.currentTicketCount())
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        setupSavedData(savedInstanceState)
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return super.onSupportNavigateUp()
    }
}
