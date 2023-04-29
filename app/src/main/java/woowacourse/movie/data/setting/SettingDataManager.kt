package woowacourse.movie.data.setting

interface SettingDataManager {
    fun getIsAlarmSetting(): Boolean
    fun setIsAlarmSetting(isOn: Boolean)
}
