package woowacourse.movie.presentation.view.setting

import android.Manifest
import android.app.AlertDialog
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
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
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

    private val requestPermissionLauncher =
        registerForActivityResult(
            ActivityResultContracts.RequestPermission(),
        ) { isGranted -> handlePermissionResult(isGranted) }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentSettingBinding.inflate(inflater, container, false)
        presenter.fetchSettingInfo()
        initPushAlarmSwitch()
        return binding.root
    }

    override fun showPushAlarmSetting(isEnabled: Boolean) {
        binding.switchSettingPushAlarm.isChecked = isEnabled
    }

    private fun initPushAlarmSwitch() {
        binding.switchSettingPushAlarm.setOnCheckedChangeListener { _, isChecked ->
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU && isChecked) {
                requestNotificationPermission()
            } else {
                presenter.savePushAlarmSetting(isChecked)
            }
        }
    }

    private fun requestNotificationPermission() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.TIRAMISU) return

        when {
            isNotificationPermissionGranted() -> presenter.savePushAlarmSetting(true)
            shouldShowRequestPermissionRationale(Manifest.permission.POST_NOTIFICATIONS) ->
                showPermissionRationaleDialog()

            else -> requestPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
        }
    }

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    private fun isNotificationPermissionGranted(): Boolean =
        ContextCompat.checkSelfPermission(
            requireContext(),
            Manifest.permission.POST_NOTIFICATIONS,
        ) == PackageManager.PERMISSION_GRANTED

    private fun handlePermissionResult(isGranted: Boolean) {
        if (isGranted) {
            binding.switchSettingPushAlarm.isChecked = true
            presenter.savePushAlarmSetting(true)
        } else {
            binding.switchSettingPushAlarm.isChecked = false
            presenter.savePushAlarmSetting(false)

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                if (shouldShowRequestPermissionRationale(Manifest.permission.POST_NOTIFICATIONS)) {
                    showPermissionRationaleDialog()
                } else {
                    showPermissionDeniedDialog()
                }
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    private fun showPermissionRationaleDialog() {
        AlertDialog
            .Builder(requireContext())
            .setTitle(getString(R.string.setting_request_permission_dialog_title))
            .setMessage(R.string.setting_request_permission_dialog_message)
            .setPositiveButton(R.string.setting_request_permission_dialog_positive) { _, _ ->
                requestPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
            }.setNegativeButton(R.string.setting_request_permission_dialog_negative) { _, _ ->
                binding.switchSettingPushAlarm.isChecked = false
                presenter.savePushAlarmSetting(false)
            }.show()
    }

    private fun showPermissionDeniedDialog() {
        AlertDialog
            .Builder(requireContext())
            .setTitle(R.string.setting_permission_denied_title)
            .setMessage(R.string.setting_permission_denied_message)
            .setPositiveButton(R.string.setting_permission_go_to_settings) { _, _ ->
                val intent =
                    Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).apply {
                        data = Uri.fromParts("package", requireContext().packageName, null)
                    }
                startActivity(intent)
            }.setNegativeButton(android.R.string.cancel) { _, _ ->
                binding.switchSettingPushAlarm.isChecked = false
                presenter.savePushAlarmSetting(false)
            }.show()
    }
}
