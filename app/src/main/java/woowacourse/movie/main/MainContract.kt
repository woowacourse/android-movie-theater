package woowacourse.movie.main

interface MainContract {
    interface View {
        fun showExactAlarmDialog()

        fun showSettingAlarmDialog()

        fun shouldShowNotificationRationale(): Boolean

        fun requestNotificationPermission()
    }

    interface Presenter {
        fun requestExactAlarmPermission()

        fun requestSettingAlarmPermission()

        fun checkAllPermission()
    }
}
