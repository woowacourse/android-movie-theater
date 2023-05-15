package woowacourse.movie.feature.setting

interface SettingContract {
    interface View {
        fun setInitAlarmSettingInfo(initAlarmSetInfo: Boolean)
        fun alarmPermissionIsNotApprove()
        fun errorLoadAlarmInfo()
        fun errorSetAlarmInfo()
    }

    interface Presenter {
        fun loadAlarmSettingInfo()
        fun changeSwitchCheckedState(isPermissionApprove: Boolean, isSwitchChecked: Boolean)
    }
}
