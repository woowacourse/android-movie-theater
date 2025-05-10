package woowacourse.movie

interface PermissionHandler {
    fun hasSettingAlarmPermission(): Boolean

    fun hasExactAlarmPermission(): Boolean
}
