package woowacourse.movie.ui.setting

interface MovieSettingContract {
    interface View {
        fun showNotificationStatus()
    }

    interface Presenter {
        fun loadNotificationStatus()
    }
}
