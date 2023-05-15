package woowacourse.movie.presentation.view.main.setting

import woowacourse.movie.data.SharedPreference
import woowacourse.movie.data.SharedPreferenceUtil

class SettingPresenter(
    private val view: SettingContract.View,
    private val sharedPreferenceUtil: SharedPreference
) : SettingContract.Presenter {

    override fun setAlarmSetting(isGranted: Boolean) {
        if (!isGranted) {
            sharedPreferenceUtil.setBoolean(
                SharedPreferenceUtil.ALARM_KEY,
                false
            )
            view.updatePermissionNotGrantedView()
        }
    }

    override fun getAlarmSettingInfo(isNotificationGranted: Boolean) {
        val isSet =
            sharedPreferenceUtil.getBoolean(SharedPreferenceUtil.ALARM_KEY, false)
        view.updateAlarmSettingView(isSet, isNotificationGranted)
    }

    override fun onChangedAlarmSetting(isChecked: Boolean) {
        sharedPreferenceUtil.setBoolean(SharedPreferenceUtil.ALARM_KEY, isChecked)
    }

}