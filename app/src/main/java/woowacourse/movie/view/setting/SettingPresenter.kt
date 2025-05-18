package woowacourse.movie.view.setting

import androidx.lifecycle.Observer
import woowacourse.movie.domain.model.SettingData
import woowacourse.movie.domain.model.Ticket
import woowacourse.movie.repository.SettingRepository
import woowacourse.movie.repository.TicketRepository
import woowacourse.movie.util.CustomLiveData
import kotlin.concurrent.thread

class SettingPresenter(
    private val view: SettingContract.View,
    private val repository: TicketRepository,
    private val settingRepository: SettingRepository,
) : SettingContract.Presenter {
    private val liveData = CustomLiveData<List<Ticket>>()
    private val observer =
        object : woowacourse.movie.util.Observer<List<Ticket>> {
            override fun update(data: List<Ticket>) {
                val showTimes = data.map { ticket -> ticket.showTime.minusMinutes(30) }
                view.setNotification(
                    data,
                    showTimes,
                )
            }
        }

    init {
        liveData.subscribe(observer)
    }

    override fun setNotification() {
        thread {
            repository.findAll()
                .onSuccess {
                    liveData.put(it)
                }
        }
    }

    override fun setPermissionSwitch() {
        val settings = settingRepository.findAll()
        settings.onSuccess {
            val isPushAlarmEnabled =
                it.find {
                        setting ->
                    setting.key == SettingData.NOTIFICATION_KEY
                }?.value ?: false
            view.setPermissionSwitch(isPushAlarmEnabled)
        }.onFailure {
            view.showError(ERR_FAILED_TO_LOAD_SETTINGS)
        }
    }

    override fun savePushAlarmSetting(isEnabled: Boolean) {
        settingRepository.save(SettingData(SettingData.NOTIFICATION_KEY, isEnabled))
    }

    companion object {
        private const val ERR_FAILED_TO_LOAD_TICKETS = "티켓을 불러오는데 실패했습니다."
        private const val ERR_FAILED_TO_LOAD_SETTINGS = "설정을 불러오는데 실패했습니다."
    }
}
