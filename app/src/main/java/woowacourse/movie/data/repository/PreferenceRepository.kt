package woowacourse.movie.data.repository

interface PreferenceRepository {
    fun saveNotificationPreference(enabled: Boolean)

    fun loadNotificationPreference(): Boolean
}
