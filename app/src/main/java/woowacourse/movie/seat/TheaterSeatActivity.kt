package woowacourse.movie.seat

import android.app.AlarmManager
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.provider.Settings
import android.view.MenuItem
import android.widget.Button
import android.widget.TableRow
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.app.AlarmManagerCompat
import androidx.core.content.IntentCompat
import androidx.core.view.children
import androidx.room.Room
import woowacourse.movie.R
import woowacourse.movie.base.BindingActivity
import woowacourse.movie.database.AppDatabase
import woowacourse.movie.databinding.ActivityTheaterSeatBinding
import woowacourse.movie.error.ErrorActivity
import woowacourse.movie.model.Cinema
import woowacourse.movie.model.movieInfo.Title
import woowacourse.movie.model.theater.Seat
import woowacourse.movie.notification.NotificationReceiver
import woowacourse.movie.purchaseConfirmation.PurchaseConfirmationActivity
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.Locale

class TheaterSeatActivity :
    BindingActivity<ActivityTheaterSeatBinding>(R.layout.activity_theater_seat),
    TheaterSeatContract.View {
    private lateinit var presenter: TheaterSeatPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        setContentView(binding.root)
        initPresenter()
        initSeats()

        val channelName = "Ticket Confirmation"
        val channelId = "ticket_confirmation_channel"
        val channelDescription = "Notifications for ticket confirmations"
        val importance = NotificationManager.IMPORTANCE_DEFAULT
        val channel =
            NotificationChannel(channelId, channelName, importance).apply {
                description = channelDescription
            }
        val notificationManager: NotificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(channel)

        binding.confirmButton.setOnClickListener {
            confirmTicketPurchase()
        }
    }

    override fun updateSeatDisplay(seat: Seat) {
        val buttonId = resources.getIdentifier("${seat.row}${seat.number}", "id", packageName)
        val button = findViewById<Button>(buttonId)
        val color = if (seat.chosen) Color.RED else Color.WHITE
        button.setBackgroundColor(color)
    }

    override fun showConfirmationDialog(
        title: String,
        message: String,
        positiveLabel: String,
        onPositiveButtonClicked: () -> Unit,
        negativeLabel: String,
        onNegativeButtonClicked: () -> Unit,
    ) {
        val builder = AlertDialog.Builder(this)
        builder.setTitle(title)
        builder.setMessage(message)
        builder.setCancelable(false)
        builder.setPositiveButton(positiveLabel) { _, _ -> onPositiveButtonClicked() }
        builder.setNegativeButton(negativeLabel) { dialog, _ ->
            onNegativeButtonClicked()
            dialog.dismiss()
        }
        builder.show()
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

    override fun navigateToNextPage(intent: Intent) {
        startActivity(intent)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        finish()
        return true
    }

    override fun showTitle(title: Title) {
        binding.title = title.toString()
        binding.executePendingBindings()
    }

    override fun showPrice(price: Int) {
        binding.price = price
        binding.executePendingBindings()
    }

    private fun initPresenter() {
        val ticketNum = intent.getStringExtra(EXTRA_TICKET_NUM) ?: return ErrorActivity.start(this)
        val cinema = IntentCompat.getSerializableExtra(intent, EXTRA_CINEMA, Cinema::class.java)
        val showTime = intent.getStringExtra(EXTRA_TIME_DATE) ?: return ErrorActivity.start(this)
        if (cinema == null) {
            ErrorActivity.start(this)
            return finish()
        }
        val database = Room.databaseBuilder(this, AppDatabase::class.java, "ticket").build()
        presenter = TheaterSeatPresenter(this, database, showTime, ticketNum.toInt(), cinema)
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

    private fun confirmTicketPurchase() {
        showConfirmationDialog(
            title = "예매 확인",
            message = "정말 예매하시겠습니까?",
            positiveLabel = "예매 완료",
            onPositiveButtonClicked = onPositiveButtonClicked@{
                val cinema =
                    IntentCompat.getSerializableExtra(intent, EXTRA_CINEMA, Cinema::class.java)
                val ticketPrice = findViewById<TextView>(R.id.total_price).text
                if (cinema != null) {
                    val timeDate = intent.getStringExtra(EXTRA_TIME_DATE)!!
                    val formatter = SimpleDateFormat("yyyy.MM.dd HH:mm", Locale.KOREA)
                    try {
                        val date = formatter.parse(timeDate)
                        val movieStartTime = date?.time ?: return@onPositiveButtonClicked
                        presenter.saveTicketToDatabase { ticketId ->
                            scheduleNotification(
                                this,
                                movieStartTime,
                                cinema,
                                ticketPrice.toString()
                            )
                            val confirmationIntent = PurchaseConfirmationActivity.newIntent(
                                context = this,
                                ticketId = ticketId
                            )
                            navigateToNextPage(confirmationIntent)
                        }
                    } catch (e: ParseException) {
                        Toast.makeText(this, "예매 시간 형식이 잘못되었습니다.", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    Toast.makeText(this, "Cinema data is not available.", Toast.LENGTH_SHORT)
                        .show()
                }
            },
            negativeLabel = "취소",
            onNegativeButtonClicked = {}
        )
    }


    private fun scheduleNotification(
        context: Context,
        movieStartTime: Long,
        cinema: Cinema,
        ticketPrice: String,
    ) {
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        try {
            if (AlarmManagerCompat.canScheduleExactAlarms(alarmManager)) {
                val alarmTime = movieStartTime - 30 * 60 * 1000 // 영화 시작 시간 30분 전
                val intent = Intent(context, NotificationReceiver::class.java).apply {
                    putExtra("notificationId", 1001)
                    putExtra("message", "${cinema.theater.movie.title} 영화 시작 30분 전입니다!")
                    putExtra("title", cinema.theater.movie.title.toString())
                    putExtra("cinemaName", cinema.cinemaName)
                    putExtra("ticketPrice", ticketPrice)
                    putExtra("seatNumber", presenter.selectedSeats.toTypedArray())
                    putExtra("runningTime", cinema.theater.movie.runningTime.toString())
                    putExtra("timeDate", intent.getStringExtra(EXTRA_TIME_DATE))
                }
                val pendingIntent = PendingIntent.getBroadcast(
                    context,
                    1001,
                    intent,
                    PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE,
                )

                alarmManager.setExactAndAllowWhileIdle(
                    AlarmManager.RTC_WAKEUP,
                    alarmTime,
                    pendingIntent,
                )
            } else {
                val settingsIntent = Intent(Settings.ACTION_REQUEST_SCHEDULE_EXACT_ALARM)
                context.startActivity(settingsIntent)
            }
        } catch (e: SecurityException) {
            Toast.makeText(context, "정확한 알람 스케줄링 권한이 필요합니다.", Toast.LENGTH_LONG).show()
            val settingsIntent = Intent(Settings.ACTION_REQUEST_SCHEDULE_EXACT_ALARM)
            context.startActivity(settingsIntent)
        }
    }


    companion object {
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
