package woowacourse.movie.setting

import woowacourse.movie.repository.MovieRepository
import kotlin.concurrent.thread

class SettingPresenter(
    private val repository: MovieRepository,
    private val alarmSetting: AlarmSetting,
) : SettingContract.Presenter {
    override fun setAlarm() {
        thread {
            runCatching {
                repository.reservations()
            }.onSuccess { reservations ->
                reservations.forEach {
                    alarmSetting.setAlarm(it)
                }
            }
        }.join()
    }

    override fun cancelAlarm() {
        thread {
            runCatching {
                repository.reservations()
            }.onSuccess { reservations ->
                reservations.forEach {
                    alarmSetting.cancelAlarm(it)
                }
            }
        }.join()
    }
}
