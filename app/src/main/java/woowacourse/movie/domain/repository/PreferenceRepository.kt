package woowacourse.movie.domain.repository

interface PreferenceRepository {
    fun getNotificationMode(): Result<Boolean>

    fun saveNotificationMode(mode: Boolean): Result<Unit>
}
