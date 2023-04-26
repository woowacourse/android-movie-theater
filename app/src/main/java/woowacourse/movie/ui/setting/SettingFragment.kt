package woowacourse.movie.ui.setting

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.os.SystemClock
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SwitchCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import woowacourse.movie.R

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

        setAlarmIntent()
        initToggleButton(view, pendingIntent)

        return view
    }

    private fun setAlarmIntent() {
        val intent = Intent(requireContext(), ReservationAlarmReceiver::class.java)
        pendingIntent = PendingIntent.getBroadcast(
            requireContext(),
            ReservationAlarmReceiver.NOTIFICATION_ID,
            intent,
            PendingIntent.FLAG_IMMUTABLE,
        )
    }

    private fun initToggleButton(view: View, pendingIntent: PendingIntent) {
        toggleButton = view.findViewById(R.id.setting_switch)
        toggleButton.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                requestNotificationPermission(view)
                makeAlarm(pendingIntent)
            } else {
                cancelAlarm(pendingIntent)
            }
        }
    }

    private fun cancelAlarm(pendingIntent: PendingIntent) {
        alarmManager.cancel(pendingIntent)
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

    private fun makeAlarm(pendingIntent: PendingIntent) {
        val time = (SystemClock.elapsedRealtime() + 3000) // 3초 뒤

        alarmManager.set(
            AlarmManager.RTC_WAKEUP,
            time,
            pendingIntent,
        )
    }
}
