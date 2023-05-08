package woowacourse.movie.view.moviemain

interface MovieMainContract {
    interface View {
        val presenter: Presenter
        fun setUpBottomNavigation()
        fun requestNotificationPermission()
    }

    interface Presenter {
        fun setAlarmPreference(isAlarmOn: Boolean)
    }
}
