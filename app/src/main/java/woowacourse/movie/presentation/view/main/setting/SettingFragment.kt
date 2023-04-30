package woowacourse.movie.presentation.view.main.setting

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.widget.SwitchCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import woowacourse.movie.R
import woowacourse.movie.data.SharedPreferenceUtil

class SettingFragment : Fragment(R.layout.fragment_setting) {
    private val switchSettingAlarm by lazy { requireView().findViewById<SwitchCompat>(R.id.switch_setting_alarm) }
    private val sharedPreferenceUtil: SharedPreferenceUtil by lazy {
        SharedPreferenceUtil(
            requireContext()
        )
    }

    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted: Boolean ->
        if (isGranted.not()) {
            switchSettingAlarm.isSelected = isGranted
            sharedPreferenceUtil.setBoolean(getString(R.string.push_alarm_permission), isGranted)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val allowedPushNotification =
            sharedPreferenceUtil.getBoolean((getString(R.string.push_alarm_permission)), false)
        setAlarmView(allowedPushNotification)
    }

    private fun setAlarmView(allowedPushNotification: Boolean) {

        switchSettingAlarm.isChecked =
            allowedPushNotification && notificationPermissionIsGranted()
        switchSettingAlarm.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked && notificationPermissionIsGranted().not()) {
                requestNotificationPermission()
            }
            sharedPreferenceUtil.setBoolean(getString(R.string.push_alarm_permission), isChecked)
        }
    }

    private fun requestNotificationPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            requestPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
        }
    }

    private fun notificationPermissionIsGranted(): Boolean {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            return ContextCompat.checkSelfPermission(
                requireContext(), Manifest.permission.POST_NOTIFICATIONS
            ) == PackageManager.PERMISSION_GRANTED
        }
        return true
    }
}
