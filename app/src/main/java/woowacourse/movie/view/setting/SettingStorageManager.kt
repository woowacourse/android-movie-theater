package woowacourse.movie.view.setting

interface SettingStorageManager {
    fun loadNotificationSetting(): Boolean

    fun updateNotificationSetting(enabled: Boolean)
}
