package woowacourse.movie.view

import android.widget.Switch
import woowacourse.movie.SettingPreferencesManager

class PushAlarmSwitch(val view: Switch) {
    init {
        view.isChecked = SettingPreferencesManager.getAlarmReceptionStatus()
        view.setOnClickListener {
            SettingPreferencesManager.changeAlarmReceptionStatus()
        }
    }
}
