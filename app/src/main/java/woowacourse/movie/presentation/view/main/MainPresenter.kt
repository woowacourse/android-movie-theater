package woowacourse.movie.presentation.view.main

import woowacourse.movie.data.SharedPreference
import woowacourse.movie.data.SharedPreferenceUtil

class MainPresenter(
    private val view: MainContract.View,
    private val sharedPreferenceUtil: SharedPreference
) :
    MainContract.Presenter {

    override fun setDefaultNotificationAlarmSetting() {
        sharedPreferenceUtil.setBoolean(
            SharedPreferenceUtil.ALARM_KEY,
            false
        )
    }

    override fun setNotificationAlarmSetting(isGranted: Boolean) {
        sharedPreferenceUtil.setBoolean(
            SharedPreferenceUtil.ALARM_KEY,
            isGranted
        )
    }

    override fun checkNotificationPermission(isNotificationGranted: Boolean) {
        view.updateNotificationGrantedView(isNotificationGranted)
    }
}