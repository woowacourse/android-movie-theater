package woowacourse.movie.presentation.seatSelection

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.MenuItem
import android.widget.TableRow
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.children
import woowacourse.movie.databinding.ActivitySeatSelectionBinding
import woowacourse.movie.db.ReservationDatabase
import woowacourse.movie.model.Movie
import woowacourse.movie.model.Reservation
import woowacourse.movie.model.Seat
import woowacourse.movie.model.SeatGrade
import woowacourse.movie.model.SeatingSystem
import woowacourse.movie.notification.AlarmController
import woowacourse.movie.notification.CurrentTimeProvider
import woowacourse.movie.presentation.ticketingResult.TicketingResultActivity
import woowacourse.movie.utils.formatSeat

class SeatSelectionActivity : AppCompatActivity(), SeatSelectionContract.View {
    private lateinit var presenter: SeatSelectionPresenter
    private val seatItems: List<TextView> by lazy {
        binding.tableLayoutSeats.children.filterIsInstance<TableRow>()
            .flatMap { tableRow ->
                tableRow.children.filterIsInstance<TextView>().toList()
            }.toList()
    }
    private lateinit var binding: ActivitySeatSelectionBinding
    private val alarmController by lazy { AlarmController(this, CurrentTimeProvider()) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySeatSelectionBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val movieId = intent.getLongExtra(EXTRA_MOVIE_ID, EXTRA_DEFAULT_MOVIE_ID)
        val ticketCount = intent.getIntExtra(EXTRA_COUNT, EXTRA_DEFAULT_TICKET_COUNT)
        val screeningDate = intent.getStringExtra(EXTRA_SCREENING_DATE) ?: ""
        val screeningTime = intent.getStringExtra(EXTRA_SCREENING_TIME) ?: ""
        val theaterId = intent.getLongExtra(EXTRA_THEATER_ID, EXTRA_DEFAULT_THEATER_ID)

        presenter = SeatSelectionPresenter(this, ticketCount, ReservationDatabase.getDatabase(this))
        presenter.loadMovieData(movieId)
        presenter.loadSeats()
        initializeCompleteButton(screeningDate, screeningTime, theaterId)
    }

    private fun initializeCompleteButton(
        screeningDate: String,
        screeningTime: String,
        theaterId: Long,
    ) {
        binding.btnComplete.setOnClickListener {
            AlertDialog.Builder(this)
                .setTitle("예매 확인")
                .setMessage("정말 예매하시겠습니까?")
                .setPositiveButton("예매 완료") { _, _ ->
                    presenter.navigate(screeningDate, screeningTime, theaterId)
                }
                .setNegativeButton("취소") { dialog, _ -> dialog.dismiss() }
                .setCancelable(false)
                .show()
        }
    }

    override fun displaySeats(seats: List<Seat>) {
        seatItems.forEachIndexed { index, textView ->
            val seat = seats[index]
            textView.text = formatSeat(seat)
            val colorCode =
                when (seat.seatGrade) {
                    SeatGrade.B_CLASS -> "#8E13EF"
                    SeatGrade.A_CLASS -> "#1B48E9"
                    SeatGrade.S_CLASS -> "#19D358"
                }
            textView.setTextColor(Color.parseColor(colorCode))
            textView.setOnClickListener { presenter.updateSeatSelection(index) }
        }
    }

    override fun bindMovie(movie: Movie) {
        binding.movie = movie
    }

    override fun updateSelectedSeatUI(index: Int) {
        seatItems[index].isSelected = true
    }

    override fun updateUnSelectedSeatUI(index: Int) {
        seatItems[index].isSelected = false
    }

    override fun bindSeatingSystem(seatingSystem: SeatingSystem) {
        binding.seatingSystem = seatingSystem
    }

    override fun updateViews() {
        binding.invalidateAll()
    }

    override fun showToastMessage(message: String?) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    override fun navigate(reservation: Reservation) {
        alarmController.setReservationAlarm(reservation)
        startActivity(TicketingResultActivity.createIntent(this, reservation))
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) finish()
        return super.onOptionsItemSelected(item)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        val selectedIndexes = presenter.seatingSystem.getSelectedSeatsIndex()
        outState.putIntegerArrayList(KEY_SELECTED_SEATS, ArrayList(selectedIndexes))
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        val savedSelectedSeat =
            savedInstanceState.getIntegerArrayList(KEY_SELECTED_SEATS)?.toList() ?: emptyList()
        savedSelectedSeat.forEach { presenter.updateSeatSelection(it) }
    }

    companion object {
        const val EXTRA_MOVIE_ID = "movie_id"
        const val EXTRA_COUNT = "ticket_count"
        const val EXTRA_THEATER_ID = "theater_id"
        const val EXTRA_SCREENING_DATE = "screening_date"
        const val EXTRA_SCREENING_TIME = "screening_time"
        const val EXTRA_DEFAULT_MOVIE_ID = -1L
        const val EXTRA_DEFAULT_TICKET_COUNT = -1
        const val EXTRA_DEFAULT_THEATER_ID = -1L
        const val KEY_SELECTED_SEATS = "selected_seats"

        fun createIntent(
            context: Context,
            movieId: Long,
            count: Int,
            screeningDate: String,
            screeningTime: String,
            theaterId: Long,
        ): Intent {
            return Intent(context, SeatSelectionActivity::class.java).apply {
                putExtra(EXTRA_MOVIE_ID, movieId)
                putExtra(EXTRA_COUNT, count)
                putExtra(EXTRA_SCREENING_DATE, screeningDate)
                putExtra(EXTRA_SCREENING_TIME, screeningTime)
                putExtra(EXTRA_THEATER_ID, theaterId)
            }
        }
    }
}
