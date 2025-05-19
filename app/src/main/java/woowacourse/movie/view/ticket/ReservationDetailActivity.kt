package woowacourse.movie.view.ticket

import android.Manifest
import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import woowacourse.movie.R
import woowacourse.movie.contract.ticket.ReservationDetailContract
import woowacourse.movie.data.ApplicationSettings
import woowacourse.movie.domain.reservation.Row
import woowacourse.movie.domain.reservation.Seat
import woowacourse.movie.domain.ticket.Reservation
import woowacourse.movie.presenter.ticket.ReservationDetailPresenter
import woowacourse.movie.view.reservation.ReservationAlarmReceiver
import woowacourse.movie.view.reservation.ShowAlarmPermissionInfoDialog
import woowacourse.movie.view.reservation.ShowNotificationPermissionInfoDialog
import woowacourse.movie.view.util.ErrorMessage
import java.time.LocalDateTime
import java.time.ZoneId

class ReservationDetailActivity :
    AppCompatActivity(),
    ReservationDetailContract.View {
    private val showNotificationPermissionInfoDialog: ShowNotificationPermissionInfoDialog by lazy {
        ShowNotificationPermissionInfoDialog(
            this,
        )
    }

    private val showAlarmPermissionInfoDialog: ShowAlarmPermissionInfoDialog by lazy {
        ShowAlarmPermissionInfoDialog(
            this,
        )
    }
    private var presenter: ReservationDetailContract.Presenter? = null

    private lateinit var cancelDescriptionView: TextView
    private lateinit var priceView: TextView
    private lateinit var descriptionView: TextView
    private lateinit var showtimeView: TextView
    private lateinit var titleView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_reservation_detail)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.layout_reservation_detail)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val reservation =
            intent?.getTicketExtra(EXTRA_TICKET) ?: error(ErrorMessage(CAUSE_TICKET).notProvided())

        findViews()
        initPresenter(reservation)
        requestNotificationPermission()
        initViews()
        setAlarmManager(reservation)
    }

    private fun requestNotificationPermission() {
        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.POST_NOTIFICATIONS,
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            showNotificationPermissionInfoDialog(onDismiss = {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                    if (shouldShowRequestPermissionRationale(Manifest.permission.POST_NOTIFICATIONS)) {
                        // 권한 요청 거부한 경우
                    } else {
                        requestPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
                    }
                } else {
                    // 안드로이드 12 이하는 Notification에 관한 권한 필요 없음
                }
            })
        }
    }

    private val requestPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted: Boolean ->
            ApplicationSettings.notificationEnabled = isGranted

            val alarmManager = getSystemService(ALARM_SERVICE) as AlarmManager

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                checkSettingAlarmPermission(alarmManager)
            }
        }

    private fun checkSettingAlarmPermission(alarmManager: AlarmManager) {
        if (!alarmManager.canScheduleExactAlarms()) {
            showAlarmPermissionInfoDialog(onDismiss = {
                startActivity(Intent(Settings.ACTION_REQUEST_SCHEDULE_EXACT_ALARM))
            })
        }
    }

    private fun setAlarmManager(reservation: Reservation) {
        val pendingIntent = pendingIntent(reservation)
        val alarmManager = getSystemService(ALARM_SERVICE) as AlarmManager

        if (alarmManager.canScheduleExactAlarms()) {
            alarmManager.setExactAndAllowWhileIdle(
                AlarmManager.RTC_WAKEUP,
                reservation.showtime
                    .minusMinutes(30)
                    .atZone(ZoneId.systemDefault())
                    .toEpochSecond() * 1_000,
                pendingIntent,
            )
        }
    }

    private fun pendingIntent(reservation: Reservation): PendingIntent {
        val intent = ReservationAlarmReceiver.newIntent(this, reservation)
        return PendingIntent.getBroadcast(this, 0, intent, PendingIntent.FLAG_IMMUTABLE)
    }

    private fun initPresenter(reservation: Reservation) {
        presenter = ReservationDetailPresenter(this, reservation)
    }

    private fun findViews() {
        cancelDescriptionView = findViewById<TextView>(R.id.tv_ticket_cancel_description)
        priceView = findViewById<TextView>(R.id.tv_ticket_price)
        descriptionView = findViewById<TextView>(R.id.tv_ticket_description)
        showtimeView = findViewById<TextView>(R.id.tv_ticket_showtime)
        titleView = findViewById<TextView>(R.id.tv_ticket_movie_title)
    }

    @Suppress("DEPRECATION")
    private fun Intent.getTicketExtra(key: String): Reservation? =
        when {
            Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU ->
                getSerializableExtra(
                    key,
                    Reservation::class.java,
                )

            else -> getSerializableExtra(key) as? Reservation
        }

    private fun initViews() {
        (presenter ?: error(ErrorMessage(CAUSE_TICKET).notProvided())).run {
            presentCancelDescription()
            presentTitle()
            presentShowtime()
            presentCount()
            presentPrice()
        }
    }

    override fun setCancelDescription(minutes: Int) {
        cancelDescriptionView.text =
            getString(
                R.string.ticket_cancel_time_description,
                minutes,
            )
    }

    override fun setMovieTitle(movieTitle: String) {
        titleView.text = movieTitle
    }

    override fun setShowtime(showtime: LocalDateTime) {
        showtimeView.text =
            showtime.run {
                getString(R.string.ticket_showtime, year, monthValue, dayOfMonth, hour, minute)
            }
    }

    override fun setCount(
        seats: Set<Seat>,
        cinemaName: String,
    ) {
        descriptionView.text =
            getString(
                R.string.ticket_description,
                seats.size,
                seats.map { it.prettyString }.sorted().joinToString(),
                cinemaName,
            )
    }

    private val Seat.prettyString: String get() = "${row.prettyString}${column.value}"

    private val Row.prettyString: String get() = ('A' + this.value - 1).toString()

    override fun setPrice(price: Int) {
        priceView.text = getString(R.string.ticket_price, price)
    }

    companion object {
        private const val CAUSE_TICKET = "ticket"

        private const val EXTRA_TICKET = "woowacourse.movie.EXTRA_TICKET"

        fun newIntent(
            context: Context,
            reservation: Reservation,
        ): Intent =
            run {
                Intent(context, ReservationDetailActivity::class.java)
                    .putExtra(EXTRA_TICKET, reservation)
            }
    }
}
