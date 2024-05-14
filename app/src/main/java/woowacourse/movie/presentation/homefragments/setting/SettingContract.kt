package woowacourse.movie.presentation.homefragments.setting

interface SettingContract {
    interface View {
        fun setNotificationState(isChecked: Boolean)
    }

    interface Presenter {
        fun loadNotificationState(isChecked: Boolean?)
    }
}
