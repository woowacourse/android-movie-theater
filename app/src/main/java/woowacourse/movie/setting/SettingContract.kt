package woowacourse.movie.setting

import android.content.Context

interface SettingContract {
    interface View {
        fun initAlarmState(isGrant: Boolean)

        fun requestNotificationPermission()
    }

    interface Presenter {
        fun setPermissionState(context: Context)

        fun checkPermission(
            hasRequest: Boolean,
            context: Context,
        )

        fun updatePermission(isGrant: Boolean)
    }
}
