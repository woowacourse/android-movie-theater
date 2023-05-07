package woowacourse.movie.ui.main.setting

import woowacourse.movie.DefaultPreference

class SettingPresenter(
    private val view: SettingContract.View,
    private val sharedPreferences: DefaultPreference
) : SettingContract.Presenter {
    override fun setUpSwitch(notifications: String, boolean: Boolean) {
        view.setSwitchChecked(sharedPreferences.getBoolean(notifications, boolean))
    }

    override fun updateSwitch(notifications: String, boolean: Boolean) {
        sharedPreferences.setBoolean(notifications, boolean)
        view.setSwitchChecked(boolean)
    }
}
