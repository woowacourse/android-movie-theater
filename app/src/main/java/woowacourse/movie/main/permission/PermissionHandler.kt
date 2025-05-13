package woowacourse.movie.main.permission

interface PermissionHandler {
    fun hasSettingAlarmPermission(): Boolean

    fun hasExactAlarmPermission(): Boolean

    fun hasAllPermission(): Boolean
}
