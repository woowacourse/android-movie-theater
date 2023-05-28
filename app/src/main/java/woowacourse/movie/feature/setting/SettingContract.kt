package woowacourse.movie.feature.setting

interface SettingContract {
    interface View {
        fun setMovieReminderChecked(value: Boolean)
        fun requestPermission()
    }

    interface Presenter {
        fun setMovieReminderChecked()
        fun changeMovieReminderChecked(switchChecked: Boolean)
    }
}
