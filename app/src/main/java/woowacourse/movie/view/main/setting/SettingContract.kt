package woowacourse.movie.view.main.setting

interface SettingContract {
    interface Presenter {
        fun changeAlarmState()
        fun initAlarmState()
    }
    interface View {
        fun setSwitchState(isChecked: Boolean)
    }
}
