package woowacourse.movie.data

import com.example.domain.repository.AlarmSettingRepository
import woowacourse.movie.data.preference.AlarmSettingPreference

class AlarmSettingRepositoryImpl(private val alarmSettingPreference: AlarmSettingPreference) :
    AlarmSettingRepository {

    override fun getEnablePushNotification(): Boolean =
        alarmSettingPreference.enablePushNotification

    override fun setEnablePushNotification(value: Boolean) {
        alarmSettingPreference.enablePushNotification = value
    }
}
