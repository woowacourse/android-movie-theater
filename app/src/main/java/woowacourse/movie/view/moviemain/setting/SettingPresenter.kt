package woowacourse.movie.view.moviemain.setting

import woowacourse.movie.domain.repository.ReservationRepository
import woowacourse.movie.domain.repository.SettingRepository
import woowacourse.movie.view.mapper.toUiModel

class SettingPresenter(
    private val view: SettingContract.View,
    private val settingManager: SettingRepository,
    private val reservationRepository: ReservationRepository
) : SettingContract.Presenter {

    override fun initToggle() {
        view.setToggle(settingManager.getIsAlarmSetting())
    }

    override fun changeAlarmState(isOn: Boolean) {
        if (isOn && view.requestNotificationPermission()) {
            val reservations = reservationRepository.findAll().map { it.toUiModel() }
            view.setAlarms(reservations)
            settingManager.setIsAlarmSetting(true)
            view.setToggle(true)
            return
        }
        view.cancelAlarms()
        settingManager.setIsAlarmSetting(false)
        view.setToggle(false)
    }
}
