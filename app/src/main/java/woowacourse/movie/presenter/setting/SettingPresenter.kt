package woowacourse.movie.presenter.setting

class SettingPresenter(private val view: SettingContract.View) : SettingContract.Presenter {
    override fun loadSavedSetting(isPushSetting: Boolean) {
        view.showSavedSetting(isPushSetting)
    }

    override fun settingPushAlarmState(
        isPushSetting: Boolean,
    ) {
        view.saveSetting(isPushSetting)
    }
}
