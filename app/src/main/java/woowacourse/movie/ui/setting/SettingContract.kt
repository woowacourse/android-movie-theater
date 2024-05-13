package woowacourse.movie.ui.setting

interface SettingContract {
    interface View

    interface Presenter {
        fun confirmAlarm()

        fun cancelAram()
    }
}
