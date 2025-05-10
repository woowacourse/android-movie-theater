package woowacourse.movie.setting

import android.content.Context

interface SettingContract {
    interface View {
        fun initAlarmState(isGrant: Boolean)
    }

    interface Presenter {
        fun setPermissionState(context: Context)

        fun updatePermission(isGrant: Boolean)
    }
}
