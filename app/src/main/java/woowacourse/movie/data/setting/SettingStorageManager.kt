package woowacourse.movie.data.setting

interface SettingStorageManager {
    fun isNotificationEnabled(): Boolean

    fun updateNotificationSetting(enabled: Boolean)
}
