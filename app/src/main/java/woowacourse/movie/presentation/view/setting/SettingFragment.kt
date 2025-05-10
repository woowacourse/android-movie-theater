package woowacourse.movie.presentation.view.setting

import android.Manifest
import android.app.AlertDialog
import android.content.ActivityNotFoundException
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import androidx.core.net.toUri
import androidx.fragment.app.Fragment
import woowacourse.movie.BuildConfig
import woowacourse.movie.R
import woowacourse.movie.data.SettingPreferenceManager
import woowacourse.movie.databinding.FragmentSettingBinding

class SettingFragment :
    Fragment(),
    SettingContract.View {
    private lateinit var binding: FragmentSettingBinding
    private val presenter: SettingContract.Presenter by lazy {
        val preferenceManager = SettingPreferenceManager(requireContext())
        SettingPresenter(this, preferenceManager)
    }

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentSettingBinding.inflate(inflater, container, false)
        presenter.fetchSettingInfo()
        setSwitchListener()
        return binding.root
    }

    override fun showPushAlarmSetting(isEnabled: Boolean) {
        binding.switchSettingPushAlarm.isChecked = isEnabled
    }

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    private fun setSwitchListener() {
        binding.switchSettingPushAlarm.setOnCheckedChangeListener { _, isChecked ->
            presenter.savePushAlarmSetting(isChecked)
            if (isChecked) {
                requestNotificationPermission()
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    private val requestPermissionLauncher =
        registerForActivityResult(
            ActivityResultContracts.RequestPermission(),
        ) { isGranted: Boolean ->
            updateSwitchState(isGranted)
            if (isGranted) {
                showNotificationSettingsDialog()
            }
        }

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    private fun requestNotificationPermission() {
        if (!isNotificationPermissionGranted()) {
            if (shouldShowPermissionRationale()) {
                showPermissionRationale()
            } else {
                requestPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
            }
        }
    }

    private fun isNotificationPermissionGranted(): Boolean {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.TIRAMISU) return true

        return ContextCompat.checkSelfPermission(
            requireContext(),
            Manifest.permission.POST_NOTIFICATIONS,
        ) == PackageManager.PERMISSION_GRANTED
    }

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    private fun shouldShowPermissionRationale(): Boolean = shouldShowRequestPermissionRationale(Manifest.permission.POST_NOTIFICATIONS)

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    private fun updateSwitchState(isGranted: Boolean) {
        binding.switchSettingPushAlarm.setOnCheckedChangeListener(null)
        binding.switchSettingPushAlarm.isChecked = isGranted
        setSwitchListener()
    }

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    private fun showPermissionRationale() {
        AlertDialog
            .Builder(requireContext())
            .setTitle(getString(R.string.setting_request_permission_dialog_title))
            .setMessage(R.string.setting_request_permission_dialog_message)
            .setPositiveButton(R.string.setting_request_permission_dialog_positive) { _, _ ->
                requestPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
            }.setNegativeButton(R.string.setting_request_permission_dialog_negative) { _, _ ->
                binding.switchSettingPushAlarm.isChecked = false
            }.show()
    }

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    private fun showNotificationSettingsDialog() {
        AlertDialog
            .Builder(requireContext())
            .setTitle(getString(R.string.setting_request_permission_dialog_title))
            .setMessage(getString(R.string.setting_request_reminder_permission_dialog_message))
            .setPositiveButton(R.string.setting_request_permission_dialog_positive) { _, _ ->
                openAppNotificationSettings()
            }.setNegativeButton(R.string.setting_request_permission_dialog_negative, null)
            .show()
    }

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    private fun openAppNotificationSettings() {
        try {
            val intent =
                Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).apply {
                    data = APPLICATION_ID.toUri()
                }
            openAppSettingsLauncher.launch(intent)
        } catch (e: ActivityNotFoundException) {
            showToast(getString(R.string.setting_error_message))
        }
    }

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    private val openAppSettingsLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            updateNotificationPermissionState()
        }

    private fun updateNotificationPermissionState() {
        val isGranted = isNotificationPermissionGranted()
        presenter.savePushAlarmSetting(isGranted)
    }

    private fun showToast(message: String) = Toast.makeText(requireContext(), message, Toast.LENGTH_LONG).show()

    companion object {
        private const val APPLICATION_ID = "package:${BuildConfig.APPLICATION_ID}"
    }
}
