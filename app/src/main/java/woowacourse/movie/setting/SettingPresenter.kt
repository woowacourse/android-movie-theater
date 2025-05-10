package woowacourse.movie.setting

import android.content.Context
import woowacourse.movie.data.SettingRepository

class SettingPresenter(
    private val view: SettingContract.View,
    private val settingPreference: SettingRepository,
) : SettingContract.Presenter {
    override fun setPermissionState(context: Context) {
        view.initAlarmState(settingPreference.isAlarmPermitted())
    }

    override fun updatePermission(isGrant: Boolean) {
        settingPreference.setAlarmPermitted(isGrant)
    }
}
