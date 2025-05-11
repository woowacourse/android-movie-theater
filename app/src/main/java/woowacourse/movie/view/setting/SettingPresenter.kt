package woowacourse.movie.view.setting

import woowacourse.movie.domain.model.SettingData
import woowacourse.movie.domain.model.Ticket
import woowacourse.movie.repository.Repository

class SettingPresenter(
    val view: SettingContract.View,
    val repository: Repository<Ticket>,
    val settingRepository: Repository<SettingData>,
) : SettingContract.Presenter {
    override fun setNotification() {
        repository.findAll()
            .onSuccess {
                val showTimes = it.map { ticket -> ticket.showTime.minusMinutes(30) }
                view.setNotification(
                    it,
                    showTimes,
                )
            }
            .onFailure {
                view.showError(ERR_FAILED_TO_LOAD_TICKETS)
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
