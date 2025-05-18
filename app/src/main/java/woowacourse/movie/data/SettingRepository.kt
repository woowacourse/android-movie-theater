package woowacourse.movie.data

interface SettingRepository {
    fun isAlarmPermitted(): Boolean

    fun setAlarmPermitted(isGranted: Boolean)
}
