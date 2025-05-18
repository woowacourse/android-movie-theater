package woowacourse.movie.presenter.setting

interface SettingContracts {
    interface View {
        fun showNotificationPermission(isGranted: Boolean)
    }

    interface Presenter {
        fun updateNotificationPermission()

        fun updateNotificationPermission(isGranted: Boolean)
    }
}
