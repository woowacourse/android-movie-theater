package woowacourse.movie.fragment.setting

interface SettingContract {
    interface View {
        var presenter: Presenter

        fun reRequestPermission(isChecked: Boolean)
        fun updateCanPushSwitch(isChecked: Boolean)
        fun disableCanPushSwitch()
    }

    interface Presenter {
        fun saveCanPushAlarmSwitchData(isChecked: Boolean)
        fun loadCanPushAlarmSwitchData(default: Boolean)
        fun onClickCanPushSwitch(
            isPermissionDenied: Boolean,
            isForeverDeniedPermission: Boolean,
            isChecked: Boolean
        )
    }
}
