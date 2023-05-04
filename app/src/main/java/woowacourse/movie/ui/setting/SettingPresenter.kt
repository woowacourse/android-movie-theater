package woowacourse.movie.ui.setting

class SettingPresenter(
    private val view: SettingContract.View,
    private val settingRepository: SettingRepository
) : SettingContract.Presenter {

    override fun saveSetting(isAvailable: Boolean) {
        settingRepository.isAvailableAlarm = isAvailable
        view.setSwitch(
            isChecked = isAvailable
        )
    }

    override fun loadSetting() {
        view.setSwitch(
            isChecked = settingRepository.isAvailableAlarm
        )
    }
}
