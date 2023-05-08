package woowacourse.movie.movie.setting

import woowacourse.movie.movie.BasePreference
import woowacourse.movie.movie.MainActivity.Companion.SETTING_PREFERENCE_KEY

class SettingPresenter(private val view: SettingContract.View, private val settingPreference: BasePreference) : SettingContract.Presenter {
    override fun initFragment() {
        view.initSwitch(getSwitchState())
    }

    override fun getSwitchState(): Boolean {
        return settingPreference.getBoolean(SETTING_PREFERENCE_KEY)
    }

    override fun setSwitchState(isChecked: Boolean) {
        settingPreference.setBoolean(SETTING_PREFERENCE_KEY, isChecked)
    }
}
