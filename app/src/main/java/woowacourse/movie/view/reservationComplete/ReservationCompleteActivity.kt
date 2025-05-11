package woowacourse.movie.view.reservationComplete

import android.Manifest
import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import androidx.activity.OnBackPressedCallback
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresPermission
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.databinding.DataBindingUtil
import woowacourse.movie.MainActivity
import woowacourse.movie.R
import woowacourse.movie.databinding.ActivityReservationCompleteBinding
import woowacourse.movie.model.ticket.MovieTicket
import woowacourse.movie.presenter.reservationComplete.ReservationCompleteContracts
import woowacourse.movie.presenter.reservationComplete.ReservationCompletePresenter
import woowacourse.movie.view.extension.dialogMessage
import woowacourse.movie.view.extension.getSerializableExtraData
import woowacourse.movie.view.setting.AlarmReceiver
import java.time.LocalDateTime
import java.time.ZoneId

class ReservationCompleteActivity :
    androidx.appcompat.app.AppCompatActivity(),
    ReservationCompleteContracts.View {
    private val presenter: ReservationCompleteContracts.Presenter =
        ReservationCompletePresenter(this)
    private lateinit var binding: ActivityReservationCompleteBinding

    @RequiresPermission(Manifest.permission.SCHEDULE_EXACT_ALARM)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = DataBindingUtil.setContentView(this, R.layout.activity_reservation_complete)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.reservation_complete)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val intentMovieTicketData: MovieTicket? =
            intent.getSerializableExtraData<MovieTicket>(TICKET_DATA_KEY)
        if (intentMovieTicketData == null) {
            presenter.requestErrorDialogMessage()
            return
        }
        presenter.updateTicketData(intentMovieTicketData)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        setupBackPressedDispatcher()
        scheduleNotification(
            this,
            intentMovieTicketData,
        )
    }

    private fun setupBackPressedDispatcher() {
        onBackPressedDispatcher.addCallback(
            this,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    startActivity(MainActivity.getIntent(this@ReservationCompleteActivity))
                    finish()
                }
            },
        )
    }

    override fun onSupportNavigateUp(): Boolean {
        startActivity(MainActivity.getIntent(this))
        finish()
        return super.onSupportNavigateUp()
    }

    override fun showMovieTicket(movieTicket: MovieTicket) {
        binding.movieTicket = movieTicket
    }

    override fun showErrorDialogMessage() {
        dialogMessage(this, R.string.not_found_data_error_message)
    }

    @RequiresPermission(Manifest.permission.SCHEDULE_EXACT_ALARM)
    private fun scheduleNotification(
        context: Context,
        movieTicket: MovieTicket,
    ) {
        val prefs = context.getSharedPreferences("setting", MODE_PRIVATE)
        if (!prefs.getBoolean("push_enabled", false)) return

        val triggerTime = movieTicket.selectedTime.value.minusMinutes(30)
        val triggerDateTime: LocalDateTime = LocalDateTime.of(movieTicket.selectedDate, triggerTime)
        val zoneId = ZoneId.of("Asia/Seoul")
        val triggerTimeMillis = triggerDateTime.atZone(zoneId).toInstant().toEpochMilli()

        val intent =
            Intent(context, AlarmReceiver::class.java).apply {
                action = "movie_alarm"
                putExtra("movie_title", movieTicket.title)
            }

        val pendingIntent =
            PendingIntent.getBroadcast(
                context,
                movieTicket.title.hashCode(),
                intent,
                PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE,
            )
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            val alarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager
            if (!alarmManager.canScheduleExactAlarms()) {
                val intent = Intent(Settings.ACTION_REQUEST_SCHEDULE_EXACT_ALARM)
                startActivity(intent)
            } else {
                alarmManager.setExact(
                    AlarmManager.RTC_WAKEUP,
                    triggerTimeMillis,
                    pendingIntent,
                )
            }
        }
    }

    companion object {
        const val TICKET_DATA_KEY = "movieTicket"

        fun getIntent(
            context: Context,
            movieTicket: MovieTicket,
        ): Intent =
            Intent(context, ReservationCompleteActivity::class.java).apply {
                putExtra(TICKET_DATA_KEY, movieTicket)
            }
    }
}
