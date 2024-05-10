package woowacourse.movie.presenter.setting

interface SettingContract {
    interface View {
        fun showSavedSetting(isPushSetting: Boolean)

        fun showPushSettingOnToast()

        fun showPushSettingOffToast()
    }

    interface Presenter {
        fun loadSavedSetting()

        fun settingPushAlarmState(isPushSetting: Boolean)
    }
}
