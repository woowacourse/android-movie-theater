package woowacourse.movie.ui.setting

interface MovieSettingContract {
    interface View {
        fun showNotificationStatus(status: Boolean)
    }

    interface Presenter {
        fun loadNotificationStatus()

        fun cancelNotification()

        fun setAlarmStatus(status: Boolean)
    }
}
