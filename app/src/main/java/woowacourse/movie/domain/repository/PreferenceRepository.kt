package woowacourse.movie.domain.repository

interface PreferenceRepository {
    fun isNotificationEnabled(): Boolean

    fun saveNotificationEnabled(mode: Boolean)
}
