package woowacourse.movie

interface PermissionManager {
    fun requestPermission(permission: String, ifDeniedDescription: String)

    fun isPermissionDenied(permission: String): Boolean

    fun isPermissionDeniedForever(permission: String): Boolean
}
