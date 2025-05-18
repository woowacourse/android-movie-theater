package woowacourse.movie.view.setting

interface SettingContract {
    interface View {
        fun showNotificationPermission(isGranted: Boolean)
    }

    interface Presenter {
        fun synchronizePermission(isGranted: Boolean)

        fun setPreferences(isGranted: Boolean)
    }
}
