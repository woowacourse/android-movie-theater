package woowacourse.movie.domain.datasource

interface PermissionDataSource {
    fun savePermission(isGranted: Boolean)

    fun isGranted(): Boolean
}
