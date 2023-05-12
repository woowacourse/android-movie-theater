package woowacourse.movie.fragment.setting.contract.presenter

import woowacourse.movie.util.preference.DataPreference
import woowacourse.movie.fragment.setting.contract.SettingContract

class SettingPresenter(val view: SettingContract.View, private val dataPreference: DataPreference) :
    SettingContract.Presenter {

    override fun onSaveData(data: Boolean) {
        dataPreference.saveData(data)
    }

    override fun onLoadData() {
        view.setSwitchState(dataPreference.loadData())
    }
}
