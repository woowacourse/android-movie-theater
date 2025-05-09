package woowacourse.movie.presentation.setting

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import androidx.core.net.toUri
import woowacourse.movie.BuildConfig
import woowacourse.movie.R
import woowacourse.movie.data.preference.NotificationPreferenceListenerImpl
import woowacourse.movie.data.preference.PreferenceManager
import woowacourse.movie.databinding.FragmentSettingBinding
import woowacourse.movie.presentation.common.base.BaseFragment
import woowacourse.movie.presentation.common.custom.CustomAlertDialog
import woowacourse.movie.presentation.common.custom.DialogInfo

class SettingFragment :
    BaseFragment<FragmentSettingBinding>(R.layout.fragment_setting),
    SettingContract.View {
    private lateinit var presenter: SettingContract.Presenter
    private val dialog: CustomAlertDialog by lazy { CustomAlertDialog(requireContext()) }

    private val permissionRationaleDialogInfo by lazy {
        DialogInfo(
            getString(R.string.notification_permission_dialog_title),
            getString(R.string.notification_permission_dialog_message),
            false,
            getString(R.string.notification_permission_dialog_negative),
            getString(R.string.notification_permission_dialog_positive),
            { presenter.updateNotificationEnabled(false) },
            { if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) launchResultLauncher() },
        )
    }

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

        setSettingPresenter()
        setNotificationSwitchListener()
        syncNotificationPermissionState()
    }

    override fun notifyNotificationEnabled(isEnabled: Boolean) {
        binding.switchNotification.isChecked = isEnabled
    }

    private fun setSettingPresenter() {
        val preferenceManager = PreferenceManager.getInstance(requireContext())
        val prefsListener = NotificationPreferenceListenerImpl(preferenceManager)
        presenter = SettingPresenter(this, prefsListener)
    }

    private fun setNotificationSwitchListener() {
        binding.switchNotification.setOnCheckedChangeListener { _, isChecked ->
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
                dialog.show(permissionRationaleDialogInfo)
                return
            }

            requestPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
            return
        }

        presenter.updateNotificationEnabled(true)
    }

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    private fun launchResultLauncher() {
        val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
        intent.data = INTENT_DATA_PACKAGE_NAME.toUri()
        openAppSettingsLauncher.launch(intent)
    }

    companion object {
        private const val INTENT_DATA_PACKAGE_NAME = "package:${BuildConfig.APPLICATION_ID}"
    }
}
