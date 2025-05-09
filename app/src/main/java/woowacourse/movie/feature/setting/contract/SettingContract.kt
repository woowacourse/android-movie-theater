package woowacourse.movie.feature.setting.contract

interface SettingContract {
    interface View {
        fun updateNotificationSettingSwitch(isNotificationEnabled: Boolean)
    }

    interface Presenter {
        fun getNotificationSetting()

        fun saveNotificationSetting(isNotificationEnabled: Boolean)
    }
}
