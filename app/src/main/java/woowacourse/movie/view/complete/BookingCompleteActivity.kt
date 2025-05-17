package woowacourse.movie.view.complete

import android.app.AlarmManager
import android.app.AlertDialog
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.Intent.FLAG_ACTIVITY_CLEAR_TOP
import android.content.Intent.FLAG_ACTIVITY_SINGLE_TOP
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.view.MenuItem
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.net.toUri
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.databinding.DataBindingUtil
import woowacourse.movie.R
import woowacourse.movie.databinding.ActivityBookingCompleteBinding
import woowacourse.movie.domain.model.Ticket
import woowacourse.movie.view.core.ext.showToast
import woowacourse.movie.view.main.MainActivity
import woowacourse.movie.view.receiver.AlarmReceiver
import woowacourse.movie.view.core.ext.requireSerializable
import woowacourse.movie.view.uiModel.toUiModel

class BookingCompleteActivity : AppCompatActivity(), BookingCompleteContract.View {
    private lateinit var binding: ActivityBookingCompleteBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = DataBindingUtil.setContentView(this, R.layout.activity_booking_complete)

        val ticketId = intent.getLongExtra(KEY_TICKET_ID, 0)
        val isFromSeatScreen = intent.getStringExtra(KEY_FROM) != null

        val presenter = BookingCompletePresenter.initialize(this, applicationContext)
        presenter.loadTicket(ticketId, isFromSeatScreen)
        intent.requireSerializable<Ticket>(KEY_TICKET).apply {
            presenter = BookingCompletePresenter(this@BookingCompleteActivity, this)
        }

        initView()
    }

    private fun initView() {
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                val intent =
                    Intent(this, MainActivity::class.java).apply {
                        addFlags(FLAG_ACTIVITY_CLEAR_TOP)
                        addFlags(FLAG_ACTIVITY_SINGLE_TOP)
                    }
                startActivity(intent)
                finish()
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun showTicket(ticket: Ticket) {
        binding.model =
            ticket.toCompleteScreen(
            ticket.toUiModel(
                getString(R.string.formatter_booking_schedule),
                getString(R.string.formatter_text_seat_formatter),
                getString(R.string.formatter_general_people_count),
                getString(R.string.formatter_on_site_payment),
            )
    }

    override fun generateAlarm(ticket: Ticket) =
        runOnUiThread {
            val alarmManager = getSystemService(ALARM_SERVICE) as AlarmManager

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                if (!alarmManager.canScheduleExactAlarms()) {
                    showExactAlarmPermissionGuideDialog()
                    return@runOnUiThread
                }
            }

            val intent = AlarmReceiver.newIntent(this, ticket.title, ticket.id)

            val pendingIntent =
                PendingIntent.getBroadcast(
                    this,
                    ticket.id.toInt(),
                    intent,
                    PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE,
                )

            val alarmMillis = ticket.alarmTime()

            alarmManager.setExactAndAllowWhileIdle(
                AlarmManager.RTC_WAKEUP,
                alarmMillis,
                pendingIntent,
            )
        }

    override fun showMessage() {
        runOnUiThread { showToast(R.string.text_booking_fail) }
    }

    @RequiresApi(Build.VERSION_CODES.S)
    private fun showExactAlarmPermissionGuideDialog() {
        AlertDialog.Builder(this)
            .setTitle(getString(R.string.permission_exact_alarm_title))
            .setMessage(getString(R.string.permission_exact_alarm_message))
            .setPositiveButton(getString(R.string.permission_exact_alarm_allow)) { _, _ ->
                val intent =
                    Intent(Settings.ACTION_REQUEST_SCHEDULE_EXACT_ALARM).apply {
                        data = "package:${this@BookingCompleteActivity.packageName}".toUri()
                    }.apply {
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                    }
                startActivity(intent)
            }
            .setNegativeButton(getString(R.string.permission_exact_alarm_deny), null)
            .show()
    }

    companion object {
        const val KEY_TICKET_ID = "TICKET_ID"
        const val KEY_FROM = "KEY_FROM"

        fun newIntent(
            context: Context,
            ticketId: Long,
            from: String? = null,
        ) = Intent(context, BookingCompleteActivity::class.java).apply {
            putExtra(KEY_TICKET_ID, ticketId)
            from?.let { putExtra(KEY_FROM, from) }
    }
}
