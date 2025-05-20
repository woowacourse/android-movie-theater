package woowacourse.movie.view.setting

import android.Manifest
import android.app.AlarmManager
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.content.edit
import androidx.core.net.toUri
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import woowacourse.movie.R
import woowacourse.movie.databinding.FragmentSettingBinding
import woowacourse.movie.view.dialog.DialogFactory
import woowacourse.movie.view.dialog.DialogInfo

class SettingFragment : Fragment() {
    private var _binding: FragmentSettingBinding? = null
    private val binding get() = _binding!!

    private val preferences by lazy {
        requireContext().getSharedPreferences("setting_preferences", Context.MODE_PRIVATE)
    }

    private fun isNotificationEnabledInApp(): Boolean {
        return preferences.getBoolean("notification_isEnabled", false)
    }

    private fun setNotificationEnabledInApp(enabled: Boolean) {
        preferences.edit { putBoolean("notification_isEnabled", enabled) }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = DataBindingUtil.inflate(inflater, R.layout.fragment_setting, container, false)

        return binding.root
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?,
    ) {
        super.onViewCreated(view, savedInstanceState)

        binding.swNotification.setOnCheckedChangeListener(null)
        binding.swNotification.isChecked = isNotificationEnabledInApp()

        binding.swNotification.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                handleSwitchOn()
            } else {
                setNotificationEnabledInApp(false)
            }
        }
    }

    override fun onResume() {
        super.onResume()
        binding.swNotification.setOnCheckedChangeListener(null)
        binding.swNotification.isChecked =
            hasDeviceAlarmPermission() && isNotificationEnabledInApp()
        binding.swNotification.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                handleSwitchOn()
            } else {
                setNotificationEnabledInApp(false)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun handleSwitchOn() {
        if (!hasDeviceAlarmPermission()) {
            showPermissionAlarmDialog()
            binding.swNotification.isChecked = false
        } else if (!isNotificationPermissionGranted()) {
            showPermissionNotificationDialog()
            binding.swNotification.isChecked = false
        } else {
            setNotificationEnabledInApp(true)
        }
    }

    private fun showPermissionNotificationDialog() {
        DialogFactory().show(
            DialogInfo(
                requireContext(),
                getString(R.string.need_permission),
                getString(R.string.ask_for_need_notification_permission),
                getString(R.string.agree),
                getString(R.string.cancel),
            ),
        ) {
            val intent =
                Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).apply {
                    data = "package:${requireContext().packageName}".toUri()
                }
            startActivity(intent)
        }
    }

    private fun showPermissionAlarmDialog() {
        DialogFactory().show(
            DialogInfo(
                requireContext(),
                this.getString(R.string.need_permission),
                this.getString(R.string.ask_for_need_alarm_permission),
                this.getString(R.string.agree),
                this.getString(R.string.cancel),
            ),
        ) {
            requestDeviceAlarmPermission(requireContext())
        }
    }

    private fun hasDeviceAlarmPermission(): Boolean {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            val alarmManager = requireContext().getSystemService(AlarmManager::class.java)
            alarmManager.canScheduleExactAlarms()
        } else {
            true
        }
    }

    private fun requestDeviceAlarmPermission(context: Context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            val intent = Intent(Settings.ACTION_REQUEST_SCHEDULE_EXACT_ALARM)
            intent.data = "package:${context.packageName}".toUri()

            context.startActivity(intent)
        }
    }

    private fun isNotificationPermissionGranted(): Boolean {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.POST_NOTIFICATIONS,
            ) == PackageManager.PERMISSION_GRANTED
        } else {
            true
        }
    }
}
