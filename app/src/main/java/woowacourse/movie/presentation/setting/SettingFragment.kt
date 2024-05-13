package woowacourse.movie.presentation.setting

import android.Manifest.permission.POST_NOTIFICATIONS
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import woowacourse.movie.R
import woowacourse.movie.data.datastore.DefaultNotificationDataStore
import woowacourse.movie.databinding.FragmentSettingBinding
import woowacourse.movie.presentation.base.BindingFragment

class SettingFragment : BindingFragment<FragmentSettingBinding>(R.layout.fragment_setting) {
    private val notificationDataStore by lazy {
        DefaultNotificationDataStore.instance(requireContext().applicationContext)
    }

    private val explanationDialogForPushAlarm by lazy {
        AlertDialog.Builder(requireContext())
            .setTitle(R.string.request_notification_permission_title)
            .setMessage(R.string.request_notification_permission)
            .setPositiveButton(R.string.request_notification_permission_confirm) { _, _ ->
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                    requestPermissionLauncher.launch(POST_NOTIFICATIONS)
                }
            }
            .setNegativeButton(R.string.request_notification_permission_deny) { dialog, _ ->
                dialog.dismiss()
            }
            .create()
    }

    private val explanationDialogForNavigateToSetting by lazy {
        AlertDialog.Builder(requireContext())
            .setTitle(R.string.request_notification_permission_title)
            .setMessage(R.string.navigate_to_application_detail_setting_descriptrion)
            .setPositiveButton(R.string.navigate_to_application_detail_setting_positive_button) { _, _ ->
                activityResultLauncher.launch(appSettingIntent())
            }
            .setNegativeButton(R.string.request_notification_permission_deny) { dialog, _ ->
                dialog.dismiss()
            }
            .create()
    }

    private val requestPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
            if (isGranted) {
                updateAlarmSwitch(true)
            } else {
                showToast(getString(R.string.deny_notification_permission))
            }
        }

    private val activityResultLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { _ ->
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                if (hasAccessPermission()) {
                    updateAlarmSwitch(true)
                } else {
                    showToast(getString(R.string.deny_notification_permission))
                }
            }
        }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?,
    ) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
    }

    private fun initViews() {
        binding.switchAlarm.isChecked = notificationDataStore.acceptedPushAlarm
        binding.layoutPushAlarm.setOnClickListener {
            if (notificationDataStore.acceptedPushAlarm) {
                updateAlarmSwitch(false)
                return@setOnClickListener
            }
            when {
                Build.VERSION.SDK_INT < Build.VERSION_CODES.TIRAMISU -> updateAlarmSwitch(true)
                hasAccessPermission() -> updateAlarmSwitch(true)
                isFirstRequestPermission() -> requestPermissionLauncher.launch(POST_NOTIFICATIONS)
                isSecondRequestPermission() -> {
                    notificationDataStore.hasBeenDeniedPermission = true
                    explanationDialogForPushAlarm.show()
                }

                isCompletelyDeniedPermission() -> explanationDialogForNavigateToSetting.show()
                else -> requestPermissionLauncher.launch(POST_NOTIFICATIONS)
            }
        }
    }

    private fun updateAlarmSwitch(isChecked: Boolean) {
        notificationDataStore.acceptedPushAlarm = isChecked
        binding.switchAlarm.isChecked = isChecked
    }

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    private fun isFirstRequestPermission(): Boolean =
        hasAccessPermission().not() &&
            shouldShowRequestPermissionRationale(POST_NOTIFICATIONS).not() &&
            notificationDataStore.hasBeenDeniedPermission.not()

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    private fun isSecondRequestPermission(): Boolean = shouldShowRequestPermissionRationale(POST_NOTIFICATIONS)

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    private fun isCompletelyDeniedPermission(): Boolean =
        hasAccessPermission().not() &&
            shouldShowRequestPermissionRationale(POST_NOTIFICATIONS).not() &&
            notificationDataStore.hasBeenDeniedPermission

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    private fun hasAccessPermission(): Boolean {
        return ContextCompat.checkSelfPermission(
            requireContext(), POST_NOTIFICATIONS,
        ) == PackageManager.PERMISSION_GRANTED
    }

    private fun appSettingIntent(): Intent {
        return Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
            .setData(Uri.fromParts("package", requireContext().packageName, null))
    }

    private fun showToast(message: String) {
        Toast.makeText(
            requireActivity(),
            message,
            Toast.LENGTH_SHORT,
        ).show()
    }
}
