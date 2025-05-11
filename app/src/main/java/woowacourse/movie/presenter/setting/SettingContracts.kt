package woowacourse.movie.presenter.setting

interface SettingContracts {
    interface View {
        fun updateAlarmSwitchView(isPushEnabled: Boolean)
    }

    interface Presenter {
        fun changeAlarmSwitch(isChecked: Boolean)

        fun loadAlarmSwitch()
    }
}
