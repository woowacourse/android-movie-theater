package woowacourse.movie.presentation.setting

import android.Manifest.permission.POST_NOTIFICATIONS
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import woowacourse.movie.MovieReservationApp
import woowacourse.movie.R
import woowacourse.movie.databinding.FragmentSettingBinding
import woowacourse.movie.presentation.base.BindingFragment


class SettingFragment : BindingFragment<FragmentSettingBinding>(R.layout.fragment_setting) {

    private val notificationPreference by lazy { (requireActivity().application as MovieReservationApp).notificationDatastore }

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

    private val requestPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
            if (isGranted) {
                notificationPreference.canNotification = true
                binding.switchAlarm.isChecked = true
            } else {
                showToast(getString(R.string.deny_notification_permission))
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
        binding.switchAlarm.isChecked = notificationPreference.canNotification
        binding.layoutPushAlarm.setOnClickListener {
            if (notificationPreference.canNotification) {
                updateAlarmSwitch(false)
                return@setOnClickListener
            }
            when {
                Build.VERSION.SDK_INT < Build.VERSION_CODES.TIRAMISU -> updateAlarmSwitch(true)
                isSecondRequestPermission() -> explanationDialogForPushAlarm.show()
                else -> requestPermissionLauncher.launch(POST_NOTIFICATIONS)
            }
        }
    }

    private fun updateAlarmSwitch(isChecked: Boolean) {
        notificationPreference.canNotification = isChecked
        binding.switchAlarm.isChecked = isChecked
    }

    private fun isSecondRequestPermission(): Boolean =
        (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) && shouldShowRequestPermissionRationale(
            POST_NOTIFICATIONS
        )

    private fun showToast(message: String) {
        Toast.makeText(
            requireActivity(),
            message,
            Toast.LENGTH_SHORT
        ).show()
    }
}