package woowacourse.movie.ui.main.setting

import woowacourse.movie.SharedPreference

class SettingPresenter(
    private val view: SettingContract.View,
    private val sharedPreferences: SharedPreference
) : SettingContract.Presenter {
    init {
        setUpSwitch(SettingFragment.NOTIFICATIONS, false)
    }

    override fun setUpSwitch(notifications: String, boolean: Boolean) {
        view.setSwitchChecked(sharedPreferences.getBoolean(notifications, boolean))
    }

    override fun updateSwitch(notifications: String, boolean: Boolean) {
        sharedPreferences.setBoolean(notifications, boolean)
        view.setSwitchChecked(sharedPreferences.getBoolean(notifications, boolean))
    }
}
