package woowacourse.movie.domain.repository

interface PreferenceRepository {
    fun getNotificationMode(): Boolean

    fun saveNotificationMode(mode: Boolean)
}
