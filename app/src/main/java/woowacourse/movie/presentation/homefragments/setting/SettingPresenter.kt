package woowacourse.movie.presentation.homefragments.setting

class SettingPresenter(
    private val view: SettingContract.View,
) : SettingContract.Presenter {
    override fun loadSetting(savedSetting: Boolean) {
        view.showSavedSetting(savedSetting)
    }

    override fun settingAlarm(pushSetting: Boolean) {
        view.saveSetting(pushSetting)
    }
}
