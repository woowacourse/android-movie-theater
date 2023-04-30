package woowacourse.movie.movie.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SwitchCompat
import androidx.fragment.app.Fragment
import woowacourse.movie.R
import woowacourse.movie.movie.alarm.SettingPreference

class SettingFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        val view = inflater.inflate(R.layout.fragment_setting, container, false)
        val settingPreference = this.activity?.let { SettingPreference(it.applicationContext) }
        val switch = view.findViewById<SwitchCompat>(R.id.push_alarm_switch)
        settingPreference?.let { bindSwitch(switch, it) }
        return view
    }

    private fun bindSwitch(switch: SwitchCompat, settingPreference: SettingPreference) {
        switch.isChecked = settingPreference.setting
        switch.setOnCheckedChangeListener { _, isChecked ->
            settingPreference.setting = isChecked
        }
    }
}
