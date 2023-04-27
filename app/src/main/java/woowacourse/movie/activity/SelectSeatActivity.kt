package woowacourse.movie.activity

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import domain.Ticket
import domain.TicketOffice
import woowacourse.movie.R
import woowacourse.movie.getSerializableCompat
import woowacourse.movie.receiver.ReservationNotificationReceiver
import woowacourse.movie.setBackgroundColorId
import woowacourse.movie.view.MovieView
import woowacourse.movie.view.SeatTable
import woowacourse.movie.view.SeatView
import woowacourse.movie.view.mapper.TicketsMapper
import woowacourse.movie.view.model.MovieUiModel
import woowacourse.movie.view.model.SeatUiModel
import woowacourse.movie.view.model.TicketDateUiModel
import woowacourse.movie.view.model.TicketsUiModel
import java.sql.Date
import java.text.NumberFormat
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.ZoneId
import java.util.*

class SelectSeatActivity : AppCompatActivity() {
    private val ticketOffice: TicketOffice by lazy {
        receiveTicketOfficeData()
    }

    private val ticketDateTime: LocalDateTime by lazy {
        receiveTicketDateTimeData() ?: run {
            finishActivityWithToast(getString(R.string.reservation_data_null_error))
            LocalDateTime.now()
        }
    }

    private val movieUiModel: MovieUiModel by lazy {
        receiveMovieViewModelData() ?: run {
            finishActivityWithToast(getString(R.string.reservation_data_null_error))
            MovieUiModel(0, "", LocalDate.MAX, LocalDate.MAX, 0, "")
        }
    }

    private val seatTable: SeatTable by lazy {
        SeatTable(
            tableLayout = findViewById(R.id.select_seat_tableLayout),
            rowSize = 5,
            colSize = 4,
            ::updateUi
        )
    }

    private val priceTextView: TextView by lazy {
        findViewById(R.id.select_seat_price_text_view)
    }

    private val checkButton: Button by lazy {
        findViewById(R.id.select_seat_check_button)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_select_seat)
        seatTable.makeSeatTable()
        MovieView(title = findViewById(R.id.select_seat_movie_title_text_view)).render(
            movieUiModel
        )
        changePriceTextView()
        checkButtonClick()
    }

    private fun checkButtonClick() {
        checkButton.setOnClickListener {
            val dialog = createReservationAlertDialog()
            dialog.setCanceledOnTouchOutside(false)
            dialog.show()
        }
        checkButton.isClickable = false
    }

    private fun createReservationAlertDialog(): AlertDialog {
        val builder = AlertDialog.Builder(this)
        builder.setTitle(R.string.select_seat_dialog_title)
        builder.setMessage(R.string.select_seat_dialog_message)
        builder.setPositiveButton(R.string.select_seat_dialog_positive_button_text) { dialog, _ ->
            ReservationResultActivity.start(
                this,
                movieUiModel,
                TicketsMapper.toUi(ticketOffice.tickets)
            )
            registerAlarm()
        }
        builder.setNegativeButton(R.string.select_seat_dialog_negative_button_text) { dialog, _ ->
            dialog.dismiss()
        }
        return builder.create()
    }

    private fun registerAlarm() {
        val ticketsUiModel = TicketsMapper.toUi(ticketOffice.tickets)
        val receiverIntent = generateReceiverIntent(ticketsUiModel)
        val pendingIntent =
            PendingIntent.getBroadcast(this, 125, receiverIntent, PendingIntent.FLAG_IMMUTABLE)
        val alarmManager: AlarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val timeMillis = getTimeInMillis()
        alarmManager[AlarmManager.RTC, timeMillis] = pendingIntent
    }

    private fun generateReceiverIntent(ticketsUiModel: TicketsUiModel): Intent {
        val receiverIntent = Intent(this, ReservationNotificationReceiver::class.java)
        receiverIntent.putExtra("movie", movieUiModel)
        return receiverIntent.putExtra("tickets", ticketsUiModel)
    }

    private fun getTimeInMillis(): Long {
        val sqlDate = getAlarmDateTime(ticketDateTime, 30)
        val calendar: Calendar = Calendar.getInstance()
        calendar.time = sqlDate
        return calendar.timeInMillis
    }

    private fun getAlarmDateTime(localDateTime: LocalDateTime, minute: Long): Date {
        val milliSeconds: Long = localDateTime.atZone(ZoneId.systemDefault())
            .minusMinutes(minute)
            .toInstant().toEpochMilli()
        return Date(milliSeconds)
    }


    private fun updateUi(seatView: SeatView) {
        changeSeatViewState(seatView)
        changePriceTextView()
        changeCheckButtonState()
    }

    private fun changeSeatViewState(seatView: SeatView) {
        val ticket =
            ticketOffice.generateTicket(
                ticketDateTime,
                SeatUiModel.toNumber(seatView.row),
                seatView.col
            )
        if (ticketOffice.tickets.isContainSameTicket(ticket)) {
            setViewNotSelected(seatView, ticket)
        } else {
            setViewSelected(seatView, ticket)
        }
    }

    private fun setViewNotSelected(seatView: SeatView, ticket: Ticket) {
        ticketOffice.deleteTicket(ticket)
        seatView.view.isSelected = false
        seatView.setBackgroundColorId(color = R.color.white)
    }

    private fun setViewSelected(seatView: SeatView, ticket: Ticket) {
        if (ticketOffice.isAvailableAddTicket()) {
            ticketOffice.addTicket(ticket)
            seatView.view.isSelected = true
            seatView.setBackgroundColorId(color = R.color.seat_selection_selected_seat_color)
        }
    }

    private fun changePriceTextView() {
        val formattedPrice =
            NumberFormat.getNumberInstance(Locale.US).format(ticketOffice.tickets.price.value)
        priceTextView.text = getString(R.string.select_seat_price_format).format(formattedPrice)
    }

    private fun changeCheckButtonState() {
        if (!ticketOffice.isAvailableAddTicket()) {
            checkButton.setBackgroundColorId(R.color.select_seat_clickable_check_button_background)
            checkButton.isClickable = true
        } else {
            checkButton.setBackgroundColorId(R.color.select_seat_non_clickable_check_button_background)
            checkButton.isClickable = false
        }
    }

    private fun finishActivityWithToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_LONG)
        finish()
    }

    private fun receiveTicketOfficeData(): TicketOffice {
        val peopleCount = intent.getIntExtra(PEOPLE_COUNT_KEY, 0)
        if (peopleCount == 0) finishActivityWithToast(getString(R.string.reservation_data_null_error))
        return TicketOffice(peopleCount = peopleCount)
    }

    private fun receiveMovieViewModelData(): MovieUiModel? {
        return intent.extras?.getSerializableCompat(MOVIE_KEY_VALUE)
    }

    private fun receiveTicketDateTimeData(): LocalDateTime? {
        return intent.extras?.getSerializableCompat<TicketDateUiModel>(TICKET_KEY)?.date
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> finish()
        }
        return super.onOptionsItemSelected(item)
    }

    companion object {
        private const val PEOPLE_COUNT_KEY = "peopleCount"
        private const val TICKET_KEY = "ticket"
        private const val MOVIE_KEY_VALUE = "movie"
        fun start(
            context: Context,
            peopleCount: Int,
            ticketDateUiModel: TicketDateUiModel,
            movieUiModel: MovieUiModel
        ) {
            val intent = Intent(context, SelectSeatActivity::class.java)
            intent.putExtra(PEOPLE_COUNT_KEY, peopleCount)
            intent.putExtra(TICKET_KEY, ticketDateUiModel)
            intent.putExtra(MOVIE_KEY_VALUE, movieUiModel)
            context.startActivity(intent)
        }
    }
}
