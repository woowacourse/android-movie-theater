package woowacourse.movie.feature.setting.contract

interface SettingContract {
    interface View {
        fun updateNotificationSettingSwitch(isNotificationEnabled: Boolean)
    }

    interface Presenter {
        fun loadNotificationSetting()

        fun saveNotificationSetting(isNotificationEnabled: Boolean)
    }
}
