package woowacourse.movie.feature.setting

import android.os.Bundle
import android.view.View
import android.widget.CompoundButton
import androidx.appcompat.widget.SwitchCompat
import androidx.fragment.app.Fragment
import woowacourse.movie.R
import woowacourse.movie.data.UserSettings

class SettingFragment : Fragment(R.layout.fragment_setting) {

    private lateinit var notificationSwitch: SwitchCompat
    private val userSettings = UserSettings.getInstance()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setNotificationSwitch(view)

        notificationSwitch.setOnCheckedChangeListener { switchCompat, _ ->
            notificationSwitchClickEvent(switchCompat)
        }
    }

    private fun setNotificationSwitch(view: View) {
        notificationSwitch = view.findViewById(R.id.notification_switch)
        notificationSwitch.isChecked =
            userSettings.get(requireContext(), UserSettings.NOTIFICATION_KEY)
    }

    private fun notificationSwitchClickEvent(switchCompat: CompoundButton) {
        userSettings.set(requireContext(), UserSettings.NOTIFICATION_KEY, switchCompat.isChecked)
    }
}
