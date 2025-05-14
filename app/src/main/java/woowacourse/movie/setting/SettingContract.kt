package woowacourse.movie.setting

interface SettingContract {
    interface View {
        fun initAlarmState(isGrant: Boolean)
    }

    interface Presenter {
        fun setPermissionState()

        fun updatePermission(isGrant: Boolean)
    }
}
