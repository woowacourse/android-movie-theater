package woowacourse.movie.model.data.storage

interface SettingStorage {
    fun setNotificationSettings(notifyState: Boolean): Boolean
    fun getNotificationSettings(): Boolean
}
