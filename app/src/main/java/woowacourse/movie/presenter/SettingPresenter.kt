package woowacourse.movie.presenter

import woowacourse.movie.SettingPreferencesManager
import woowacourse.movie.contract.SettingContract

class SettingPresenter(val view: SettingContract.View) : SettingContract.Presenter {

    override fun onClickSwitch() {
        SettingPreferencesManager.changeAlarmReceptionStatus()
    }

    override fun updateSwitchState() {
        val isChecked = SettingPreferencesManager.getAlarmReceptionStatus()
        view.setSwitchState(isChecked)
    }
}
