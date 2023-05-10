package woowacourse.movie.view.main.setting

import woowacourse.movie.database.SharedPreferenceManager

class SettingPresenter(
    val view: SettingContract.View,
    private val sharedPreferenceManager: SharedPreferenceManager,
) : SettingContract.Presenter {
    override fun changeAlarmState() {
        sharedPreferenceManager.changeData()
        val isChecked = sharedPreferenceManager.getData()
        view.setSwitchState(isChecked)
    }

    override fun initAlarmState() {
        val isChecked = sharedPreferenceManager.getData()
        view.setSwitchState(isChecked)
    }
}
