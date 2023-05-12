package woowacourse.movie.view.setting

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SwitchCompat
import androidx.fragment.app.Fragment
import woowacourse.movie.R
import woowacourse.movie.permission.SinglePermissionRequester
import woowacourse.movie.storage.SettingsStorage
import woowacourse.movie.view.main.MainActivity

class SettingsFragment : Fragment(), SettingContract.View {
    private lateinit var pushNotificationSwitch: SwitchCompat

    override lateinit var present: SettingContract.Present

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_settings, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        present = SettingPresenter(this, SettingsStorage)

        pushNotificationSwitch = view.findViewById(R.id.push_notification_switch)

        present.setupNotificationState()
        initPushNotificationSwitch()
    }

    private fun initPushNotificationSwitch() {
        pushNotificationSwitch.setOnCheckedChangeListener { _, isChecked ->
            if (checkDeniedPermission())
                (activity as MainActivity).requestPermission()

            present.updateNotificationState(isChecked)
        }
    }

    override fun updateNotificationState(isChecked: Boolean) {
        pushNotificationSwitch.isChecked = isChecked
    }

    private fun checkDeniedPermission(): Boolean = context?.let {
        SinglePermissionRequester.checkDeniedPermission(
            it,
            SinglePermissionRequester.NOTIFICATION_PERMISSION
        )
    } ?: false
}
