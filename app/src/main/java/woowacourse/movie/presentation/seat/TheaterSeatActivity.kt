package woowacourse.movie.presentation.seat

import android.annotation.SuppressLint
import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.MenuItem
import android.widget.Button
import android.widget.TableRow
import androidx.appcompat.app.AlertDialog
import androidx.core.content.IntentCompat
import androidx.core.view.children
import woowacourse.movie.MovieBroadCastReceiver.Companion.RESERVATION_NOTIFICATION_ACTION
import woowacourse.movie.R
import woowacourse.movie.data.MovieRepositoryInjector
import woowacourse.movie.databinding.ActivityTheaterSeatBinding
import woowacourse.movie.model.Cinema
import woowacourse.movie.model.Reservation
import woowacourse.movie.model.movieInfo.Title
import woowacourse.movie.model.theater.Seat
import woowacourse.movie.presentation.base.BindingActivity
import woowacourse.movie.presentation.error.ErrorActivity
import woowacourse.movie.presentation.purchaseConfirmation.PurchaseConfirmationActivity
import java.time.LocalDateTime
import java.time.ZoneId
import kotlin.concurrent.thread

@SuppressLint("DiscouragedApi")
class TheaterSeatActivity :
    BindingActivity<ActivityTheaterSeatBinding>(R.layout.activity_theater_seat),
    TheaterSeatContract.View {
    private lateinit var presenter: TheaterSeatPresenter
    private val dialog: AlertDialog by lazy {
        AlertDialog.Builder(this)
            .setTitle("예매 확인")
            .setMessage("예매를 완료하시겠습니까?")
            .setCancelable(false)
            .setPositiveButton("확인") { _, _ ->
                thread {
                    presenter.confirmPurchase()
                }.join()
            }
            .setNegativeButton("취소") { dialog, _ ->
                dialog.dismiss()
            }.create()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        setContentView(binding.root)
        initPresenter()
        initSeats()

        binding.confirmButton.setOnClickListener {
            presenter.completeSeatSelection()
        }
    }

    override fun updateSeatDisplay(seat: Seat) {
        val buttonId = resources.getIdentifier("${seat.row}${seat.number}", "id", packageName)
        val button = findViewById<Button>(buttonId)
        val color = if (seat.chosen) Color.RED else Color.WHITE
        button.setBackgroundColor(color)
    }

    override fun showConfirmationDialog() {
        dialog.show()
    }

    override fun setSeatBackground(
        seatId: String,
        color: String,
    ) {
        val buttonId = resources.getIdentifier(seatId, "id", packageName)
        val button = findViewById<Button>(buttonId)
        val colorInt = Color.parseColor(color)
        button.setBackgroundColor(colorInt)
    }

    override fun navigateToPurchaseConfirmView(reservation: Reservation) {
        postReservationAlarm(reservation)
        PurchaseConfirmationActivity.newIntent(this, reservation.id).also {
            startActivity(it)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        finish()
        return true
    }

    override fun showTitle(title: Title) {
        binding.title = title.toString()
        binding.invalidateAll()
    }

    override fun showPrice(price: Int) {
        binding.price = price
        binding.invalidateAll()
    }

    override fun showError() {
        ErrorActivity.start(this)
        finish()
    }

    private fun initPresenter() {
        val ticketNum = intent.getStringExtra(EXTRA_TICKET_NUM)
        val dateTime = intent.getStringExtra(EXTRA_TIME_DATE)
        val cinema = IntentCompat.getSerializableExtra(intent, EXTRA_CINEMA, Cinema::class.java)
        if (cinema == null || ticketNum == null || dateTime == null) {
            ErrorActivity.start(this)
            return finish()
        }
        presenter =
            TheaterSeatPresenter(
                MovieRepositoryInjector.instance(applicationContext).movieRepository(),
                this,
                ticketNum.toInt(),
                dateTime,
                cinema,
            )
    }

    private fun initSeats() {
        binding.seatTable.children.filterIsInstance<TableRow>()
            .forEach { row ->
                row.children.filterIsInstance<Button>()
                    .forEach { button ->
                        button.setOnClickListener {
                            presenter.toggleSeatSelection(button.text.toString())
                        }
                    }
            }
    }

    private fun postReservationAlarm(reservation: Reservation) {
        val movieTitle: String = reservation.title.name
        val milliTimes =
            reservation.releaseDate.minusMinutes(ALARM_OFFSET).toMilliSeconds()

        val alarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager

        val pendingIntent =
            Intent(RESERVATION_NOTIFICATION_ACTION).apply {
                putExtra("movieTitle", movieTitle)
                putExtra("reservationId", reservation.id)
            }.let {
                PendingIntent.getBroadcast(
                    this,
                    0,
                    it,
                    PendingIntent.FLAG_IMMUTABLE,
                )
            }

        val alarmClockInfo =
            AlarmManager.AlarmClockInfo(
                milliTimes,
                pendingIntent,
            )
        alarmManager.setAlarmClock(alarmClockInfo, pendingIntent)
    }

    private fun LocalDateTime.toMilliSeconds(): Long {
        return atZone(ZoneId.systemDefault()).toInstant().toEpochMilli()
    }

    companion object {
        const val ALARM_OFFSET = 30L
        const val EXTRA_TIME_DATE = "timeDate"
        const val EXTRA_TICKET_NUM = "ticketNum"
        const val EXTRA_CINEMA = "cinema"

        fun newIntent(
            context: Context,
            ticketNum: String,
            cinema: Cinema,
            timeDate: String,
        ): Intent {
            return Intent(
                context,
                TheaterSeatActivity::class.java,
            ).apply {
                putExtra(EXTRA_TICKET_NUM, ticketNum)
                putExtra(EXTRA_CINEMA, cinema)
                putExtra(EXTRA_TIME_DATE, timeDate)
            }
        }
    }
}
