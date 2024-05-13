package woowacourse.movie.ui.selection

import android.app.AlertDialog
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.widget.TableLayout
import android.widget.TableRow
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.view.children
import androidx.databinding.BindingAdapter
import androidx.databinding.DataBindingUtil
import woowacourse.movie.R
import woowacourse.movie.data.database.MovieDatabase
import woowacourse.movie.data.database.ticket.TicketDao
import woowacourse.movie.databinding.ActivityMovieSeatSelectionBinding
import woowacourse.movie.domain.Seat
import woowacourse.movie.domain.UserTicket
import woowacourse.movie.ui.base.BaseActivity
import woowacourse.movie.ui.complete.MovieReservationCompleteActivity
import woowacourse.movie.ui.notification.ReservationAlarmItem
import woowacourse.movie.ui.notification.ReservationAlarmScheduler
import woowacourse.movie.ui.reservation.MovieReservationKey.RESERVATION_DETAIL
import woowacourse.movie.ui.reservation.ReservationDetail
import woowacourse.movie.ui.utils.positionToIndex
import java.time.LocalDateTime

class MovieSeatSelectionActivity :
    BaseActivity<MovieSeatSelectionPresenter>(),
    MovieSeatSelectionContract.View {
    private lateinit var binding: ActivityMovieSeatSelectionBinding
    private val selectedSeatInfo = mutableListOf<Int>()
    private val seats by lazy { binding.seatTable.makeSeats() }
    private val dao: TicketDao by lazy { MovieDatabase.getDatabase(applicationContext).ticketDao() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_movie_seat_selection)
        binding.presenter = presenter
        binding.activity = this

        val reservationDetail =
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                intent.getParcelableExtra(RESERVATION_DETAIL, ReservationDetail::class.java)
            } else {
                intent.getParcelableExtra(RESERVATION_DETAIL)
            }

        if (reservationDetail == null) {
            presenter.handleError(IllegalStateException())
            return
        }

        presenter.loadTheaterInfo(reservationDetail)
        presenter.updateSelectCompletion()
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        setOnConfirmButtonListener()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putIntegerArrayList(
            MovieSeatSelectionKey.SEAT_INFO,
            selectedSeatInfo as ArrayList<Int>,
        )
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)

        val seats = savedInstanceState.getIntegerArrayList(MovieSeatSelectionKey.SEAT_INFO)
        seats?.let {
            it.forEach { seatIndex ->
                presenter.recoverSeatSelection(seatIndex)
                selectedSeatInfo.add(seatIndex)
            }
        }
    }

    fun showAlertDialog() {
        AlertDialog.Builder(this)
            .setCancelable(false)
            .setTitle(R.string.selection_dialog_title)
            .setMessage(R.string.selection_dialog_content)
            .setPositiveButton(R.string.selection_dialog_btn_complete) { _, _ ->
                presenter.completeReservation()
            }
            .setNegativeButton(R.string.selection_dialog_btn_cancel) { dialog, _ ->
                dialog.dismiss()
            }
            .show()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> finish()
        }
        return super.onOptionsItemSelected(item)
    }

    override fun initializePresenter(): MovieSeatSelectionPresenter {
        return MovieSeatSelectionPresenter(this, dao)
    }

    override fun showSelectedSeat(index: Int) {
        seats[index]
            .setBackgroundColor(
                ContextCompat.getColor(this, R.color.selected_seat),
            )
    }

    override fun showUnSelectedSeat(index: Int) {
        seats[index]
            .setBackgroundColor(
                ContextCompat.getColor(this, R.color.unSelected_seat),
            )
    }

    override fun showReservationTotalAmount(amount: Int) {
        binding.totalSeatAmountText.text =
            resources.getString(R.string.selection_total_price)
                .format(amount)
    }

    override fun updateSelectCompletion(isComplete: Boolean) {
        binding.confirmButton.apply {
            isEnabled = isComplete
            isClickable = isComplete
        }
    }

    override fun setAlarm(
        reservationId: Long,
        reservedTime: LocalDateTime,
        movieTitle: String,
    ) {
        val scheduler = ReservationAlarmScheduler(this)
        scheduler.setSchedule(
            ReservationAlarmItem(
                reservationId,
                reservedTime,
                getString(R.string.notification_reservation_title),
                getString(R.string.notification_reservation_subtitle, movieTitle),
            ),
        )
    }

    override fun showReservationInfo(
        userTicket: UserTicket,
        rowSize: Int,
        colSize: Int
    ) {
        binding.totalSeatAmountText.text =
            resources.getString(R.string.selection_total_price)
                .format(userTicket.seatInformation.totalSeatAmount())
        binding.title = userTicket.title
        binding.confirmButton.apply {
            val isCompleted = userTicket.seatInformation.checkSelectCompletion()
            isEnabled = isCompleted
            isClickable = isCompleted
        }
        repeat(rowSize) { row ->
            makeSeats(colSize, row)
        }
    }

    override fun navigateToCompleteScreen(ticketId: Long) {
        Intent(this, MovieReservationCompleteActivity::class.java).run {
            putExtra(MovieSeatSelectionKey.TICKET_ID, ticketId)
            startActivity(this)
        }
    }

    override fun showError(throwable: Throwable) {
        Log.e(TAG, throwable.message.toString())
        Toast.makeText(this, resources.getString(R.string.toast_invalid_key), Toast.LENGTH_LONG)
            .show()
        finish()
    }

    private fun TableLayout.makeSeats(): List<TextView> =
        children.filterIsInstance<TableRow>().flatMap { it.children }
            .filterIsInstance<TextView>().toList()

    private fun makeSeats(
        colSize: Int,
        row: Int,
    ) {
        repeat(colSize) { col ->
            seats[positionToIndex(row, col)].apply {
                setOnClickListener {
                    selectedSeatInfo.add(positionToIndex(row, col))
                }
            }
        }
    }

    private fun setOnConfirmButtonListener() {
        binding.confirmButton.setOnClickListener {
            showAlertDialog()
        }
    }

    companion object {
        private val TAG = MovieSeatSelectionActivity::class.simpleName
        private const val MOVIE_CONTENT_ID_DEFAULT_VALUE = -1L
    }
}

@BindingAdapter("row", "column")
fun setPosition(
    textView: TextView,
    row: Int,
    column: Int,
) {
    textView.text = Seat(row, column).toString()
}
