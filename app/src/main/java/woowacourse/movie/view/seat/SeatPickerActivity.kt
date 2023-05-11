package woowacourse.movie.view.seat

import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.widget.TableLayout
import android.widget.TableRow
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.children
import woowacourse.movie.R
import woowacourse.movie.alarm.AlarmManager
import woowacourse.movie.broadcastreceiver.NotificationReceiver
import woowacourse.movie.db.ReservationDBHelper
import woowacourse.movie.db.ReservationDataImpl
import woowacourse.movie.model.MovieTicketModel
import woowacourse.movie.model.PriceModel
import woowacourse.movie.model.seat.SeatModel
import woowacourse.movie.model.seat.SeatsModel
import woowacourse.movie.utils.getParcelable
import woowacourse.movie.utils.getParcelableByKey
import woowacourse.movie.view.movieTicket.MovieTicketActivity
import java.time.ZoneId
import java.time.ZonedDateTime

class SeatPickerActivity : AppCompatActivity(), SeatPickerContract.View {
    override lateinit var presenter: SeatPickerContract.Presenter

    private val seatViews = mutableMapOf<SeatModel, TextView>()
    private val seats = SeatsModel().getAll()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_seat_picker)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val reservationDB = ReservationDBHelper(this)
        val reservationDataImpl = ReservationDataImpl(reservationDB.writableDatabase)

        presenter = SeatPicketPresenter(this, reservationDataImpl)

        loadSavedData(savedInstanceState)

        setSeatViews()
        setDoneButton()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putParcelable(TICKET_INSTANCE_KEY, presenter.getTicketOriginalModel())
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

    private fun setSeatViews() {
        val views = findViewById<TableLayout>(R.id.layout_seat)
            .children
            .filterIsInstance<TableRow>()
            .flatMap { it.children }
            .filterIsInstance<TextView>()
            .toList()

        views.zip(seats) { view, seat ->
            seatViews[seat] = view
            setSeat(seat)

            presenter.setSelectedSeat(seat)

            view.setOnClickListener {
                selectSeat(view, seat)
                presenter.canReserveSeat()
            }
        }
    }

    private fun setSeat(seat: SeatModel) {
        seatViews[seat]?.text = getString(R.string.seat, seat.row.letter, seat.column.value)
        seatViews[seat]?.setTextColor(getColor(seat.rank.color))
    }

    override fun setSelectedSeat(seat: SeatModel, isSelected: Boolean) {
        seatViews[seat]?.isSelected = isSelected
    }

    private fun selectSeat(
        view: TextView,
        seat: SeatModel
    ) {
        if (view.isSelected) {
            presenter.cancelSeat(seat)
            return
        }
        selectEmptySeat(seat)
    }

    private fun selectEmptySeat(seat: SeatModel) {
        if (presenter.canReserveSeat()) {
            presenter.addSeat(seat)
            return
        }
        notifyUnableToAddSeat()
    }

    override fun setTextPrice(price: PriceModel) {
        val priceView = findViewById<TextView>(R.id.seat_picker_price)
        priceView.text = price.format()
    }

    override fun setTicketViews(ticketModel: MovieTicketModel) {
        val titleView = findViewById<TextView>(R.id.seat_picker_title)
        val priceView = findViewById<TextView>(R.id.seat_picker_price)
        titleView.text = ticketModel.title
        priceView.text = ticketModel.price.format()
    }

    private fun PriceModel.format(): String = getString(R.string.price, amount)

    private fun notifyUnableToAddSeat() {
        Toast
            .makeText(
                this,
                getString(R.string.toast_message_seat_selection_done),
                Toast.LENGTH_SHORT
            )
            .show()
    }

    override fun setEnabledDone(isEnabled: Boolean) {
        val doneButton = findViewById<TextView>(R.id.seat_picker_done_button)
        doneButton.isEnabled = isEnabled
    }

    private fun setDoneButton() {
        val doneButton = findViewById<TextView>(R.id.seat_picker_done_button)
        doneButton.setOnClickListener {
            presenter.actionReservation()
        }
    }

    override fun showReservationCheckDialog(ticketModel: MovieTicketModel) {
        AlertDialog.Builder(this)
            .setTitle(getString(R.string.dialog_title_seat_selection_check))
            .setMessage(getString(R.string.dialog_message_seat_selection_check))
            .setPositiveButton(getString(R.string.dialog_positive_button_seat_selection_check)) { _, _ ->
                presenter.saveReservation(ticketModel)
                setAlarmManager(ticketModel)
                moveToTicketActivity(ticketModel)
            }
            .setNegativeButton(getString(R.string.dialog_negative_button_seat_selection_check)) { dialog, _ ->
                dialog.dismiss()
            }
            .setCancelable(false)
            .show()
    }

    private fun setAlarmManager(ticketModel: MovieTicketModel) {
        val time =
            ZonedDateTime.of(
                ticketModel.time.dateTime.minusMinutes(ALARM_MINUTE_CONDITION),
                ZoneId.systemDefault()
            )
                .toInstant().toEpochMilli()
        val pendingIntent = getPendingIntent(ticketModel)
        AlarmManager.setAlarm(this, time, pendingIntent)
    }

    private fun getPendingIntent(ticketModel: MovieTicketModel): PendingIntent {
        return PendingIntent.getBroadcast(
            this,
            ticketModel.hashCode(),
            NotificationReceiver.createIntent(this, ticketModel),
            PendingIntent.FLAG_IMMUTABLE
        )
    }

    private fun moveToTicketActivity(ticketModel: MovieTicketModel) {
        val intent = MovieTicketActivity.createIntent(this, ticketModel)
        startActivity(intent)
    }

    private fun loadSavedData(savedInstanceState: Bundle?) {
        val ticketModel: MovieTicketModel = savedInstanceState?.getParcelableByKey(
            TICKET_INSTANCE_KEY
        )
            ?: intent.getParcelable(TICKET_EXTRA_KEY)!!

        presenter.setupTicket(ticketModel)
    }

    companion object {
        private const val TICKET_EXTRA_KEY = "ticket_extra"
        private const val TICKET_INSTANCE_KEY = "ticket_instance"
        private const val ALARM_MINUTE_CONDITION = 30L

        fun createIntent(context: Context, ticket: MovieTicketModel): Intent {
            val intent = Intent(context, SeatPickerActivity::class.java)
            intent.putExtra(TICKET_EXTRA_KEY, ticket)
            return intent
        }
    }
}
