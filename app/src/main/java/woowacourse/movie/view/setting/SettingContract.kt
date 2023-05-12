package woowacourse.movie.view.setting

interface SettingContract {
    interface View {
        var present: Present

        fun updateNotificationState(isChecked: Boolean)
    }

    interface Present {
        fun setupNotificationState()
        fun updateNotificationState(isGranted: Boolean)
    }
}
