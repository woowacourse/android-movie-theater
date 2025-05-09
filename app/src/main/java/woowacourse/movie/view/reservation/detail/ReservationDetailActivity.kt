package woowacourse.movie.view.reservation.detail

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import woowacourse.movie.R
import woowacourse.movie.databinding.ActivityReservationBinding
import woowacourse.movie.model.DummyMovieDao
import woowacourse.movie.model.MovieTicket
import woowacourse.movie.model.TheaterUIModel
import woowacourse.movie.view.Extras
import woowacourse.movie.view.getParcelableExtraCompat
import woowacourse.movie.view.reservation.detail.viewhelper.DateTimeHelper
import woowacourse.movie.view.reservation.detail.viewhelper.MovieInfoHelper
import woowacourse.movie.view.reservation.seat.SeatSelectActivity
import java.time.LocalDate

class ReservationDetailActivity :
    AppCompatActivity(),
    ReservationDetailContract.View {
    lateinit var binding: ActivityReservationBinding
    val presenter: ReservationDetailPresenter by lazy {
        ReservationDetailPresenter(
            this,
            DummyMovieDao,
        )
    }
    private val movieInfoHelper: MovieInfoHelper by lazy { MovieInfoHelper(this, binding) }
    private val dateTimeHelper: DateTimeHelper by lazy { DateTimeHelper(this, binding) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityReservationBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.presenter = presenter
        ViewCompat.setOnApplyWindowInsetsListener(binding.svReservation) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        presenter.fetchData {
            intent?.getParcelableExtraCompat<TheaterUIModel>(Extras.TheaterData.THEATER_UI_MODEL_KEY)
        }
        setupCompleteButtonClick()
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun showMovieInfo(
        posterResId: Int,
        title: String,
        startDate: String,
        endDate: String,
        runningTime: Int,
    ) {
        movieInfoHelper.showMovieInfo(posterResId, title, startDate, endDate, runningTime)
    }

    override fun showTicketCount(count: Int) {
        binding.tvReservationTicketCount.text = count.toString()
    }

    override fun updateDateAdapter(
        duration: List<LocalDate>,
        selected: Int,
    ) {
        dateTimeHelper.updateDateAdapter(duration, selected)
    }

    override fun updateTimeAdapter(times: List<String>) {
        dateTimeHelper.updateTimeAdapter(times)
    }

    private fun setupCompleteButtonClick() {
        binding.btnReservationSelectComplete.setOnClickListener {
            presenter.createTicket { ticket ->
                if (presenter.isTimeSelected) {
                    navigateToSeatSelect(ticket)
                } else {
                    showToast(R.string.reservation_error_empty_selected_movie_time)
                }
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

    override fun showErrorMessage(
        @StringRes messageResId: Int,
    ) {
        showToast(messageResId)
    }

    override fun showToast(stringResId: Int) {
        Toast.makeText(this, getString(stringResId), Toast.LENGTH_SHORT).show()
    }

    override fun finishView() {
        finish()
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
