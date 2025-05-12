package woowacourse.movie.domain

interface SettingRepository {
    fun updateNotificationEnabled(isEnabled: Boolean)

    fun isNotificationEnabled(): Boolean
}
