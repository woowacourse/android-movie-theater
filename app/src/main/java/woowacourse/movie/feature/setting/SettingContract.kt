package woowacourse.movie.feature.setting

interface SettingContract {
    interface View {
        fun setMovieReminderChecked(value: Boolean)
        fun requestPermission()
    }

    interface Presenter {
        fun setMovieReminderChecked(hasPermission: Boolean)
        fun changeMovieReminderChecked(hasPermission: Boolean, switchChecked: Boolean)
    }
}
