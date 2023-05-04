package woowacourse.movie.presentation.view.main.setting

import android.Manifest
import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.widget.SwitchCompat
import androidx.fragment.app.Fragment
import woowacourse.movie.R

class SettingFragment : Fragment(R.layout.fragment_setting), SettingContract.View {
    private val switchSettingAlarm by lazy { requireView().findViewById<SwitchCompat>(R.id.switch_setting_alarm) }
    private val presenter: SettingPresenter by lazy {
        SettingPresenter(
            view = this,
            context = requireContext()
        )
    }

    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted: Boolean ->
        presenter.setAlarmSetting(isGranted)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter.getAlarmSettingInfo()
    }

    override fun updatePermissionNotGrantedView() {
        switchSettingAlarm.isSelected = false
    }

    override fun updateAlarmSettingView(isSet: Boolean, isGranted: Boolean) {
        setAlarmView(isSet, isGranted)
    }

    private fun setAlarmView(allowedPushNotification: Boolean, isGranted: Boolean) {
        switchSettingAlarm.isChecked =
            allowedPushNotification && isGranted
        switchSettingAlarm.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked && isGranted.not()) {
                requestNotificationPermission()
            }
        }
    }

    private fun requestNotificationPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            requestPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
        }
    }
}
