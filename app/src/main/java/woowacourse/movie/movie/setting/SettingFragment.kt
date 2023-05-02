package woowacourse.movie.movie.setting

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SwitchCompat
import androidx.fragment.app.Fragment
import woowacourse.movie.R
import woowacourse.movie.movie.MainActivity.Companion.setting_preference_key
import woowacourse.movie.movie.utils.SettingPreference

class SettingFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        val view = inflater.inflate(R.layout.fragment_setting, container, false)
        val switch = view.findViewById<SwitchCompat>(R.id.push_alarm_switch)
        this.activity?.let { bindSwitch(switch, it.applicationContext) }
        return view
    }

    private fun bindSwitch(switch: SwitchCompat, context: Context) {
        switch.isChecked = SettingPreference.getBoolean(context, setting_preference_key)
        switch.setOnCheckedChangeListener { _, isChecked ->
            SettingPreference.setBoolean(context, setting_preference_key, isChecked)
        }
    }
}
