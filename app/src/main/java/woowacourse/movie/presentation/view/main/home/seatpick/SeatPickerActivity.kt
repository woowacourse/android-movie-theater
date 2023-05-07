package woowacourse.movie.presentation.view.main.home.seatpick

import android.app.AlarmManager
import android.app.PendingIntent
import android.app.PendingIntent.FLAG_IMMUTABLE
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.Gravity
import android.widget.TableRow
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.Dimension
import androidx.appcompat.app.AlertDialog
import androidx.core.view.children
import androidx.databinding.DataBindingUtil
import woowacourse.movie.R
import woowacourse.movie.broadcast.bookingnotificaiotn.BookingAlarmReceiver
import woowacourse.movie.databinding.ActivitySeatPickerBinding
import woowacourse.movie.presentation.extension.getParcelableCompat
import woowacourse.movie.presentation.model.ReservationResult
import woowacourse.movie.presentation.view.common.BackButtonActivity
import woowacourse.movie.presentation.view.main.home.bookcomplete.BookCompleteActivity
import woowacourse.movie.presentation.view.main.home.seatpick.model.SeatGradeModel

class SeatPickerActivity : BackButtonActivity(), SeatPickerContract.View {
    private lateinit var binding: ActivitySeatPickerBinding
    private val presenter: SeatPickerContract.Presenter by lazy {
        SeatPickerPresenter(
            view = this,
            context = this,
            movieBookingInfo = intent.getParcelableCompat(
                MOVIE_BOOKING_INFO_SCHEDULE_INTENT_KEY
            )
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_seat_picker)

        presenter.onCreate()
    }

    override fun initView(movieTitle: String) {
        initBookingInfoView(movieTitle)
        initSeatPickerView()
        setConfirmView()
    }

    override fun finishErrorView() {
        Toast.makeText(this, getString(R.string.error_intent_message), Toast.LENGTH_SHORT)
            .show()
        this.finish()
    }

    private fun initBookingInfoView(movieTitle: String) {
        binding.tvSeatPickerMovieTitle.text =
            movieTitle
        presenter.getDefaultTotalPrice()

    }

    override fun updateDefaultTotalPriceView(value: Int) {
        binding.tvSeatPickerTotalPrice.text = value.toString()
    }

    private fun setConfirmView(
    ) {
        binding.tvSeatPickerConfirm.setOnClickListener {
            presenter.confirmBooking()
        }
    }

    override fun showTicketIsMaxCountView(ticketCount: Int) {
        Toast.makeText(
            this,
            getString(R.string.error_seat_picker_ticket_count_not_match).format(
                ticketCount
            ),
            Toast.LENGTH_SHORT
        ).show()
    }

    override fun showConfirmDialog() {
        AlertDialog.Builder(this).apply {
            setTitle(getString(R.string.dialog_title_seat_picker_confirm))
            setMessage(getString(R.string.dialog_message_seat_picker_confirm))
            setCancelable(false)
            setPositiveButton(getString(R.string.dialog_positive_button_seat_picker_confirm)) { _, _ ->
                presenter.bookComplete()
            }
            setNegativeButton(getString(R.string.dialog_negative_button_seat_picker_confirm)) { dialog, _ ->
                dialog.dismiss()
            }
        }.show()
    }

    override fun updateNotification(
        reservationResult: ReservationResult,
        notificationTimeStamp: Long
    ) {
        val alarmManager: AlarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val alarmIntent = createAlarmIntent(reservationResult)

        alarmManager.set(
            AlarmManager.RTC_WAKEUP,
            notificationTimeStamp,
            alarmIntent
        )
    }

    private fun createAlarmIntent(reservation: ReservationResult): PendingIntent =
        Intent(this, BookingAlarmReceiver::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }.let { intent ->
            intent.putExtra(BookCompleteActivity.RESERVATION_ID_INTENT_KEY, reservation)
            intent.putExtra(
                BookingAlarmReceiver.RESERVATION_INTENT_KEY, reservation
            )
            PendingIntent.getBroadcast(this, 0, intent, FLAG_IMMUTABLE)
        }

    override fun showBookCompleteView(reservation: ReservationResult) {
        val intent = BookCompleteActivity.getIntent(this).apply {
            putExtra(
                BookCompleteActivity.RESERVATION_ID_INTENT_KEY,
                reservation
            )
        }
        startActivity(intent)
    }

    private fun initSeatPickerView() {
        binding.tbSeatPicker.children
            .filterIsInstance<TableRow>()
            .flatMap { it.children }
            .filterIsInstance<TextView>()
            .forEachIndexed { index, textView ->
                val seatModel = presenter.setSeatInfo(index)
                textView.setSeatInfo(seatModel.seatName, seatModel.seatGrade)

                textView.setSeatPickerClickListener(index)
            }
    }

    private fun TextView.setSeatInfo(seatName: String, seatGrade: SeatGradeModel) {
        text = seatName
        gravity = Gravity.CENTER
        background = getDrawable(R.drawable.selector_seat_view)
        setTextSize(Dimension.SP, 22f)

        val textColor = Color.parseColor(seatGrade.seatColor)
        setTextColor(textColor)
    }

    private fun TextView.setSeatPickerClickListener(
        seatIndex: Int
    ) {
        setOnClickListener {
            val isSelected = presenter.updateSeatStatus(it.isSelected, seatIndex)
            it.isSelected = isSelected

            presenter.calculateTotalPrice()
        }
    }

    override fun updateTotalPriceView(totalPrice: Int) {
        binding.tvSeatPickerTotalPrice.text = totalPrice.toString()
    }

    companion object {
        const val MOVIE_BOOKING_INFO_SCHEDULE_INTENT_KEY = "MOVIE_BOOKING_INFO_SCHEDULE_KEY"
        fun getIntent(context: Context): Intent {
            return Intent(context, SeatPickerActivity::class.java)
        }
    }
}
