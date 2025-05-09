package woowacourse.movie.presentation.view.setting

interface SettingContract {
    interface View {
        fun showPushAlarmSetting(isEnabled: Boolean)
    }

    interface Presenter {
        fun fetchSettingInfo()

        fun savePushAlarmSetting(isEnabled: Boolean)
    }
}
