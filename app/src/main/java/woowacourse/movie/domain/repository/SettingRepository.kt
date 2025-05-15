package woowacourse.movie.domain.repository

interface SettingRepository {
    fun fetchNotificationSetting(): Boolean

    fun saveNotificationSetting(isNotificationEnabled: Boolean)
}
