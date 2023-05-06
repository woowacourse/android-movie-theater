package woowacourse.movie.ui.activity.seatpicker.view

import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.database.sqlite.SQLiteDatabase
import android.os.Bundle
import android.view.MenuItem
import android.widget.TableRow
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.children
import woowacourse.movie.R
import woowacourse.movie.alarm.AlarmManager
import woowacourse.movie.broadcastreceiver.NotificationReceiver
import woowacourse.movie.data.db.DBHelper
import woowacourse.movie.data.entity.Seats
import woowacourse.movie.databinding.ActivitySeatPickerBinding
import woowacourse.movie.ui.activity.MovieTicketActivity
import woowacourse.movie.ui.activity.seatpicker.SeatPickerContract
import woowacourse.movie.ui.activity.seatpicker.presenter.SeatPickerPresenter
import woowacourse.movie.ui.model.MovieTicketModel
import woowacourse.movie.ui.model.seat.SeatModel
import woowacourse.movie.ui.utils.getParcelable
import woowacourse.movie.ui.utils.getParcelableByKey
import java.time.ZoneId
import java.time.ZonedDateTime

class SeatPickerActivity : AppCompatActivity(), SeatPickerContract.View {
    private lateinit var binding: ActivitySeatPickerBinding
    private val db: SQLiteDatabase by lazy { DBHelper(this).writableDatabase }
    override lateinit var presenter: SeatPickerContract.Presenter
    private val seats = Seats().getAll()
    private val seatTable = mutableMapOf<SeatModel, TextView>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        presenter = SeatPickerPresenter(this)
        binding = ActivitySeatPickerBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        loadSavedData(savedInstanceState)

        setDoneButton()
    }

    override fun onDestroy() {
        super.onDestroy()
        db.close()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        val ticket = presenter.getTicketModelWithOriginalPrice()
        outState.putParcelable(TICKET_INSTANCE_KEY, ticket)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                finish()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun applyTicketData(ticket: MovieTicketModel) {
        setSeatViews(ticket)
        setTicketViews(ticket)
    }

    override fun setSeatReserved(seat: SeatModel) {
        val view = seatTable[seat]
        view?.let {
            it.isSelected = true
        }
    }

    override fun setSeatCanceled(seat: SeatModel) {
        val view = seatTable[seat]
        view?.let {
            it.isSelected = false
        }
    }

    override fun updatePrice(price: Int) {
        binding.seatPickerPrice.text = price.formatPrice()
    }

    override fun notifyUnableToReserveMore() {
        Toast
            .makeText(
                this,
                getString(R.string.toast_message_seat_selection_done),
                Toast.LENGTH_SHORT
            )
            .show()
    }

    override fun deactivateDoneButton() {
        binding.seatPickerDoneButton.isEnabled = false
    }

    override fun activateDoneButton() {
        binding.seatPickerDoneButton.isEnabled = true
    }

    override fun afterReservation(ticket: MovieTicketModel) {
        setAlarm(ticket)
        moveToTicketActivity(ticket)
    }

    private fun Int.formatPrice(): String = getString(R.string.price, this)

    private fun setSeatViews(ticket: MovieTicketModel) {
        val seatViews = binding.layoutSeat
            .children
            .filterIsInstance<TableRow>()
            .flatMap { it.children }
            .filterIsInstance<TextView>()
            .toList()

        seatViews.zip(seats) { view, seat ->
            seatTable[seat] = view
            view.text = getString(R.string.seat, seat.row.letter, seat.column.value)
            view.setTextColor(getColor(seat.rank.color))
            if (ticket.isSelectedSeat(seat)) view.isSelected = true
            view.setOnClickListener {
                selectSeat(view, seat)
                presenter.checkSelectionDone()
            }
        }
    }

    private fun selectSeat(
        view: TextView,
        seat: SeatModel
    ) {
        if (view.isSelected) {
            presenter.cancelSeat(seat)
            return
        }
        presenter.reserveSeat(seat)
    }

    private fun setTicketViews(ticket: MovieTicketModel) {
        binding.seatPickerTitle.text = ticket.title
        binding.seatPickerPrice.text = ticket.price.amount.formatPrice()
    }

    private fun setDoneButton() {
        binding.seatPickerDoneButton.setOnClickListener {
            showReservationCheckDialog()
        }
        presenter.checkSelectionDone()
    }

    private fun showReservationCheckDialog() {
        AlertDialog.Builder(this)
            .setTitle(getString(R.string.dialog_title_seat_selection_check))
            .setMessage(getString(R.string.dialog_message_seat_selection_check))
            .setPositiveButton(getString(R.string.dialog_positive_button_seat_selection_check)) { _, _ ->
                presenter.addReservation(db)
            }
            .setNegativeButton(getString(R.string.dialog_negative_button_seat_selection_check)) { dialog, _ ->
                dialog.dismiss()
            }
            .setCancelable(false)
            .show()
    }

    private fun setAlarm(ticketModel: MovieTicketModel) {
        val time =
            ZonedDateTime.of(
                ticketModel.time.notificationTime,
                ZoneId.systemDefault()
            )
                .toInstant().toEpochMilli()
        val intent = NotificationReceiver.createIntent(this, ticketModel)
        val pendingIntent = PendingIntent.getBroadcast(
            this,
            ticketModel.hashCode(),
            intent,
            PendingIntent.FLAG_IMMUTABLE
        )

        AlarmManager.setAlarm(this, time, pendingIntent)
    }

    private fun moveToTicketActivity(ticketModel: MovieTicketModel) {
        val intent = MovieTicketActivity.createIntent(this, ticketModel)
        startActivity(intent)
    }

    private fun loadSavedData(savedInstanceState: Bundle?) {
        val ticket = savedInstanceState?.getParcelableByKey<MovieTicketModel>(
            TICKET_INSTANCE_KEY
        )
            ?: intent.getParcelable(TICKET_EXTRA_KEY)!!
        presenter.initTicket(ticket)
    }

    companion object {
        private const val TICKET_EXTRA_KEY = "ticket_extra"
        private const val TICKET_INSTANCE_KEY = "ticket_instance"

        fun createIntent(context: Context, ticket: MovieTicketModel): Intent {
            val intent = Intent(context, SeatPickerActivity::class.java)
            intent.putExtra(TICKET_EXTRA_KEY, ticket)
            return intent
        }
    }
}
