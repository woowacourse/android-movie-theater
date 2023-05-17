package woowacourse.movie.presentation.view.main.setting

interface SettingContract {
    interface View {
        fun updatePermissionNotGrantedView()
        fun updateAlarmSettingView(isSet: Boolean, isGranted: Boolean)
    }

    interface Presenter {
        fun setAlarmSetting(isGranted: Boolean)
        fun getAlarmSettingInfo(isNotificationGranted: Boolean)
        fun onChangedAlarmSetting(isChecked: Boolean)
    }
}