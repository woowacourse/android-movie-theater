package woowacourse.movie.data.setting

interface SettingStorageManager {
    fun loadNotificationSetting(): Boolean

    fun updateNotificationSetting(enabled: Boolean)
}
