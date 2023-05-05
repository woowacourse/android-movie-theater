package woowacourse.movie.data

interface AlarmSettingRepository {
    fun getEnablePushNotification(): Boolean
    fun setEnablePushNotification(value: Boolean)
}
