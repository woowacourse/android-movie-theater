package woowacourse.movie.view.setting

import woowacourse.movie.domain.model.SettingData
import woowacourse.movie.repository.SettingRepository
import woowacourse.movie.repository.TicketRepository

class SettingPresenter(
    private val view: SettingContract.View,
    private val repository: TicketRepository,
    private val settingRepository: SettingRepository,
) : SettingContract.Presenter {
    override fun setNotification() {
        repository.findAll { result ->
            result.onSuccess {
                val showTimes = it.map { ticket -> ticket.showTime.minusMinutes(30) }
                view.setNotification(
                    it,
                    showTimes,
                )
            }
        }
    }

    override fun setPermissionSwitch() {
        val settings = settingRepository.findAll()
        settings.onSuccess {
            val isPushAlarmEnabled =
                it.find { setting ->
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
