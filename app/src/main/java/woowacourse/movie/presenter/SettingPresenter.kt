package woowacourse.movie.presenter

import woowacourse.movie.SettingPreferencesManager
import woowacourse.movie.contract.SettingContract

class SettingPresenter(val view: SettingContract.View) : SettingContract.Presenter {

    override fun changeAlarmReceptionState() {
        SettingPreferencesManager.changeAlarmReceptionStatus()
    }

    override fun setInitSwitchState() {
        val isChecked = SettingPreferencesManager.getAlarmReceptionStatus()
        view.setSwitchState(isChecked)
    }
}
