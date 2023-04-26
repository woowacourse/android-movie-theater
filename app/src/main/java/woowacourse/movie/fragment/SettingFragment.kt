package woowacourse.movie.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SwitchCompat
import androidx.fragment.app.Fragment
import woowacourse.movie.R
import woowacourse.movie.Setting
import woowacourse.movie.SharedSetting

class SettingFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_setting, container, false)
        makeNotificationSwitchCompat(view)
        return view
    }

    private fun makeNotificationSwitchCompat(view: View) {
        val setting: Setting = SharedSetting(view.context)
        val switch = view.findViewById<SwitchCompat>(R.id.setting_push_switch)
        switch.isChecked = setting.getSettingValue(SETTING_NOTIFICATION)
        switch.setOnCheckedChangeListener { _, isChecked ->
            setting.setSettingValue(SETTING_NOTIFICATION, isChecked)
        }
    }

    companion object {
        const val SETTING_NOTIFICATION = "notification"
    }
}
