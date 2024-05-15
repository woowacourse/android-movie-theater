package woowacourse.movie.list.contract

interface SettingContract {
    interface View

    interface Presenter {
        fun saveNotificationGranted(isGranted: Boolean)
    }
}
