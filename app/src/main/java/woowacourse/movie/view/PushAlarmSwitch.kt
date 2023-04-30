package woowacourse.movie.view

import android.widget.Switch
import woowacourse.movie.SettingPreferenceManager

class PushAlarmSwitch(val view: Switch) {
    init {
        SettingPreferenceManager.inIt(view.context)
        view.isChecked = SettingPreferenceManager.getAlarmReceptionStatus()
        view.setOnClickListener {
            SettingPreferenceManager.changeAlarmReceptionStatus()
        }
    }
}
