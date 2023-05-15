package woowacourse.movie.view.activities.seatselection

import android.app.AlarmManager
import android.app.PendingIntent
import android.app.PendingIntent.FLAG_IMMUTABLE
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.widget.Button
import android.widget.TableLayout
import android.widget.TableRow
import android.widget.TextView
import androidx.annotation.ColorRes
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.AppCompatButton
import androidx.appcompat.widget.Toolbar
import woowacourse.movie.R
import woowacourse.movie.data.reservation.ReservationDbHelper
import woowacourse.movie.data.reservation.ReservationRepositoryImpl
import woowacourse.movie.repository.Screening1Repository
import woowacourse.movie.repository.TheaterRepository
import woowacourse.movie.view.activities.common.BackButtonActivity
import woowacourse.movie.view.activities.reservationresult.ReservationResultActivity
import woowacourse.movie.view.broadcast.AlarmReceiver
import woowacourse.movie.view.broadcast.AlarmReceiver.Companion.RESERVATION_ID
import woowacourse.movie.view.utils.getPushAlarmReceptionIsWanted
import java.time.LocalDateTime
import java.time.ZoneId
import kotlin.properties.Delegates

class SeatSelectionActivity : BackButtonActivity(), SeatSelectionContract.View {

    private val presenter: SeatSelectionContract.Presenter by lazy {
        SeatSelectionPresenter(
            this,
            intent.getLongExtra(SCREENING_ID, -1),
            selectedScreeningDateTime,
            intent.getLongExtra(THEATER_ID, -1),
            Screening1Repository,
            TheaterRepository,
            ReservationRepositoryImpl(ReservationDbHelper.getDbInstance(this))
        )
    }

    private val selectedScreeningDateTime: LocalDateTime by lazy {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            intent.getSerializableExtra(SCREENING_DATE_TIME, LocalDateTime::class.java)
                ?: throw IllegalArgumentException("이 액티비티는 인텐트에 상영 시각이 저장되어 있을 때만 실행될 수 있습니다.")
        } else {
            intent.getSerializableExtra(SCREENING_DATE_TIME) as LocalDateTime
        }
    }

    private var selectedSeatNames: Set<String> by Delegates.observable(setOf()) { _, _, new ->
        presenter.setSelectedSeats(new)
        findViewById<Button>(R.id.reservation_btn).isEnabled =
            new.size == intent.getIntExtra(AUDIENCE_COUNT, 1)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_seat_selection)

        presenter.loadScreening()
        initReservationButtonOnClickListener()
    }

    private fun initReservationButtonOnClickListener() {
        val reservationButton = findViewById<Button>(R.id.reservation_btn)
        reservationButton.setOnClickListener { showDialogAskingIfYouWantToMakeReservation() }
    }

    private fun showDialogAskingIfYouWantToMakeReservation() {
        AlertDialog.Builder(this).apply {
            setTitle(getString(R.string.reservation_dialog_title))
            setMessage(getString(R.string.reservation_dialog_message))
            setPositiveButton(getString(R.string.confirm_reservation)) { _, _ ->
                presenter.reserve(selectedSeatNames)
            }
            setNegativeButton(getString(R.string.cancel)) { _, _ -> }
            setCancelable(false)
        }
            .create()
            .show()
    }

    override fun setSeats(seatUIStates: SeatsUIState) {
        val seatsView = findViewById<TableLayout>(R.id.seat_table)
        seatUIStates.seats.forEach {
            val tableRow = TableRow(this).apply {
                layoutParams = TableLayout.LayoutParams(0, 0, 1f)
            }
            it.forEach { seatUIState ->
                tableRow.addView(createSeatUI(seatUIState.seatName, seatUIState.textColor))
            }
            seatsView.addView(tableRow)
        }
    }

    private fun createSeatUI(seatName: String, @ColorRes textColor: Int): AppCompatButton =
        AppCompatButton(this).apply {
            text = seatName
            setTextColor(getColor(textColor))
            setBackgroundColor(getColor(R.color.unselected_seat_color))
            setOnClickListener { onSeatButtonClick(this) }
            layoutParams = TableRow.LayoutParams(0, Toolbar.LayoutParams.MATCH_PARENT, 1f)
        }

    private fun onSeatButtonClick(button: AppCompatButton) {
        fun deselect(button: AppCompatButton) {
            button.isSelected = false
            button.setBackgroundColor(getColor(R.color.unselected_seat_color))
            selectedSeatNames = selectedSeatNames - button.text.toString()
        }

        fun select(button: AppCompatButton) {
            button.isSelected = true
            button.setBackgroundColor(getColor(R.color.selected_seat_color))
            selectedSeatNames = selectedSeatNames + button.text.toString()
        }

        if (button.isSelected) deselect(button)
        else select(button)
    }

    override fun setMovieTitle(title: String) {
        val titleView = findViewById<TextView>(R.id.movie_title_tv)
        titleView.text = title
    }

    override fun setReservationFee(fee: Int) {
        val reservationFeeView = findViewById<TextView>(R.id.reservation_fee_tv)
        reservationFeeView.text = getString(R.string.fee_format).format(fee)
    }

    override fun setReservation(reservationId: Long) {
        if (getPushAlarmReceptionIsWanted(this)) {
            sendNotificationAboutReservationAt(
                selectedScreeningDateTime.minusMinutes(BEFORE_SCREENING_TIME),
                reservationId
            )
        }

        ReservationResultActivity.startActivity(this, reservationId)
    }

    private fun sendNotificationAboutReservationAt(dateTime: LocalDateTime, reservationId: Long) {
        val alarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager

        val alarmIntent = createAlarmIntent(reservationId)

        val notificationTime = dateTime.atZone(ZoneId.of("Asia/Seoul")).toInstant().toEpochMilli()

        alarmManager.set(AlarmManager.RTC_WAKEUP, notificationTime, alarmIntent)
    }

    private fun createAlarmIntent(reservationId: Long): PendingIntent =
        Intent(this, AlarmReceiver::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            putExtra(RESERVATION_ID, reservationId)
        }.let {
            PendingIntent.getBroadcast(this, 0, it, FLAG_IMMUTABLE)
        }

    companion object {
        const val SCREENING_ID = "SCREENING_ID"
        const val SCREENING_DATE_TIME = "SCREENING_DATE_TIME"
        const val AUDIENCE_COUNT = "AUDIENCE_COUNT"
        const val THEATER_ID = "THEATER_ID"
        private const val BEFORE_SCREENING_TIME = 30L

        fun startActivity(
            context: Context,
            screeningId: Long,
            screeningDateTime: LocalDateTime,
            selectedAudienceCount: Int,
            theaterId: Long
        ) {
            val intent = Intent(context, SeatSelectionActivity::class.java).apply {
                putExtra(SCREENING_ID, screeningId)
                putExtra(SCREENING_DATE_TIME, screeningDateTime)
                putExtra(AUDIENCE_COUNT, selectedAudienceCount)
                putExtra(THEATER_ID, theaterId)
            }
            context.startActivity(intent)
        }
    }
}
