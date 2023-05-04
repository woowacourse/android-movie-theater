package woowacourse.movie.repository

interface SettingRepository {
    fun setNotificationState(isChecked: Boolean)
    fun getNotificationState(): Boolean
}
