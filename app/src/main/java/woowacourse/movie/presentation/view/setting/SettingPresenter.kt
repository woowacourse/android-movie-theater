package woowacourse.movie.presentation.view.setting

import android.content.Context
import androidx.core.content.edit

class SettingPresenter(
    val view: SettingContract.View,
    private val context: Context,
) : SettingContract.Presenter {
    private val prefs by lazy {
        context.getSharedPreferences("settings", Context.MODE_PRIVATE)
    }

    override fun fetchSettingInfo() {
        val isEnabled = prefs.getBoolean("notification", false)
        view.showPushAlarmSetting(isEnabled)
    }

    override fun savePushAlarmSetting(isEnabled: Boolean) {
        prefs.edit {
            putBoolean("notification", isEnabled)
        }
    }
}
