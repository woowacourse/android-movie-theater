package woowacourse.movie.presentation.view.main.setting

import android.content.Context
import woowacourse.movie.R
import woowacourse.movie.data.SharedPreferenceUtil
import woowacourse.movie.presentation.permission.NotificationPermission

class SettingPresenter(
    private val view: SettingContract.View,
    private val context: Context
) : SettingContract.Presenter {

    private val sharedPreferenceUtil: SharedPreferenceUtil by lazy { SharedPreferenceUtil(context) }
    private val notificationPermission by lazy { NotificationPermission(context) }

    override fun setAlarmSetting(isGranted: Boolean) {
        if (!isGranted) {
            sharedPreferenceUtil.setBoolean(
                context.getString(R.string.push_alarm_setting),
                false
            )
            view.updatePermissionNotGrantedView()
        }
    }

    override fun getAlarmSettingInfo() {
        val isSet =
            sharedPreferenceUtil.getBoolean(context.getString(R.string.push_alarm_setting), false)
        val isGrantedPermission = notificationPermission.isGranted()
        view.updateAlarmSettingView(isSet, isGrantedPermission)
    }

    override fun onChangedAlarmSetting(isChecked: Boolean) {
        sharedPreferenceUtil.setBoolean(context.getString(R.string.push_alarm_setting), isChecked)
    }

}