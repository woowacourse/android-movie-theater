package woowacourse.movie.domain.repository

interface SettingRepository {
    fun getIsAlarmSetting(): Boolean
    fun setIsAlarmSetting(isOn: Boolean)
}
