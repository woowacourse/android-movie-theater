package woowacourse.movie.setting

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import androidx.core.content.ContextCompat
import woowacourse.movie.data.SettingRepository

class SettingPresenter(
    private val view: SettingContract.View,
    private val settingPreference: SettingRepository,
) : SettingContract.Presenter {
    override fun setPermissionState(context: Context) {
        view.initAlarmState(isPermitted(context) && settingPreference.isAlarmPermitted())
    }

    override fun checkPermission(
        hasRequest: Boolean,
        context: Context,
    ) {
        if (hasRequest) {
            if (!isPermitted(context)) {
                view.requestNotificationPermission()
                return
            }
        }
        updatePermission(hasRequest)
    }

    override fun updatePermission(isGrant: Boolean) {
        settingPreference.setAlarmPermitted(isGrant)
    }

    private fun isPermitted(context: Context): Boolean {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.TIRAMISU) return true

        return ContextCompat.checkSelfPermission(
            context, Manifest.permission.POST_NOTIFICATIONS,
        ) == PackageManager.PERMISSION_GRANTED
    }
}
