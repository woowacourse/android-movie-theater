package woowacourse.movie.view

import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.view.MenuItem
import androidx.activity.OnBackPressedCallback
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.preference.PreferenceManager
import woowacourse.movie.R
import woowacourse.movie.databinding.ActivityReservationCompletedBinding
import woowacourse.movie.util.DATE_FORMATTER
import woowacourse.movie.util.TIME_FORMATTER
import woowacourse.movie.util.getParcelableCompat
import woowacourse.movie.view.model.ReservationUiModel
import woowacourse.movie.view.moviemain.MovieMainActivity
import woowacourse.movie.view.moviemain.setting.SettingFragment
import woowacourse.movie.view.seatselection.AlarmReceiver
import java.text.DecimalFormat

class ReservationCompletedActivity : AppCompatActivity() {

    private lateinit var binding: ActivityReservationCompletedBinding
    private lateinit var sharedPreferences: SharedPreferences
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityReservationCompletedBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val reservation = intent.getParcelableCompat<ReservationUiModel>(RESERVATION)
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this)
        val isAlarmOn = sharedPreferences.getBoolean(SettingFragment.IS_ALARM_ON, false)

        reservation?.let {
            initViewData(it)
            if (isAlarmOn) setAlarm(reservation, SettingFragment.ALARM_MINUTE_INTERVAL)
        }

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        onBackPressedDispatcher.addCallback(
            this,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    val intent = Intent(this@ReservationCompletedActivity, MovieMainActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
                    startActivity(intent)
                }
            },
        )
    }

    private fun setAlarm(reservation: ReservationUiModel, alarmMinuteInterval: Long) {
        val alarmController = AlarmController(this)
        createChannel()
        requestNotificationPermission()
        alarmController.registerAlarm(reservation, alarmMinuteInterval)
    }

    private fun initViewData(reservation: ReservationUiModel) {
        binding.apply {
            movieTitle.text = reservation.title
            movieScreeningDate.text = getString(
                R.string.datetime_with_space,
                reservation.screeningDateTime.format(DATE_FORMATTER),
                reservation.screeningDateTime.format(TIME_FORMATTER),
            )
            peopleCount.text = getString(R.string.reservation_people_count_format)
                .format(
                    getString(R.string.general_person),
                    reservation.peopleCount,
                    reservation.seats.joinToString(),
                )
            totalPrice.text =
                getString(R.string.total_price_format).format(DECIMAL_FORMAT.format(reservation.finalReservationFee))
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                val intent = Intent(this, MovieMainActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
                startActivity(intent)
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun createChannel() {
        val name = "Reservation Notification"
        val channel = NotificationChannel(
            AlarmReceiver.CHANNEL_ID,
            name,
            NotificationManager.IMPORTANCE_DEFAULT,
        )
        val notificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(channel)
    }

    private fun requestNotificationPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU && !shouldShowRequestPermissionRationale(Manifest.permission.POST_NOTIFICATIONS)) {
                requestPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
            }
        }
    }

    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission(),
    ) { isGranted: Boolean ->
        val editor: SharedPreferences.Editor = sharedPreferences.edit()
        if (isGranted) {
            editor.putBoolean(SettingFragment.IS_ALARM_ON, true).apply()
            return@registerForActivityResult
        }
        editor.putBoolean(SettingFragment.IS_ALARM_ON, false).apply()
    }

    companion object {
        private const val RESERVATION = "RESERVATION"
        private val DECIMAL_FORMAT = DecimalFormat("#,###")
        const val REQUEST_CODE = 101

        fun newIntent(context: Context, reservation: ReservationUiModel): Intent {
            val intent = Intent(context, ReservationCompletedActivity::class.java)
            intent.putExtra(RESERVATION, reservation)
            return intent
        }

        fun getPendingIntent(context: Context, reservation: ReservationUiModel): PendingIntent {
            return PendingIntent.getActivity(
                context,
                REQUEST_CODE,
                newIntent(context, reservation),
                PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT,
            )
        }
    }
}
