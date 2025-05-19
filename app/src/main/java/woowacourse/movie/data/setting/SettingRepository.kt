package woowacourse.movie.data.setting

interface SettingRepository {
    fun isNotificationEnabled(): Boolean

    fun updateNotificationSetting(enabled: Boolean)
}
