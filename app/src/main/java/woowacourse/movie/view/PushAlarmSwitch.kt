package woowacourse.movie.view

import android.content.Context
import android.util.Log
import android.widget.Switch
import androidx.appcompat.widget.SwitchCompat
import woowacourse.SettingPreferenceManager

class PushAlarmSwitch(val view: Switch) {
    init {
        SettingPreferenceManager.inIt(view.context)
        view.isChecked = SettingPreferenceManager.getAlarmReceptionStatus()
        view.setOnClickListener {
            SettingPreferenceManager.changeAlarmReceptionStatus()
        }
    }
}
