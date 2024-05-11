package woowacourse.movie.feature.seat

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.widget.TableLayout
import android.widget.TableRow
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.core.view.children
import woowacourse.movie.R
import woowacourse.movie.data.movie.MovieRepository
import woowacourse.movie.data.reservation.Reservation
import woowacourse.movie.data.reservation.ReservationRepositoryImpl
import woowacourse.movie.data.ticket.TicketDatabase
import woowacourse.movie.data.ticket.TicketRepositoryImpl
import woowacourse.movie.data.ticket.entity.Ticket
import woowacourse.movie.databinding.ActivityMovieSeatSelectionBinding
import woowacourse.movie.feature.result.MovieResultActivity
import woowacourse.movie.feature.setting.notification.TicketAlarm
import woowacourse.movie.model.MovieGrade
import woowacourse.movie.model.MovieSeat
import woowacourse.movie.model.MovieSelectedSeats
import woowacourse.movie.util.BaseActivity
import woowacourse.movie.util.MovieIntentConstant.DEFAULT_VALUE_NOTIFICATION
import woowacourse.movie.util.MovieIntentConstant.DEFAULT_VALUE_RESERVATION_ID
import woowacourse.movie.util.MovieIntentConstant.INVALID_VALUE_RESERVATION_COUNT
import woowacourse.movie.util.MovieIntentConstant.KEY_NOTIFICATION
import woowacourse.movie.util.MovieIntentConstant.KEY_RESERVATION_COUNT
import woowacourse.movie.util.MovieIntentConstant.KEY_RESERVATION_ID
import woowacourse.movie.util.MovieIntentConstant.KEY_SELECTED_SEAT_POSITIONS
import woowacourse.movie.util.MovieSharedPreferences
import woowacourse.movie.util.formatSeat

class MovieSeatSelectionActivity :
    BaseActivity<MovieSeatSelectionContract.Presenter>(),
    MovieSeatSelectionContract.View {
    private lateinit var binding: ActivityMovieSeatSelectionBinding
    private lateinit var movieSelectedSeats: MovieSelectedSeats
    private val tableSeats: List<TextView> by lazy {
        findViewById<TableLayout>(R.id.table_seat).children.filterIsInstance<TableRow>()
            .flatMap { tableRow ->
                tableRow.children.filterIsInstance<TextView>().toList()
            }.toList()
    }
    private val ticketAlarm by lazy { TicketAlarm(this) }
    private lateinit var reservation: Reservation
    private val ticketRepository by lazy { TicketRepositoryImpl(TicketDatabase.instance(application).ticketDao()) }
    private val movieSharedPreferences by lazy { MovieSharedPreferences.instance(application) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMovieSeatSelectionBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val reservationId = intent.getLongExtra(KEY_RESERVATION_ID, DEFAULT_VALUE_RESERVATION_ID)
        initializeView(reservationId)
    }

    override fun initializePresenter() = MovieSeatSelectionPresenter(this)

    private fun initializeView(reservationId: Long) {
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        presenter.loadReservation(ReservationRepositoryImpl, reservationId)
        presenter.loadTableSeats(movieSelectedSeats)

        binding.btnComplete.setOnClickListener { displayDialog() }
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        val reservationCount =
            savedInstanceState.getInt(KEY_RESERVATION_COUNT, INVALID_VALUE_RESERVATION_COUNT)
        movieSelectedSeats = MovieSelectedSeats(reservationCount)
        presenter.updateSelectedSeats(movieSelectedSeats)

        val selectedSeatPositions = savedInstanceState.getIntArray(KEY_SELECTED_SEAT_POSITIONS)
        setUpSelectedSeats(selectedSeatPositions)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) finish()
        return super.onOptionsItemSelected(item)
    }

    override fun setUpReservation(reservation: Reservation) {
        this.reservation = reservation
        movieSelectedSeats = MovieSelectedSeats(reservation.reservationCount.count)
        binding.movieTitle = MovieRepository.getMovieById(reservation.movieId).title
    }

    override fun setUpTableSeats(baseSeats: List<MovieSeat>) {
        tableSeats.forEachIndexed { index, view ->
            val seat = baseSeats[index]
            view.text = seat.formatSeat()
            view.setTextColor(ContextCompat.getColor(this, seat.grade.getSeatColor()))
            view.setOnClickListener {
                presenter.selectSeat(index)
            }
        }
    }

    override fun updateSeatBackgroundColor(
        index: Int,
        isSelected: Boolean,
    ) {
        val backGroundColor = if (isSelected) R.color.white else R.color.selected
        tableSeats[index].setBackgroundColor(ContextCompat.getColor(this, backGroundColor))
    }

    override fun displayDialog() {
        AlertDialog.Builder(this).setTitle("예매 확인").setMessage("정말 예매하시겠습니까?")
            .setPositiveButton("예매 완료") { _, _ ->
                presenter.reserveMovie(
                    ticketRepository = ticketRepository,
                    reservation = reservation,
                    selectedSeats = movieSelectedSeats,
                )
            }
            .setNegativeButton("취소") { dialog, _ -> dialog.dismiss() }.setCancelable(false).show()
    }

    override fun updateSelectResult(movieSelectedSeats: MovieSelectedSeats) {
        binding.movieSelectedSeats = movieSelectedSeats
    }

    override fun navigateToResultView(ticketId: Long) {
        val intent = MovieResultActivity.newIntent(this, ticketId)
        startActivity(intent)
    }

    override fun showToastInvalidMovieIdError(throwable: Throwable) {
        Log.e(TAG, "invalid movie id - ${throwable.message}")
        showToast(R.string.invalid_movie_id)
        finish()
    }

    override fun setTicketAlarm(ticket: Ticket) {
        if (!movieSharedPreferences.getBoolean(KEY_NOTIFICATION, DEFAULT_VALUE_NOTIFICATION)) {
            return
        }
        ticketAlarm.setReservationAlarm(ticket)
    }

    private fun showToast(
        @StringRes stringResId: Int,
    ) {
        Toast.makeText(this, resources.getString(stringResId), Toast.LENGTH_SHORT).show()
    }

    private fun setUpSelectedSeats(selectedPositions: IntArray?) {
        selectedPositions?.forEach { position ->
            presenter.selectSeat(position)
        }
    }

    private fun MovieGrade.getSeatColor(): Int {
        return when (this) {
            MovieGrade.B_GRADE -> R.color.b_grade
            MovieGrade.S_GRADE -> R.color.s_grade
            MovieGrade.A_GRADE -> R.color.a_grade
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)

        val count = movieSelectedSeats.reservationCount
        outState.putInt(KEY_RESERVATION_COUNT, count)

        val selectedPositions = movieSelectedSeats.getSelectedPositions()
        outState.putIntArray(KEY_SELECTED_SEAT_POSITIONS, selectedPositions)
    }

    companion object {
        private val TAG = MovieSeatSelectionActivity::class.simpleName

        fun newIntent(
            context: Context,
            reservationId: Long,
        ): Intent {
            return Intent(context, MovieSeatSelectionActivity::class.java).apply {
                putExtra(KEY_RESERVATION_ID, reservationId)
            }
        }
    }
}
