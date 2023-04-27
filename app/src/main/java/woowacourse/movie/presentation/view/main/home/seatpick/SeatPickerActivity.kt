package woowacourse.movie.presentation.view.main.home.seatpick

import android.app.AlarmManager
import android.app.PendingIntent
import android.app.PendingIntent.FLAG_IMMUTABLE
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.View
import android.widget.TableRow
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.Dimension
import androidx.appcompat.app.AlertDialog
import androidx.core.view.children
import com.example.domain.Reservation
import com.example.domain.ReservationRepository
import com.example.domain.Seat
import com.example.domain.SeatGrade
import com.example.domain.TicketBundle
import woowacourse.movie.Application
import woowacourse.movie.R
import woowacourse.movie.broadcast.AlarmReceiver
import woowacourse.movie.databinding.ActivitySeatPickerBinding
import woowacourse.movie.model.MovieBookingInfo
import woowacourse.movie.model.ReservationResult
import woowacourse.movie.presentation.extension.getParcelableCompat
import woowacourse.movie.presentation.view.common.BackButtonActivity
import woowacourse.movie.presentation.view.main.home.bookcomplete.BookCompleteActivity
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter

class SeatPickerActivity : BackButtonActivity() {
    private lateinit var binding: ActivitySeatPickerBinding
    private val ticketBundle = TicketBundle()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySeatPickerBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val movieBookingInfo: MovieBookingInfo? = intent.getParcelableCompat(
            MOVIE_BOOKING_INFO_SCHEDULE_INTENT_KEY
        )

        movieBookingInfo?.let { initView(it) } ?: processEmptyMovieData()
    }

    private fun initView(movieBookingInfo: MovieBookingInfo) {
        initBookingInfoView(movieBookingInfo)
        initSeatPickerView(movieBookingInfo)
        binding.tvSeatPickerConfirm.setOnClickListener {
            if (ticketBundle.tickets.size != movieBookingInfo.ticketCount) {
                Toast.makeText(
                    this,
                    getString(R.string.error_seat_picker_ticket_count_not_match).format(
                        movieBookingInfo.ticketCount
                    ),
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                showDialog(movieBookingInfo)
            }
        }
    }

    private fun initBookingInfoView(movieBookingInfo: MovieBookingInfo) {
        binding.tvSeatPickerMovieTitle.text = movieBookingInfo.movieInfo.title
        binding.tvSeatPickerTotalPrice.text = 0.toString()
    }

    private fun initSeatPickerView(movieBookingInfo: MovieBookingInfo) {
        binding.tbSeatPicker.children
            .filterIsInstance<TableRow>()
            .flatMap { it.children }
            .filterIsInstance<TextView>()
            .forEachIndexed { index, textView ->
                initTextViewOptions(index, textView)
                initSeatPickerClickListener(index, textView, movieBookingInfo)
            }
    }

    private fun initTextViewOptions(index: Int, textView: TextView) {
        val seat = Seat(index)
        textView.text = seat.getSeatName()
        textView.gravity = Gravity.CENTER
        textView.setTextSize(Dimension.SP, 22f)
        val textColor = when (seat.getSeatGrade()) {
            SeatGrade.S_CLASS -> Color.parseColor("#19D358")
            SeatGrade.A_CLASS -> Color.parseColor("#1B48E9")
            SeatGrade.B_CLASS -> Color.parseColor("#E9B21B")
            SeatGrade.NONE -> Color.BLACK
        }
        textView.setTextColor(textColor)
    }

    private fun initSeatPickerClickListener(
        seatIndex: Int,
        textView: TextView,
        movieBookingInfo: MovieBookingInfo,
    ) {
        textView.setOnClickListener {
            setSeatViewStatus(it, seatIndex, movieBookingInfo)

            binding.tvSeatPickerTotalPrice.text =
                ticketBundle.calculateTotalPrice(movieBookingInfo.date, movieBookingInfo.time)
                    .toString()
        }
    }

    private fun setSeatViewStatus(
        it: View,
        seatIndex: Int,
        movieBookingInfo: MovieBookingInfo
    ) {
        if (it.isSelected) {
            it.isSelected = false
            ticketBundle.popTicket(Seat(seatIndex))
            it.setBackgroundColor(Color.WHITE)
        } else if (!it.isSelected) {
            if (ticketBundle.tickets.size == movieBookingInfo.ticketCount) {
                Toast.makeText(
                    this,
                    getString(R.string.error_seat_picker_selected_max).format(movieBookingInfo.ticketCount),
                    Toast.LENGTH_SHORT
                ).show()
                return
            }
            it.isSelected = true
            ticketBundle.putTicket(Seat(seatIndex))
            it.setBackgroundColor(Color.parseColor("#FAFF00"))
        }
    }

    private fun showDialog(movieBookingInfo: MovieBookingInfo) {
        AlertDialog.Builder(this).apply {
            setTitle(getString(R.string.dialog_title_seat_picker_confirm))
            setMessage(getString(R.string.dialog_message_seat_picker_confirm))
            setCancelable(false)
            setPositiveButton(getString(R.string.dialog_positive_button_seat_picker_confirm)) { _, _ ->
                onClickDialogPositiveButton(movieBookingInfo)
            }
            setNegativeButton(getString(R.string.dialog_negative_button_seat_picker_confirm)) { dialog, _ ->
                dialog.dismiss()
            }
        }.show()
    }

    private fun onClickDialogPositiveButton(movieBookingInfo: MovieBookingInfo) {
        val reservation = saveReservation(movieBookingInfo)

        val sharedPref = Application.prefs
        val allowedPushNotification =
            sharedPref.getBoolean(getString(R.string.push_alarm_permission), true)
        if (allowedPushNotification)
            setAlarmManager(reservation)

        val intent =
            Intent(this@SeatPickerActivity, BookCompleteActivity::class.java).apply {
                putExtra(
                    BookCompleteActivity.RESERVATION_ID_INTENT_KEY,
                    reservation.id
                )
            }
        startActivity(intent)
    }

    private fun saveReservation(movieBookingInfo: MovieBookingInfo): Reservation {
        val reservation = Reservation(
            binding.tvSeatPickerTotalPrice.text.toString().toInt(),
            ticketBundle.tickets.size,
            ticketBundle.getSeatNames().joinToString(", "),
            movieBookingInfo.movieInfo.title,
            movieBookingInfo.date,
            movieBookingInfo.time
        )
        ReservationRepository.save(reservation)
        return reservation
    }

    private fun setAlarmManager(reservation: Reservation) {

        val alarmMgr: AlarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager

        val alarmIntent = createAlarmIntent(reservation)

        val screeningDateTime = LocalDateTime.of(
            LocalDate.parse(reservation.date, DateTimeFormatter.ISO_DATE),
            LocalTime.parse(reservation.time, DateTimeFormatter.ofPattern("H:mm"))
        )

        val notificationTime =
            screeningDateTime.atZone(ZoneId.of("Asia/Seoul")).toInstant().toEpochMilli() -
                    TIME_MILLS_OF_HALF_HOUR

        alarmMgr.set(
            AlarmManager.RTC_WAKEUP,
            notificationTime,
            alarmIntent
        )
        Log.d(
            "time_test",
            notificationTime.toString()
        )

    }

    private fun createAlarmIntent(reservation: Reservation): PendingIntent =
        Intent(this, AlarmReceiver::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }.let { intent ->
            intent.putExtra(BookCompleteActivity.RESERVATION_ID_INTENT_KEY, reservation.id)
            intent.putExtra(
                AlarmReceiver.RESERVATION_INTENT_KEY, ReservationResult.from(reservation)
            )
            PendingIntent.getBroadcast(this, 0, intent, FLAG_IMMUTABLE)
        }

    private fun processEmptyMovieData() {
        Toast.makeText(this, getString(R.string.error_intent_message), Toast.LENGTH_SHORT)
            .show()
        this.finish()
    }

    companion object {
        const val TIME_MILLS_OF_HALF_HOUR = (1000 * 60 * 30)
        const val MOVIE_BOOKING_INFO_SCHEDULE_INTENT_KEY = "MOVIE_BOOKING_INFO_SCHEDULE_KEY"
    }
}
