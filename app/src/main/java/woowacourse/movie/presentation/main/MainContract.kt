package woowacourse.movie.presentation.main

interface MainContract {
    interface View {
        fun requestNotificationPermission()
    }

    interface Presenter {
        fun checkPermissions()

        fun saveNotificationSetting(isEnabled: Boolean)
    }
}
