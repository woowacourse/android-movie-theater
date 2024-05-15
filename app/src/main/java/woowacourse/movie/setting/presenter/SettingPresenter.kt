package woowacourse.movie.setting.presenter

import woowacourse.MovieApplication.Companion.sharedPrefs
import woowacourse.movie.setting.presenter.contract.SettingContract

class SettingPresenter(private val settingView: SettingContract.View) : SettingContract.Presenter {
    override fun loadAlarmSwitch() {
        settingView.setUpAlarmSwitch(sharedPrefs.getSavedAlarmSetting())
    }
}
