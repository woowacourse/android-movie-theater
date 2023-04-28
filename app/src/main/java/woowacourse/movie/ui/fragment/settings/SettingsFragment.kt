package woowacourse.movie.ui.fragment.settings

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.appcompat.widget.SwitchCompat
import androidx.fragment.app.Fragment
import woowacourse.movie.R
import woowacourse.movie.ui.storage.SettingsStorage
import woowacourse.movie.ui.utils.openAndroidSettings
import woowacourse.movie.ui.utils.showSnack

class SettingsFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_settings, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initPushNotificationSwitch(view)
    }

    private fun initPushNotificationSwitch(view: View) {
        val pushNotificationSwitch = view.findViewById<SwitchCompat>(R.id.push_notification_switch)
        pushNotificationSwitch.isChecked = SettingsStorage.getPushNotification()
        pushNotificationSwitch.setOnCheckedChangeListener { _, isChecked ->
            SettingsStorage.editPushNotification(isChecked)
            if (!pushNotificationSwitch.isChecked) initShowSnack(view)
        }
    }

    private fun initShowSnack(view: View) {
        activity?.let {
            view.findViewById<LinearLayout>(R.id.fragment_settings).run {
                showSnack(
                    getString(R.string.notification_permission_snackbar_message),
                    getString(R.string.notification_permission_snackbar_button),
                    it::openAndroidSettings
                )
            }
        }
    }
}
