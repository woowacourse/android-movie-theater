package woowacourse.movie.presenter.setting

import android.content.SharedPreferences
import woowacourse.movie.view.setting.SettingFragment

class SettingPresenter(
    private val view: SettingContract.View,
    private val sharedPreference: SharedPreferences,
) : SettingContract.Presenter {
    override fun loadSavedSetting() {
        val isPushSetting = sharedPreference.getBoolean(SettingFragment.PUSH_SETTING, false)
        view.showSavedSetting(isPushSetting)
    }

    override fun settingPushAlarmState(isPushSetting: Boolean) {
        val editor = sharedPreference.edit()
        editor.putBoolean(SettingFragment.PUSH_SETTING, isPushSetting).apply()
        if (isPushSetting)
            {
                view.showPushSettingOnToast()
            } else
            {
                view.showPushSettingOffToast()
            }
    }
}
