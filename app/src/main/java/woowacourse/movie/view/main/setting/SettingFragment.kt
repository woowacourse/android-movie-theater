package woowacourse.movie.view.main.setting

import android.Manifest
import android.app.AlarmManager
import android.app.AlertDialog
import android.content.Context
import android.content.Context.MODE_PRIVATE
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
import androidx.core.content.ContextCompat
import androidx.core.content.edit
import androidx.fragment.app.Fragment
import woowacourse.movie.R
import woowacourse.movie.databinding.FragmentSettingBinding
import woowacourse.movie.view.util.AlarmManagerHelper
import woowacourse.movie.view.util.ExceptionMessages
import woowacourse.movie.view.util.Extras.SettingData.NOTIFICATION_KEY
import woowacourse.movie.view.util.Extras.SettingData.SETTINGS_KEY

class SettingFragment : Fragment() {
    @Suppress("ktlint:standard:backing-property-naming")
    private var _binding: FragmentSettingBinding? = null
    private val binding: FragmentSettingBinding
        get() =
            _binding
                ?: throw IllegalStateException(ExceptionMessages.FRAGMENT_BINDING_STATE_EXCEPTION)
    private val requestPermissionLauncher =
        registerForActivityResult(
            ActivityResultContracts.RequestPermission(),
        ) { isGranted ->
            saveNotificationState(isGranted)
        }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = FragmentSettingBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?,
    ) {
        super.onViewCreated(view, savedInstanceState)
        setupNotificationSwitch()
    }

    private fun setupNotificationSwitch() {
        binding.settingNotificationSwitch.isChecked = loadNotificationState()
        binding.settingNotificationSwitch.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                when {
                    canNotificationControl() -> saveNotificationState(true)
                    isNotificationPermissionPermanentlyDenied() -> showNotificationPermissionDialog()
                    !hasExactAlarmPermission() -> showExactAlarmPermissionDialog()
                    else -> requireNotificationPermission()
                }
            } else {
                saveNotificationState(false)
            }
        }
    }

    override fun onResume() {
        super.onResume()
        if (!canNotificationControl()) {
            saveNotificationState(false)
        } else {
            saveNotificationState(true)
        }
    }

    private fun isNotificationPermissionPermanentlyDenied(): Boolean =
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            !hasNotificationPermission() &&
                !shouldShowRequestPermissionRationale(Manifest.permission.POST_NOTIFICATIONS)
        } else {
            false
        }

    private fun requireNotificationPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            requestPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
        }
    }

    private fun canNotificationControl(): Boolean = hasNotificationPermission() && hasExactAlarmPermission()

    private fun hasNotificationPermission(): Boolean =
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.POST_NOTIFICATIONS,
            ) == PackageManager.PERMISSION_GRANTED
        } else {
            true
        }

    private fun hasExactAlarmPermission(): Boolean =
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            val alarmManager =
                requireContext().getSystemService(Context.ALARM_SERVICE) as AlarmManager
            alarmManager.canScheduleExactAlarms()
        } else {
            true
        }

    private fun showNotificationPermissionDialog() {
        showPermissionSettingsDialog(
            getString(R.string.setting_notification_permission),
            Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).apply {
                data = Uri.fromParts("package", requireContext().packageName, null)
            },
        )
    }

    private fun showExactAlarmPermissionDialog() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            showPermissionSettingsDialog(
                getString(R.string.setting_exact_alarm_permission),
                Intent(Settings.ACTION_REQUEST_SCHEDULE_EXACT_ALARM),
            )
        }
    }

    private fun showPermissionSettingsDialog(
        permissionName: String,
        intent: Intent,
    ) {
        AlertDialog
            .Builder(requireContext())
            .setTitle(R.string.setting_permission_dialog_title)
            .setMessage(getString(R.string.setting_permission_dialog_content, permissionName))
            .setPositiveButton(R.string.setting_permission_dialog_positive) { _, _ ->
                startActivity(intent)
            }.setNegativeButton(R.string.setting_permission_dialog_negative) { _, _ ->
                saveNotificationState(false)
            }.setOnCancelListener {
                saveNotificationState(false)
            }.show()
    }

    private fun handleAlarmRegistration(isEnabled: Boolean) {
        val alarmHelper = AlarmManagerHelper(requireContext())

        if (isEnabled) {
            if (canNotificationControl()) {
                alarmHelper.scheduleAllMovieAlarms()
            }
        } else {
            alarmHelper.cancelAllAlarms()
        }
    }

    private fun saveNotificationState(isEnabled: Boolean) {
        handleAlarmRegistration(isEnabled)
        binding.settingNotificationSwitch.isChecked = isEnabled
        val sharedPreference = requireContext().getSharedPreferences(SETTINGS_KEY, MODE_PRIVATE)
        sharedPreference.edit {
            putBoolean(NOTIFICATION_KEY, isEnabled)
        }
    }

    private fun loadNotificationState(): Boolean {
        val sharedPreference = requireContext().getSharedPreferences(SETTINGS_KEY, MODE_PRIVATE)
        return sharedPreference.getBoolean(NOTIFICATION_KEY, false)
    }
}
