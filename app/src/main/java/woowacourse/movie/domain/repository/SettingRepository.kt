package woowacourse.movie.domain.repository

interface SettingRepository {
    fun getNotificationEnabled(): Boolean

    fun setNotificationEnabled(enabled: Boolean)
}
