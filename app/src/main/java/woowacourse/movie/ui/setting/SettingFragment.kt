package woowacourse.movie.ui.setting

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SwitchCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import woowacourse.movie.R
import woowacourse.movie.model.AlarmSwitchState
import woowacourse.movie.model.ReservationModel
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.ZoneOffset

class SettingFragment : Fragment() {
    private lateinit var toggleButton: SwitchCompat
    private val requestPermissionLauncher by lazy {
        registerForActivityResult(
            ActivityResultContracts.RequestPermission(),
        ) {}
    }
    private val alarmManager by lazy {
        requireContext().getSystemService(AppCompatActivity.ALARM_SERVICE) as AlarmManager
    }
    private lateinit var pendingIntent: PendingIntent

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        val view = inflater.inflate(R.layout.fragment_setting, container, false)

        AlarmSwitchState.init(requireContext())
        requestNotificationPermission(view)
        initToggleButton(view)
        return view
    }

    private fun initToggleButton(view: View) {
        toggleButton = view.findViewById(R.id.setting_switch)
        toggleButton.isChecked = AlarmSwitchState.isAlarmActivated

        toggleButton.setOnCheckedChangeListener { _, isChecked ->
            AlarmSwitchState.isAlarmActivated = isChecked

            if (isChecked) {
                makeAlarm()
            } else {
                cancelAlarm()
            }
        }
    }

    private fun makeAlarm() {
        if (ReservationModel.firstTicket == null) return

        setAlarmIntent()

        val setDateTime: LocalDateTime = ReservationModel.firstTicket.time.minusMinutes(30)
        val dateTime =
            setDateTime.atZone(ZoneId.systemDefault()).withZoneSameInstant(ZoneOffset.UTC)
                .toLocalDateTime()
        val currentTimeMillis: Long =
            dateTime.atZone(ZoneOffset.UTC).toInstant().toEpochMilli()

        alarmManager.set(
            AlarmManager.RTC_WAKEUP,
            currentTimeMillis,
            pendingIntent,
        )
    }

    private fun setAlarmIntent() {
        val intent = Intent(requireContext(), ReservationAlarmReceiver::class.java)

        if (ReservationModel.firstTicket != null) {
            intent.putExtra(KEY_MOVIE, ReservationModel.firstTicket)
        }

        pendingIntent = PendingIntent.getBroadcast(
            requireContext(),
            ReservationAlarmReceiver.NOTIFICATION_ID,
            intent,
            PendingIntent.FLAG_IMMUTABLE,
        )
    }

    private fun cancelAlarm() {
        if (this::pendingIntent.isInitialized) {
            alarmManager.cancel(pendingIntent)
        }
    }

    private fun requestNotificationPermission(view: View) {
        if (ContextCompat.checkSelfPermission(
                view.context,
                android.Manifest.permission.POST_NOTIFICATIONS,
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                if (shouldShowRequestPermissionRationale(android.Manifest.permission.POST_NOTIFICATIONS)) {
                    // 권한 요청 거부한 경우
                } else {
                    requestPermissionLauncher.launch(android.Manifest.permission.POST_NOTIFICATIONS)
                }
            } else {
                // 안드로이드 12 이하는 Notification에 관한 권한 필요 없음
            }
        }
    }

    companion object {
        const val KEY_MOVIE = "movie"
        private const val KEY_SWITCH = "switch"
    }
}
