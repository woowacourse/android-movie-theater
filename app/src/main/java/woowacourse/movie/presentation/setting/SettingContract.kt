package woowacourse.movie.presentation.setting

interface SettingContract {
    interface View {
        fun notifyNotificationEnabled(isEnabled: Boolean)
    }

    interface Presenter {
        fun updateNotificationEnabled(isEnabled: Boolean)
    }
}
