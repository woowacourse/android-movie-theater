package woowacourse.movie.movie.setting

import woowacourse.movie.movie.MainActivity.Companion.SETTING_PREFERENCE_KEY
import woowacourse.movie.movie.utils.SettingPreference

class SettingPresenter(private val view: SettingContract.View) : SettingContract.Presenter {
    override fun initFragment() {
        view.initSwitch(getSwitchState())
    }

    override fun getSwitchState(): Boolean {
        return SettingPreference.getBoolean(SETTING_PREFERENCE_KEY)
    }

    override fun setSwitchState(isChecked: Boolean) {
        SettingPreference.setBoolean(SETTING_PREFERENCE_KEY, isChecked)
    }
}
