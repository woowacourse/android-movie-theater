package woowacourse.movie.presentation.view.main.setting

interface SettingContract {
    interface View {
        fun updatePermissionNotGrantedView()
        fun updateAlarmSettingView(isSet: Boolean, isGranted: Boolean)
    }

    interface Presenter {
        fun setAlarmSetting(isGranted: Boolean)
        fun getAlarmSettingInfo()
        fun onChangedAlarmSetting(isChecked: Boolean)
    }
}