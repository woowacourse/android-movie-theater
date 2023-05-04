package woowacourse.movie.presentation.view.main

interface MainContract {
    interface View {
        fun updateNotificationGrantedView(isGranted: Boolean)
    }

    interface Presenter {
        fun setDefaultNotificationAlarmSetting()
        fun setNotificationAlarmSetting(isTrue: Boolean)
        fun checkNotificationPermission()
    }
}

