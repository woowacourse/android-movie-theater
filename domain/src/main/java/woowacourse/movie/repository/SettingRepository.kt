package woowacourse.movie.repository

interface SettingRepository {
    fun setAlarmState(state: Boolean)

    fun getAlarmState(): Boolean
}
