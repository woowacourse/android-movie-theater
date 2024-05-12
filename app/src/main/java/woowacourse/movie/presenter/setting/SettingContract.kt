package woowacourse.movie.presenter.setting

interface SettingContract {
    interface View {
        fun showSavedSetting(isPushSetting: Boolean)

        fun showPushSettingOnMessage()

        fun showPushSettingOffMessage()
    }

    interface Presenter {
        fun loadSavedSetting()

        fun settingPushAlarmState(isPushSetting: Boolean)
    }
}
