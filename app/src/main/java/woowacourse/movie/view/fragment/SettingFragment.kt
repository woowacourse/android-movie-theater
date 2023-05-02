package woowacourse.movie.view.fragment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import woowacourse.movie.R
import woowacourse.movie.view.PushAlarmSwitch

class SettingFragment : Fragment(R.layout.fragment_setting) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        PushAlarmSwitch(view.findViewById(R.id.setting_push_alarm_switch))
    }
}
