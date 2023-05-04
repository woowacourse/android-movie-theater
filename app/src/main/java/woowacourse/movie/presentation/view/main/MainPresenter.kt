package woowacourse.movie.presentation.view.main

import android.content.Context
import woowacourse.movie.R
import woowacourse.movie.data.SharedPreferenceUtil
import woowacourse.movie.presentation.permission.NotificationPermission

class MainPresenter(private val view: MainContract.View, private val context: Context) :
    MainContract.Presenter {
    private val sharedPreferenceUtil: SharedPreferenceUtil by lazy { SharedPreferenceUtil(context) }

    override fun setDefaultNotificationAlarmSetting() {
        sharedPreferenceUtil.setBoolean(
            context.getString(R.string.push_alarm_setting),
            false
        )
    }

    override fun setNotificationAlarmSetting(isGranted: Boolean) {
        sharedPreferenceUtil.setBoolean(
            context.getString(R.string.push_alarm_setting),
            isGranted
        )
    }

    override fun checkNotificationPermission() {
        val result = NotificationPermission(context).isGranted()
        view.updateNotificationGrantedView(result)
    }
}