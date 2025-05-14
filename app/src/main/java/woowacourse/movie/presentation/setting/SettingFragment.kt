package woowacourse.movie.presentation.setting

import android.Manifest
import android.app.AlertDialog
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import woowacourse.movie.R
import woowacourse.movie.databinding.FragmentSettingBinding
import woowacourse.movie.presentation.common.base.BaseFragment

class SettingFragment :
    BaseFragment<FragmentSettingBinding>(R.layout.fragment_setting),
    SettingContract.View {
    private lateinit var presenter: SettingContract.Presenter

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    private val requestPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
            presenter.updateNotificationEnabled(isGranted)
        }

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    private val openAppSettingsLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            syncNotificationPermissionState()
        }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?,
    ) {
        super.onViewCreated(view, savedInstanceState)
        setPresenter()
        setNotificationSwitchListener()
    }

    override fun notifyNotificationEnabled(isEnabled: Boolean) {
        binding.switchNotification.isChecked = isEnabled
    }

    private fun setPresenter() {
        presenter = SettingPresenter(this)
    }

    private fun setNotificationSwitchListener() {
        binding.switchNotification.setOnCheckedChangeListener { button, isChecked ->
            if (!button.isPressed) return@setOnCheckedChangeListener

            if (isChecked) {
                requestNotificationPermission()
                return@setOnCheckedChangeListener
            }

            presenter.updateNotificationEnabled(false)
        }
    }

    private fun syncNotificationPermissionState() {
        val isGranted = isPermissionGranted()
        presenter.updateNotificationEnabled(isGranted)
    }

    private fun isPermissionGranted(): Boolean {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.TIRAMISU) return true

        return ContextCompat.checkSelfPermission(
            requireContext(),
            Manifest.permission.POST_NOTIFICATIONS,
        ) == PackageManager.PERMISSION_GRANTED
    }

    private fun requestNotificationPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (shouldShowRequestPermissionRationale(Manifest.permission.POST_NOTIFICATIONS)) {
                showPermissionRationaleDialogInfo()
                return
            }

            requestPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
            return
        }

        presenter.updateNotificationEnabled(true)
    }

    private fun showPermissionRationaleDialogInfo() {
        AlertDialog.Builder(requireContext())
            .setTitle(R.string.notification_permission_dialog_title)
            .setMessage(R.string.notification_permission_dialog_message)
            .setPositiveButton(R.string.notification_permission_dialog_positive) { _, _ ->
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                    launchResultLauncher()
                }
            }
            .setNegativeButton(R.string.notification_permission_dialog_negative) { _, _ ->
                presenter.updateNotificationEnabled(false)
            }.show()
    }

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    private fun launchResultLauncher() {
        val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
        intent.data = Uri.fromParts(SETTINGS_INTENT_DATA_SCHEME, requireContext().packageName, null)
        openAppSettingsLauncher.launch(intent)
    }

    companion object {
        private const val SETTINGS_INTENT_DATA_SCHEME = "package"
    }
}
