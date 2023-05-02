package woowacourse.movie.ui.fragment.settings

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SwitchCompat
import androidx.fragment.app.Fragment
import woowacourse.movie.R
import woowacourse.movie.permission.SinglePermissionRequester
import woowacourse.movie.ui.activity.MainActivity
import woowacourse.movie.ui.storage.SettingsStorage

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
            if (checkDeniedPermission())
                (activity as MainActivity).requestPermission()

            SettingsStorage.editPushNotification(isChecked)
        }
    }

    private fun checkDeniedPermission(): Boolean = context?.let {
        SinglePermissionRequester.checkDeniedPermission(
            it,
            SinglePermissionRequester.NOTIFICATION_PERMISSION
        )
    } ?: false
}
