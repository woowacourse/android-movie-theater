package woowacourse.movie.feature.setting

import android.Manifest
import android.os.Bundle
import android.view.View
import android.widget.CompoundButton
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.widget.SwitchCompat
import androidx.fragment.app.Fragment
import woowacourse.movie.R
import woowacourse.movie.data.UserSettings
import woowacourse.movie.feature.Toaster
import woowacourse.movie.util.hasPermission
import woowacourse.movie.util.requestPermission

class SettingFragment : Fragment(R.layout.fragment_setting) {

    private lateinit var notificationSwitch: SwitchCompat
    private val userSettings = UserSettings.getInstance()

    private val requestPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setNotificationSwitch(view)
        notificationSwitch.setOnCheckedChangeListener { switchCompat, _ ->
            notificationSwitchClickEvent(switchCompat)
        }
    }

    private fun setNotificationSwitch(view: View) {
        notificationSwitch = view.findViewById(R.id.notification_switch)
        notificationSwitch.isChecked = userNotificationSetting()
    }

    private fun notificationSwitchClickEvent(switchCompat: CompoundButton) {
        if (!userNotificationSetting() && hasPermission(requireContext(), Manifest.permission.POST_NOTIFICATIONS)) {
            switchCompat.isChecked = false
            Toaster.showToast(requireContext(), "설정에서 알림 권한을 허용해주세요.")
            requestNotificationPermission()
        }
        userSettings.set(requireContext(), UserSettings.NOTIFICATION_KEY, switchCompat.isChecked)
    }

    private fun userNotificationSetting(): Boolean =
        userSettings.get(requireContext(), UserSettings.NOTIFICATION_KEY)

    private fun requestNotificationPermission() {
        requestPermission(
            requireActivity(),
            Manifest.permission.POST_NOTIFICATIONS,
            requestPermissionLauncher::launch
        )
    }
}
