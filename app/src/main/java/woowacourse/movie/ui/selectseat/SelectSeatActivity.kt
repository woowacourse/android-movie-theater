package woowacourse.movie.ui.selectseat

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import domain.TicketOffice
import woowacourse.movie.R
import woowacourse.movie.data.model.SeatTable
import woowacourse.movie.data.model.SeatView
import woowacourse.movie.data.model.mapper.TicketsMapper
import woowacourse.movie.data.model.uimodel.MovieUiModel
import woowacourse.movie.data.model.uimodel.TicketDateUiModel
import woowacourse.movie.data.model.uimodel.TicketsUiModel
import woowacourse.movie.databinding.ActivitySelectSeatBinding
import woowacourse.movie.getSerializableCompat
import woowacourse.movie.notification.ReservationNotificationReceiver
import woowacourse.movie.setBackgroundColorId
import woowacourse.movie.ui.reservationresult.ReservationResultActivity
import java.time.LocalDateTime
import java.time.ZoneId

class SelectSeatActivity : AppCompatActivity(), SelectSeatContract.View {
    override lateinit var presenter: SelectSeatContract.Presenter

    private var _binding: ActivitySelectSeatBinding? = null
    private val binding
        get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        _binding = DataBindingUtil.setContentView(this, R.layout.activity_select_seat)

        setUpPresenter()
        setUpUiModels()
        setUpSeatTable()
        setUpOnClick()
    }

    private fun setUpPresenter() {
        presenter = SelectSeatPresenter(
            view = this,
            seatTable = SeatTable(
                tableLayout = binding.selectSeatTableLayout,
                rowSize = 5,
                colSize = 4,
                ::updateUi
            ),
            ticketOffice = TicketOffice(
                peopleCount = intent.getIntExtra(PEOPLE_COUNT_KEY, 1)
            )
        )
    }

    private fun setUpUiModels() {
        binding.movie =
            intent.extras?.getSerializableCompat(MOVIE_KEY_VALUE) ?: throw IllegalStateException()
        binding.ticketDate =
            intent.extras?.getSerializableCompat(TICKET_KEY) ?: throw IllegalStateException()
        binding.tickets = presenter.tickets
    }

    private fun setUpSeatTable() {
        presenter.setSeatTable()
    }

    private fun setUpOnClick() {
        binding.btnSelectSeat.run {
            setOnClickListener {
                val dialog = createReservationAlertDialog()
                dialog.setCanceledOnTouchOutside(false)
                dialog.show()
            }
            isEnabled = false
        }
    }

    override fun createReservationAlertDialog(): AlertDialog {
        val builder = AlertDialog.Builder(this)
        builder.setTitle(R.string.select_seat_dialog_title)
        builder.setMessage(R.string.select_seat_dialog_message)
        builder.setPositiveButton(R.string.select_seat_dialog_positive_button_text) { dialog, _ ->
            startActivity(
                ReservationResultActivity.getIntent(
                    this,
                    binding.movie!!,
                    TicketsMapper.toUi(presenter.tickets)
                )
            )
            registerAlarm()
        }
        builder.setNegativeButton(R.string.select_seat_dialog_negative_button_text) { dialog, _ ->
            dialog.dismiss()
        }
        return builder.create()
    }

    private fun registerAlarm() {
        val ticketsUiModel = TicketsMapper.toUi(presenter.tickets)
        val receiverIntent = generateReceiverIntent(ticketsUiModel)
        val pendingIntent =
            PendingIntent.getBroadcast(this, 125, receiverIntent, PendingIntent.FLAG_IMMUTABLE)
        val alarmManager: AlarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val timeMillis = getAlarmDateTime(binding.ticketDate!!.date, 30)
        alarmManager[AlarmManager.RTC, timeMillis] = pendingIntent
    }

    private fun generateReceiverIntent(ticketsUiModel: TicketsUiModel): Intent {
        val receiverIntent = Intent(this, ReservationNotificationReceiver::class.java)
        receiverIntent.putExtra("movie", binding.movie)
        return receiverIntent.putExtra("tickets", ticketsUiModel)
    }

    private fun getAlarmDateTime(localDateTime: LocalDateTime, minute: Long): Long {
        return localDateTime.atZone(ZoneId.systemDefault())
            .minusMinutes(minute)
            .toInstant().toEpochMilli()
    }

    private fun updateUi(seatView: SeatView) {
        changeSeatState(seatView)
        changeCheckButtonState()
        updateTicketView()
    }

    private fun changeSeatState(seatView: SeatView) {
        presenter.updateSeatState(seatView, binding.ticketDate!!.date)
    }

    override fun updateSeatView(seatView: SeatView, isSelected: Boolean) {
        seatView.view.isSelected = !isSelected
        when (isSelected) {
            true -> seatView.setBackgroundColorId(R.color.selected_seat_color)
            false -> seatView.setBackgroundColorId(R.color.white)
        }
    }

    private fun changeCheckButtonState() {
        presenter.updateButtonState(binding.btnSelectSeat)
    }

    override fun updateTicketView() {
        binding.tickets = presenter.tickets
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
        fun getIntent(
            context: Context,
            peopleCount: Int,
            ticketDateUiModel: TicketDateUiModel,
            movieUiModel: MovieUiModel
        ): Intent {
            val intent = Intent(context, SelectSeatActivity::class.java)
            intent.putExtra(PEOPLE_COUNT_KEY, peopleCount)
            intent.putExtra(TICKET_KEY, ticketDateUiModel)
            intent.putExtra(MOVIE_KEY_VALUE, movieUiModel)
            return intent
        }
    }
}
