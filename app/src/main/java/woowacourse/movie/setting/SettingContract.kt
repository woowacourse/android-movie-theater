package woowacourse.movie.setting

interface SettingContract {
    interface View {
        fun requestPermission()

        fun showChecked(checked: Boolean)
    }

    interface Presenter {
        fun initSetting()

        fun toggleAlarm()
    }
}
