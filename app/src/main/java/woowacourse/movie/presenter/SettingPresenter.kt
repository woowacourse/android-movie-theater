package woowacourse.movie.presenter

import woowacourse.movie.database.SharedPreferenceManager
import woowacourse.movie.contract.SettingContract

class SettingPresenter(
    val view: SettingContract.View,
    private val sharedPreferenceManager: SharedPreferenceManager
) : SettingContract.Presenter {
    override fun onClickSwitch() {
        sharedPreferenceManager.changeData()
    }

    override fun updateSwitchState() {
        val isChecked = sharedPreferenceManager.getData()
        view.setSwitchState(isChecked)
    }
}
