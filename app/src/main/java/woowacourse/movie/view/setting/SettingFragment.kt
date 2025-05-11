package woowacourse.movie.view.setting

import android.Manifest
import android.annotation.SuppressLint
import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.widget.SwitchCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import woowacourse.movie.data.TicketInfoDatabase
import woowacourse.movie.data.TicketRepository
import woowacourse.movie.databinding.FragmentSettingBinding
import woowacourse.movie.view.setting.alarm.AlarmReceiver

class SettingFragment : Fragment() {
    private var _binding: FragmentSettingBinding? = null
    private val binding get() = _binding!!

    private lateinit var alarmBtn: SwitchCompat

    private val sharedPref by lazy {
        requireActivity().getSharedPreferences("setting", Context.MODE_PRIVATE)
    }

    private val scheduleExactAlarmPermissionLauncher =
        registerForActivityResult(
            ActivityResultContracts.StartActivityForResult(),
        ) {
            if (canScheduleExactAlarm()) {
                setAlarm()
            } else {
                alarmBtn.isChecked = false
            }
        }

    private val repository: TicketRepository by lazy {
        val dao = TicketInfoDatabase.getDatabase(requireContext()).ticketInfoDao()
        TicketRepository(dao)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentSettingBinding.inflate(inflater, container, false)
        val view = binding.root

        val isAlarmOn = sharedPref.getBoolean("isAlarmOn", false)
        setAlarmBtn(isAlarmOn)

        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun checkAndRequestExactAlarmPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            if (!canScheduleExactAlarm()) {
                val intent =
                    Intent(Settings.ACTION_REQUEST_SCHEDULE_EXACT_ALARM).apply {
                        data = Uri.parse("package:${requireContext().packageName}")
                    }
                scheduleExactAlarmPermissionLauncher.launch(intent)
            } else {
                setAlarm()
            }
        } else {
            setAlarm()
        }
    }

    private fun canScheduleExactAlarm(): Boolean {
        val alarmManager = requireContext().getSystemService(Context.ALARM_SERVICE) as AlarmManager

        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            alarmManager.canScheduleExactAlarms()
        } else {
            true
        }
    }

    private val requestPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
            if (isGranted) {
                checkAndRequestExactAlarmPermission()
            } else {
                alarmBtn.isChecked = false
                cancelAlarm()
            }
        }

    private fun setAlarmBtn(isAlarmOn: Boolean) {
        alarmBtn = binding.settingAlarmSwitchBtn
        alarmBtn.isChecked = isAlarmOn

        alarmBtn.setOnCheckedChangeListener { _, isChecked ->
            sharedPref.edit().putBoolean("isAlarmOn", isChecked).apply()

            if (isChecked) {
                requestNotificationPermission()
            } else {
                cancelAlarm()
            }
        }
    }

    private fun requestNotificationPermission() {
        if (ContextCompat.checkSelfPermission(
                requireContext(), Manifest.permission.POST_NOTIFICATIONS,
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                if (shouldShowRequestPermissionRationale(Manifest.permission.POST_NOTIFICATIONS)) {
                } else {
                    requestPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
                }
            } else {
                checkAndRequestExactAlarmPermission()
            }
        } else {
            checkAndRequestExactAlarmPermission()
        }
    }

    @SuppressLint("ScheduleExactAlarm")
    private fun setAlarm() {
        val intent = Intent(requireContext(), AlarmReceiver::class.java)
        val pendingIntent =
            PendingIntent.getBroadcast(requireContext(), 0, intent, PendingIntent.FLAG_IMMUTABLE)
        val alarmManager = requireContext().getSystemService(Context.ALARM_SERVICE) as AlarmManager

        val testTime = System.currentTimeMillis() + 5_000

        alarmManager.setExactAndAllowWhileIdle(
            AlarmManager.RTC_WAKEUP,
            testTime,
            pendingIntent,
        )
    }

    private fun cancelAlarm() {
        val intent = Intent(requireContext(), AlarmReceiver::class.java)
        val pendingIntent =
            PendingIntent.getBroadcast(requireContext(), 1000, intent, PendingIntent.FLAG_IMMUTABLE)
        val alarmManager = requireContext().getSystemService(Context.ALARM_SERVICE) as AlarmManager
        alarmManager.cancel(pendingIntent)
    }
}
