package woowacourse.movie.setting

import android.content.Context
import woowacourse.movie.repository.MovieRepository
import kotlin.concurrent.thread

class SettingPresenter(
    private val repository: MovieRepository,
    private val context: Context,
) : SettingContract.Presenter {
    override fun setAlarm() {
        thread {
            runCatching {
                repository.reservations()
            }.onSuccess { reservations ->
                val settingAlarm = AlarmSetting()
                reservations.forEach {
                    settingAlarm.setAlarm(context, it)
                }
            }
        }.join()
    }

    override fun cancelAlarm() {
        thread {
            runCatching {
                repository.reservations()
            }.onSuccess { reservations ->
                val settingAlarm = AlarmSetting()
                reservations.forEach {
                    settingAlarm.cancelAlarm(context, it)
                }
            }
        }.join()
    }
}
