package woowacourse.movie.ui.main

class SettingPresenter(
    private val view: SettingContract.View,
    private val settingRepository: SettingRepository
) : SettingContract.Presenter {

    override fun saveSetting(isAvailable: Boolean) {
        settingRepository.state = isAvailable
        view.setSwitch(
            isChecked = isAvailable
        )
    }

    override fun loadSetting() {
        view.setSwitch(
            isChecked = settingRepository.state
        )
    }
}
