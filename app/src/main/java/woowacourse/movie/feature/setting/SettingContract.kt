package woowacourse.movie.feature.setting

interface SettingContract {
    interface View {
        fun setMovieStartReminderSwitchChecked(value: Boolean)
        fun requestPermission()
    }

    interface Presenter {
        fun loadMovieStartReminderSetting()
        fun setMovieStartReminderSettingEnable(value: Boolean)
    }
}
