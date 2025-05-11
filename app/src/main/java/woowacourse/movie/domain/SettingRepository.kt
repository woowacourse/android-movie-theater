package woowacourse.movie.domain

interface SettingRepository {
    fun getNotificationEnabled(): Boolean

    fun setNotificationEnabled(enabled: Boolean)
}
