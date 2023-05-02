package woowacourse.movie.presentation.settings

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SwitchCompat
import androidx.fragment.app.Fragment
import woowacourse.movie.R
import woowacourse.movie.data.settings.SettingsPreference
import woowacourse.movie.data.settings.SettingsPreferenceKeys

class SettingsFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_settings, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initNotificationSwitch()
    }

    private fun initNotificationSwitch() {
        val notificationSwitch =
            requireActivity().findViewById<SwitchCompat>(R.id.switchPushPermission)

        notificationSwitch.isChecked = isNotifiable()

        notificationSwitch.setOnCheckedChangeListener { _, isChecked ->
            setNotifiable(isChecked)
        }
    }

    private fun isNotifiable(): Boolean {
        val settingsNotification =
            SettingsPreference.getInstance(SettingsPreferenceKeys.notificationKey, requireContext())
        return settingsNotification.isAvailable
    }

    private fun setNotifiable(value: Boolean) {
        val settingsNotification =
            SettingsPreference.getInstance(SettingsPreferenceKeys.notificationKey, requireContext())
        settingsNotification.isAvailable = value
    }
}
