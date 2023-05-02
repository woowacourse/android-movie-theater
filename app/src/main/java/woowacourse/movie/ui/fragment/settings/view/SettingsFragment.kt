package woowacourse.movie.ui.fragment.settings.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SwitchCompat
import androidx.fragment.app.Fragment
import woowacourse.movie.R
import woowacourse.movie.ui.fragment.settings.SettingsContract
import woowacourse.movie.ui.fragment.settings.presentation.SettingsPresentation

class SettingsFragment : Fragment(), SettingsContract.View {
    override lateinit var presentation: SettingsContract.Presentation

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_settings, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        presentation = SettingsPresentation()

        initPushNotificationSwitch(view)
    }

    private fun initPushNotificationSwitch(view: View) {
        val pushNotificationSwitch = view.findViewById<SwitchCompat>(R.id.push_notification_switch)
        pushNotificationSwitch.isChecked = presentation.getPushNotificationState()
        pushNotificationSwitch.setOnCheckedChangeListener { _, isChecked ->
            presentation.changePushNotificationState(isChecked)
        }
    }
}
